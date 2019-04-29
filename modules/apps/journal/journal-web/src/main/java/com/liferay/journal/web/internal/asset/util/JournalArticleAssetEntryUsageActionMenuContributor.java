/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.web.internal.asset.util;

import com.liferay.asset.constants.AssetEntryUsageConstants;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.util.AssetEntryUsageActionMenuContributor;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = AssetEntryUsageActionMenuContributor.class
)
public class JournalArticleAssetEntryUsageActionMenuContributor
	implements AssetEntryUsageActionMenuContributor {

	@Override
	public List<DropdownItem> getAssetEntryUsageActionMenu(
		AssetEntryUsage assetEntryUsage, HttpServletRequest request) {

		if (assetEntryUsage.getType() ==
				AssetEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE) {

			return Collections.emptyList();
		}

		if (_isWidgetPageTemplate(assetEntryUsage)) {
			return Collections.emptyList();
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryUsage.getAssetEntryId());

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			assetEntry.getClassPK(), WorkflowConstants.STATUS_ANY, false);

		return new DropdownItemList() {
			{
				JournalArticle approvedArticle =
					_journalArticleLocalService.fetchLatestArticle(
						assetEntry.getClassPK(),
						WorkflowConstants.STATUS_APPROVED);

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				ResourceBundle resourceBundle =
					_resourceBundleLoader.loadResourceBundle(
						themeDisplay.getLocale());

				if (approvedArticle != null) {
					add(
						dropdownItem -> {
							dropdownItem.setHref(
								_getURL(assetEntryUsage, 0, request));
							dropdownItem.setLabel(
								LanguageUtil.get(
									resourceBundle, "view-in-page"));
						});
				}

				if (article.isDraft() || article.isPending() ||
					article.isScheduled()) {

					try {
						if (JournalArticlePermission.contains(
								themeDisplay.getPermissionChecker(), article,
								ActionKeys.UPDATE)) {

							String key = "preview-draft-in-page";

							if (article.isPending()) {
								key = "preview-pending-in-page";
							}
							else if (article.isScheduled()) {
								key = "preview-scheduled-in-page";
							}

							String label = LanguageUtil.get(
								resourceBundle, key);

							add(
								dropdownItem -> {
									dropdownItem.setHref(
										_getURL(
											assetEntryUsage,
											assetEntryUsage.getAssetEntryId(),
											request));
									dropdownItem.setLabel(label);
								});
						}
					}
					catch (PortalException pe) {
						_log.error("Unable to check article permission", pe);
					}
				}
			}
		};
	}

	private String _getURL(
			AssetEntryUsage assetEntryUsage, long previewAssetEntryId,
			HttpServletRequest request)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutURL = null;

		if (assetEntryUsage.getContainerType() == _portal.getClassNameId(
				FragmentEntryLink.class)) {

			Layout layout = _layoutLocalService.fetchLayout(
				assetEntryUsage.getPlid());

			layoutURL = _portal.getLayoutFriendlyURL(layout, themeDisplay);

			if (previewAssetEntryId > 0) {
				layoutURL = _http.setParameter(
					layoutURL, "previewAssetEntryId",
					String.valueOf(previewAssetEntryId));
			}
		}
		else {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, assetEntryUsage.getContainerKey(),
				assetEntryUsage.getPlid(), PortletRequest.RENDER_PHASE);

			if (previewAssetEntryId > 0) {
				portletURL.setParameter(
					"previewAssetEntryId", String.valueOf(previewAssetEntryId));
			}

			layoutURL = portletURL.toString();
		}

		String portletURLString = _http.setParameter(
			layoutURL, "p_l_back_url", themeDisplay.getURLCurrent());

		return portletURLString + "#portlet_" +
			assetEntryUsage.getContainerKey();
	}

	private boolean _isWidgetPageTemplate(AssetEntryUsage assetEntryUsage) {
		if (assetEntryUsage.getType() !=
				AssetEntryUsageConstants.TYPE_PAGE_TEMPLATE) {

			return false;
		}

		long plid = assetEntryUsage.getPlid();

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if ((layout.getClassNameId() > 0) && (layout.getClassPK() > 0)) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry == null) {
			return false;
		}

		if (layoutPageTemplateEntry.getType() !=
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAssetEntryUsageActionMenuContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private Http _http;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.journal.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}