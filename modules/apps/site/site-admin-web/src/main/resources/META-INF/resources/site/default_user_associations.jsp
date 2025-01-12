<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

long groupId = scopeGroupId;

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupId = liveGroup.getGroupId();

	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

List<Role> defaultSiteRoles = new ArrayList();

long[] defaultSiteRoleIds = StringUtil.split(groupTypeSettings.getProperty("defaultSiteRoleIds"), 0L);

for (long defaultSiteRoleId : defaultSiteRoleIds) {
	Role role = RoleLocalServiceUtil.getRole(defaultSiteRoleId);

	defaultSiteRoles.add(role);
}

List<Team> defaultTeams = new ArrayList();

long[] defaultTeamIds = StringUtil.split(groupTypeSettings.getProperty("defaultTeamIds"), 0L);

for (long defaultTeamId : defaultTeamIds) {
	Team team = TeamLocalServiceUtil.getTeam(defaultTeamId);

	defaultTeams.add(team);
}
%>

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon
		iconCssClass="icon-remove"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="siteRolesRoleIds" type="hidden" value="<%= ListUtil.toString(defaultSiteRoles, Role.ROLE_ID_ACCESSOR) %>" />
<aui:input name="teamsTeamIds" type="hidden" value="<%= ListUtil.toString(defaultTeams, TeamImpl.TEAM_ID_ACCESSOR) %>" />

<h3><liferay-ui:message key="default-user-associations" /></h3>

<h3><liferay-ui:message key="site-roles" /> <liferay-ui:icon-help message="default-site-roles-assignment-help" /></h3>

<liferay-ui:search-container
	headerNames="title,null"
	id="siteRolesSearchContainer"
>
	<liferay-ui:search-container-results
		results="<%= defaultSiteRoles %>"
		total="<%= defaultSiteRoles.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		keyProperty="roleId"
		modelVar="role"
	>

		<liferay-ui:search-container-column-text
			name="title"
		>
			<liferay-ui:icon
				iconCssClass="<%= RolesAdminUtil.getIconCssClass(role) %>"
				label="<%= true %>"
				message="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="list-group-item-field"
		>
			<a class="modify-link" data-rowId="<%= role.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:button-row>
	<aui:button cssClass="modify-link" id="selectSiteRoleLink" value="select" />
</aui:button-row>

<h3><liferay-ui:message key="teams" /> <liferay-ui:icon-help message="default-teams-assignment-help" /></h3>

<liferay-ui:search-container
	headerNames="title,null"
	id="teamsSearchContainer"
>
	<liferay-ui:search-container-results
		results="<%= defaultTeams %>"
		total="<%= defaultTeams.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Team"
		keyProperty="teamId"
		modelVar="team"
	>
		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(team.getName()) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="list-group-item-field"
		>
			<a class="modify-link" data-rowId="<%= team.getTeamId() %>" href="javascript:;"><%= removeRoleIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:button-row>
	<aui:button cssClass="modify-link" id="selectTeamLink" value="select" />
</aui:button-row>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />siteRolesSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;
			var tr = link.ancestor('tr');

			var rowId = link.getAttribute('data-rowId');

			var selectSiteRole = Util.getWindow('<portlet:namespace />selectSiteRole');

			if (selectSiteRole) {
				var selectButton = selectSiteRole.iframe.node.get('contentWindow.document').one('.selector-button[data-roleid="' + rowId + '"]');

				Util.toggleDisabled(selectButton, false);
			}

			searchContainer.deleteRow(tr, rowId);

			<portlet:namespace />deleteRole(rowId);
		},
		'.modify-link'
	);

	Liferay.on(
		'<portlet:namespace />syncSiteRoles',
		function(event) {
			event.selectors.each(
				function(item, index, collection) {
					var modifyLink = searchContainerContentBox.one('.modify-link[data-rowid="' + item.attr('data-roleid') + '"]');

					if (!modifyLink) {
						Util.toggleDisabled(item, false);
					}
				}
			);
		}
	);
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />teamsSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;
			var tr = link.ancestor('tr');

			var rowId = link.getAttribute('data-rowId');

			var selectTeam = Util.getWindow('<portlet:namespace />selectTeam');

			if (selectTeam) {
				var selectButton = selectTeam.iframe.node.get('contentWindow.document').one('.selector-button[data-teamid="' + rowId + '"]');

				Util.toggleDisabled(selectButton, false);
			}

			searchContainer.deleteRow(tr, rowId);

			<portlet:namespace />deleteTeam(rowId);
		},
		'.modify-link'
	);

	Liferay.on(
		'<portlet:namespace />enableRemovedTeams',
		function(event) {
			event.selectors.each(
				function(item, index, collection) {
					var modifyLink = searchContainerContentBox.one('.modify-link[data-rowid="' + item.attr('data-teamid') + '"]');

					if (!modifyLink) {
						Util.toggleDisabled(item, false);
					}
				}
			);
		}
	);
</aui:script>

<aui:script use="liferay-search-container,escape">
	A.one('#<portlet:namespace />selectSiteRoleLink').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					id: '<portlet:namespace />selectSiteRole',
					title: '<liferay-ui:message arguments="site-role" key="select-x" />',

					<%
					PortletURL selectSiteRoleURL = PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.BROWSE);

					selectSiteRoleURL.setParameter("roleType", String.valueOf(RoleConstants.TYPE_SITE));
					selectSiteRoleURL.setParameter("step", "2");
					selectSiteRoleURL.setParameter("groupId", String.valueOf(groupId));
					selectSiteRoleURL.setParameter("eventName", liferayPortletResponse.getNamespace() + "selectSiteRole");
					selectSiteRoleURL.setWindowState(LiferayWindowState.POP_UP);
					%>

					uri: '<%= selectSiteRoleURL.toString() %>'
				},
				function(event) {
					for (var i = 0; i < <portlet:namespace />siteRolesRoleIds.length; i++) {
						if (<portlet:namespace />siteRolesRoleIds[i] == event.roleid) {
							return;
						}
					}

					var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />' + event.searchcontainername + 'SearchContainer');

					var rowColumns = [];

					rowColumns.push('<i class="' + event.iconcssclass + '"></i> ' + A.Escape.html(event.roletitle));

					if (event.groupid) {
						rowColumns.push('<a class="modify-link" data-rowId="' + event.roleid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

						<portlet:namespace />siteRolesRoleIds.push(event.roleid);

						document.<portlet:namespace />fm.<portlet:namespace />siteRolesRoleIds.value = <portlet:namespace />siteRolesRoleIds.join(',');
					}
					else {
						rowColumns.push('<a class="modify-link" data-rowId="' + event.roleid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');
					}

					searchContainer.addRow(rowColumns, event.roleid);

					searchContainer.updateDataStore();
				}
			);
		}
	);

	A.one('#<portlet:namespace />selectTeamLink').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					id: '<portlet:namespace />selectTeam',
					title: '<liferay-ui:message arguments="team" key="select-x" />',

					<%
					PortletURL selectTeamURL = PortletProviderUtil.getPortletURL(request, Team.class.getName(), PortletProvider.Action.BROWSE);

					selectTeamURL.setParameter("groupId", String.valueOf(groupId));
					selectTeamURL.setParameter("eventName", liferayPortletResponse.getNamespace() + "selectTeam");
					selectTeamURL.setWindowState(LiferayWindowState.POP_UP);
					%>

					uri: '<%= selectTeamURL.toString() %>'
				},
				function(event) {
					var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />' + event.teamsearchcontainername + 'SearchContainer');

					var rowColumns = [];

					rowColumns.push(event.teamname);

					if (event.teamid) {
						rowColumns.push('<a class="modify-link" data-rowId="' + event.teamid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

						<portlet:namespace />teamsTeamIds.push(event.teamid);

						document.<portlet:namespace />fm.<portlet:namespace />teamsTeamIds.value = <portlet:namespace />teamsTeamIds.join(',');
					}
					else {
						rowColumns.push('<a class="modify-link" data-rowId="' + event.teamid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');
					}

					searchContainer.addRow(rowColumns, event.teamid);
					searchContainer.updateDataStore();
				}
			);
		}
	);
</aui:script>

<aui:script>
	var <portlet:namespace />siteRolesRoleIds = ['<%= ListUtil.toString(defaultSiteRoles, Role.ROLE_ID_ACCESSOR, "', '") %>'];
	var <portlet:namespace />teamsTeamIds = ['<%= ListUtil.toString(defaultTeams, Team.TEAM_ID_ACCESSOR, "', '") %>'];

	function <portlet:namespace />deleteRole(roleId) {
		for (var i = 0; i < <portlet:namespace />siteRolesRoleIds.length; i++) {
			if (<portlet:namespace />siteRolesRoleIds[i] == roleId) {
				<portlet:namespace />siteRolesRoleIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />siteRolesRoleIds.value = <portlet:namespace />siteRolesRoleIds.join(',');
	}

	function <portlet:namespace />deleteTeam(teamId) {
		for (var i = 0; i < <portlet:namespace />teamsTeamIds.length; i++) {
			if (<portlet:namespace />teamsTeamIds[i] == teamId) {
				<portlet:namespace />teamsTeamIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />teamsTeamIds.value = <portlet:namespace />teamsTeamIds.join(',');
	}
</aui:script>