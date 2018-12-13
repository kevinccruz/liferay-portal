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

package com.liferay.layout.page.template.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateStructureException;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructurePersistence;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the layout page template structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructurePersistence
 * @see com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructureUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateStructurePersistenceImpl
	extends BasePersistenceImpl<LayoutPageTemplateStructure>
	implements LayoutPageTemplateStructurePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutPageTemplateStructureUtil} to access the layout page template structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutPageTemplateStructureImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LayoutPageTemplateStructureModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the layout page template structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByUuid(String uuid, int start,
		int end,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByUuid(String uuid, int start,
		int end,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator,
		boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<LayoutPageTemplateStructure> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateStructure>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateStructure layoutPageTemplateStructure : list) {
					if (!uuid.equals(layoutPageTemplateStructure.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<LayoutPageTemplateStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateStructure>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByUuid_First(String uuid,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByUuid_First(uuid,
				orderByComparator);

		if (layoutPageTemplateStructure != null) {
			return layoutPageTemplateStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchPageTemplateStructureException(msg.toString());
	}

	/**
	 * Returns the first layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByUuid_First(String uuid,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		List<LayoutPageTemplateStructure> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByUuid_Last(String uuid,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByUuid_Last(uuid,
				orderByComparator);

		if (layoutPageTemplateStructure != null) {
			return layoutPageTemplateStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchPageTemplateStructureException(msg.toString());
	}

	/**
	 * Returns the last layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByUuid_Last(String uuid,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateStructure> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template structures before and after the current layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the current layout page template structure
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateStructure[] findByUuid_PrevAndNext(
		long layoutPageTemplateStructureId, String uuid,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		uuid = Objects.toString(uuid, "");

		LayoutPageTemplateStructure layoutPageTemplateStructure = findByPrimaryKey(layoutPageTemplateStructureId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateStructure[] array = new LayoutPageTemplateStructureImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					layoutPageTemplateStructure, uuid, orderByComparator, true);

			array[1] = layoutPageTemplateStructure;

			array[2] = getByUuid_PrevAndNext(session,
					layoutPageTemplateStructure, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateStructure getByUuid_PrevAndNext(
		Session session,
		LayoutPageTemplateStructure layoutPageTemplateStructure, String uuid,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(LayoutPageTemplateStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template structures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutPageTemplateStructure layoutPageTemplateStructure : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateStructure);
		}
	}

	/**
	 * Returns the number of layout page template structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout page template structures
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 = "layoutPageTemplateStructure.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(layoutPageTemplateStructure.uuid IS NULL OR layoutPageTemplateStructure.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			LayoutPageTemplateStructureModelImpl.UUID_COLUMN_BITMASK |
			LayoutPageTemplateStructureModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the layout page template structure where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPageTemplateStructureException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByUUID_G(uuid,
				groupId);

		if (layoutPageTemplateStructure == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPageTemplateStructureException(msg.toString());
		}

		return layoutPageTemplateStructure;
	}

	/**
	 * Returns the layout page template structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the layout page template structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateStructure) {
			LayoutPageTemplateStructure layoutPageTemplateStructure = (LayoutPageTemplateStructure)result;

			if (!Objects.equals(uuid, layoutPageTemplateStructure.getUuid()) ||
					(groupId != layoutPageTemplateStructure.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<LayoutPageTemplateStructure> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					LayoutPageTemplateStructure layoutPageTemplateStructure = list.get(0);

					result = layoutPageTemplateStructure;

					cacheResult(layoutPageTemplateStructure);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (LayoutPageTemplateStructure)result;
		}
	}

	/**
	 * Removes the layout page template structure where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout page template structure that was removed
	 */
	@Override
	public LayoutPageTemplateStructure removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = findByUUID_G(uuid,
				groupId);

		return remove(layoutPageTemplateStructure);
	}

	/**
	 * Returns the number of layout page template structures where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout page template structures
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "layoutPageTemplateStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(layoutPageTemplateStructure.uuid IS NULL OR layoutPageTemplateStructure.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "layoutPageTemplateStructure.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			LayoutPageTemplateStructureModelImpl.UUID_COLUMN_BITMASK |
			LayoutPageTemplateStructureModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator,
		boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplateStructure> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateStructure>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateStructure layoutPageTemplateStructure : list) {
					if (!uuid.equals(layoutPageTemplateStructure.getUuid()) ||
							(companyId != layoutPageTemplateStructure.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<LayoutPageTemplateStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateStructure>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (layoutPageTemplateStructure != null) {
			return layoutPageTemplateStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchPageTemplateStructureException(msg.toString());
	}

	/**
	 * Returns the first layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		List<LayoutPageTemplateStructure> list = findByUuid_C(uuid, companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (layoutPageTemplateStructure != null) {
			return layoutPageTemplateStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchPageTemplateStructureException(msg.toString());
	}

	/**
	 * Returns the last layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateStructure> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template structures before and after the current layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the current layout page template structure
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateStructure[] findByUuid_C_PrevAndNext(
		long layoutPageTemplateStructureId, String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		uuid = Objects.toString(uuid, "");

		LayoutPageTemplateStructure layoutPageTemplateStructure = findByPrimaryKey(layoutPageTemplateStructureId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateStructure[] array = new LayoutPageTemplateStructureImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					layoutPageTemplateStructure, uuid, companyId,
					orderByComparator, true);

			array[1] = layoutPageTemplateStructure;

			array[2] = getByUuid_C_PrevAndNext(session,
					layoutPageTemplateStructure, uuid, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateStructure getByUuid_C_PrevAndNext(
		Session session,
		LayoutPageTemplateStructure layoutPageTemplateStructure, String uuid,
		long companyId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(LayoutPageTemplateStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template structures where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutPageTemplateStructure layoutPageTemplateStructure : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateStructure);
		}
	}

	/**
	 * Returns the number of layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout page template structures
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "layoutPageTemplateStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(layoutPageTemplateStructure.uuid IS NULL OR layoutPageTemplateStructure.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "layoutPageTemplateStructure.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			LayoutPageTemplateStructureModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the layout page template structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<LayoutPageTemplateStructure> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateStructure>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateStructure layoutPageTemplateStructure : list) {
					if ((groupId != layoutPageTemplateStructure.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutPageTemplateStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateStructure>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutPageTemplateStructure != null) {
			return layoutPageTemplateStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPageTemplateStructureException(msg.toString());
	}

	/**
	 * Returns the first layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		List<LayoutPageTemplateStructure> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutPageTemplateStructure != null) {
			return layoutPageTemplateStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPageTemplateStructureException(msg.toString());
	}

	/**
	 * Returns the last layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateStructure> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template structures before and after the current layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the current layout page template structure
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateStructure[] findByGroupId_PrevAndNext(
		long layoutPageTemplateStructureId, long groupId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = findByPrimaryKey(layoutPageTemplateStructureId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateStructure[] array = new LayoutPageTemplateStructureImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateStructure, groupId, orderByComparator,
					true);

			array[1] = layoutPageTemplateStructure;

			array[2] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateStructure, groupId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateStructure getByGroupId_PrevAndNext(
		Session session,
		LayoutPageTemplateStructure layoutPageTemplateStructure, long groupId,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(LayoutPageTemplateStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template structures where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutPageTemplateStructure layoutPageTemplateStructure : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateStructure);
		}
	}

	/**
	 * Returns the number of layout page template structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template structures
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutPageTemplateStructure.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			LayoutPageTemplateStructureModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateStructureModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			LayoutPageTemplateStructureModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the layout page template structure where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchPageTemplateStructureException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByG_C_C(long groupId,
		long classNameId, long classPK)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByG_C_C(groupId,
				classNameId, classPK);

		if (layoutPageTemplateStructure == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPageTemplateStructureException(msg.toString());
		}

		return layoutPageTemplateStructure;
	}

	/**
	 * Returns the layout page template structure where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByG_C_C(long groupId,
		long classNameId, long classPK) {
		return fetchByG_C_C(groupId, classNameId, classPK, true);
	}

	/**
	 * Returns the layout page template structure where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByG_C_C(long groupId,
		long classNameId, long classPK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C,
					finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateStructure) {
			LayoutPageTemplateStructure layoutPageTemplateStructure = (LayoutPageTemplateStructure)result;

			if ((groupId != layoutPageTemplateStructure.getGroupId()) ||
					(classNameId != layoutPageTemplateStructure.getClassNameId()) ||
					(classPK != layoutPageTemplateStructure.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<LayoutPageTemplateStructure> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
						finderArgs, list);
				}
				else {
					LayoutPageTemplateStructure layoutPageTemplateStructure = list.get(0);

					result = layoutPageTemplateStructure;

					cacheResult(layoutPageTemplateStructure);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (LayoutPageTemplateStructure)result;
		}
	}

	/**
	 * Removes the layout page template structure where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the layout page template structure that was removed
	 */
	@Override
	public LayoutPageTemplateStructure removeByG_C_C(long groupId,
		long classNameId, long classPK)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = findByG_C_C(groupId,
				classNameId, classPK);

		return remove(layoutPageTemplateStructure);
	}

	/**
	 * Returns the number of layout page template structures where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching layout page template structures
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "layoutPageTemplateStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "layoutPageTemplateStructure.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "layoutPageTemplateStructure.classPK = ?";

	public LayoutPageTemplateStructurePersistenceImpl() {
		setModelClass(LayoutPageTemplateStructure.class);

		setModelImplClass(LayoutPageTemplateStructureImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the layout page template structure in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 */
	@Override
	public void cacheResult(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {
		entityCache.putResult(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			layoutPageTemplateStructure.getPrimaryKey(),
			layoutPageTemplateStructure);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				layoutPageTemplateStructure.getUuid(),
				layoutPageTemplateStructure.getGroupId()
			}, layoutPageTemplateStructure);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
			new Object[] {
				layoutPageTemplateStructure.getGroupId(),
				layoutPageTemplateStructure.getClassNameId(),
				layoutPageTemplateStructure.getClassPK()
			}, layoutPageTemplateStructure);

		layoutPageTemplateStructure.resetOriginalValues();
	}

	/**
	 * Caches the layout page template structures in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateStructures the layout page template structures
	 */
	@Override
	public void cacheResult(
		List<LayoutPageTemplateStructure> layoutPageTemplateStructures) {
		for (LayoutPageTemplateStructure layoutPageTemplateStructure : layoutPageTemplateStructures) {
			if (entityCache.getResult(
						LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateStructureImpl.class,
						layoutPageTemplateStructure.getPrimaryKey()) == null) {
				cacheResult(layoutPageTemplateStructure);
			}
			else {
				layoutPageTemplateStructure.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout page template structures.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateStructureImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template structure.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {
		entityCache.removeResult(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			layoutPageTemplateStructure.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutPageTemplateStructureModelImpl)layoutPageTemplateStructure,
			true);
	}

	@Override
	public void clearCache(
		List<LayoutPageTemplateStructure> layoutPageTemplateStructures) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplateStructure layoutPageTemplateStructure : layoutPageTemplateStructures) {
			entityCache.removeResult(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateStructureImpl.class,
				layoutPageTemplateStructure.getPrimaryKey());

			clearUniqueFindersCache((LayoutPageTemplateStructureModelImpl)layoutPageTemplateStructure,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutPageTemplateStructureModelImpl layoutPageTemplateStructureModelImpl) {
		Object[] args = new Object[] {
				layoutPageTemplateStructureModelImpl.getUuid(),
				layoutPageTemplateStructureModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			layoutPageTemplateStructureModelImpl, false);

		args = new Object[] {
				layoutPageTemplateStructureModelImpl.getGroupId(),
				layoutPageTemplateStructureModelImpl.getClassNameId(),
				layoutPageTemplateStructureModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C, args,
			layoutPageTemplateStructureModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutPageTemplateStructureModelImpl layoutPageTemplateStructureModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutPageTemplateStructureModelImpl.getUuid(),
					layoutPageTemplateStructureModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((layoutPageTemplateStructureModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutPageTemplateStructureModelImpl.getOriginalUuid(),
					layoutPageTemplateStructureModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutPageTemplateStructureModelImpl.getGroupId(),
					layoutPageTemplateStructureModelImpl.getClassNameId(),
					layoutPageTemplateStructureModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}

		if ((layoutPageTemplateStructureModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutPageTemplateStructureModelImpl.getOriginalGroupId(),
					layoutPageTemplateStructureModelImpl.getOriginalClassNameId(),
					layoutPageTemplateStructureModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}
	}

	/**
	 * Creates a new layout page template structure with the primary key. Does not add the layout page template structure to the database.
	 *
	 * @param layoutPageTemplateStructureId the primary key for the new layout page template structure
	 * @return the new layout page template structure
	 */
	@Override
	public LayoutPageTemplateStructure create(
		long layoutPageTemplateStructureId) {
		LayoutPageTemplateStructure layoutPageTemplateStructure = new LayoutPageTemplateStructureImpl();

		layoutPageTemplateStructure.setNew(true);
		layoutPageTemplateStructure.setPrimaryKey(layoutPageTemplateStructureId);

		String uuid = PortalUUIDUtil.generate();

		layoutPageTemplateStructure.setUuid(uuid);

		layoutPageTemplateStructure.setCompanyId(companyProvider.getCompanyId());

		return layoutPageTemplateStructure;
	}

	/**
	 * Removes the layout page template structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure that was removed
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateStructure remove(
		long layoutPageTemplateStructureId)
		throws NoSuchPageTemplateStructureException {
		return remove((Serializable)layoutPageTemplateStructureId);
	}

	/**
	 * Removes the layout page template structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template structure
	 * @return the layout page template structure that was removed
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateStructure remove(Serializable primaryKey)
		throws NoSuchPageTemplateStructureException {
		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateStructure layoutPageTemplateStructure = (LayoutPageTemplateStructure)session.get(LayoutPageTemplateStructureImpl.class,
					primaryKey);

			if (layoutPageTemplateStructure == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutPageTemplateStructure);
		}
		catch (NoSuchPageTemplateStructureException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected LayoutPageTemplateStructure removeImpl(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplateStructure)) {
				layoutPageTemplateStructure = (LayoutPageTemplateStructure)session.get(LayoutPageTemplateStructureImpl.class,
						layoutPageTemplateStructure.getPrimaryKeyObj());
			}

			if (layoutPageTemplateStructure != null) {
				session.delete(layoutPageTemplateStructure);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateStructure != null) {
			clearCache(layoutPageTemplateStructure);
		}

		return layoutPageTemplateStructure;
	}

	@Override
	public LayoutPageTemplateStructure updateImpl(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {
		boolean isNew = layoutPageTemplateStructure.isNew();

		if (!(layoutPageTemplateStructure instanceof LayoutPageTemplateStructureModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutPageTemplateStructure.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(layoutPageTemplateStructure);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutPageTemplateStructure proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutPageTemplateStructure implementation " +
				layoutPageTemplateStructure.getClass());
		}

		LayoutPageTemplateStructureModelImpl layoutPageTemplateStructureModelImpl =
			(LayoutPageTemplateStructureModelImpl)layoutPageTemplateStructure;

		if (Validator.isNull(layoutPageTemplateStructure.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutPageTemplateStructure.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplateStructure.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplateStructure.setCreateDate(now);
			}
			else {
				layoutPageTemplateStructure.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!layoutPageTemplateStructureModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplateStructure.setModifiedDate(now);
			}
			else {
				layoutPageTemplateStructure.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPageTemplateStructure.isNew()) {
				session.save(layoutPageTemplateStructure);

				layoutPageTemplateStructure.setNew(false);
			}
			else {
				layoutPageTemplateStructure = (LayoutPageTemplateStructure)session.merge(layoutPageTemplateStructure);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutPageTemplateStructureModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					layoutPageTemplateStructureModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					layoutPageTemplateStructureModelImpl.getUuid(),
					layoutPageTemplateStructureModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					layoutPageTemplateStructureModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutPageTemplateStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateStructureModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						layoutPageTemplateStructureModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((layoutPageTemplateStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateStructureModelImpl.getOriginalUuid(),
						layoutPageTemplateStructureModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						layoutPageTemplateStructureModelImpl.getUuid(),
						layoutPageTemplateStructureModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((layoutPageTemplateStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateStructureModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						layoutPageTemplateStructureModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(LayoutPageTemplateStructureModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateStructureImpl.class,
			layoutPageTemplateStructure.getPrimaryKey(),
			layoutPageTemplateStructure, false);

		clearUniqueFindersCache(layoutPageTemplateStructureModelImpl, false);
		cacheUniqueFindersCache(layoutPageTemplateStructureModelImpl);

		layoutPageTemplateStructure.resetOriginalValues();

		return layoutPageTemplateStructure;
	}

	/**
	 * Returns the layout page template structure with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template structure
	 * @return the layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageTemplateStructureException {
		LayoutPageTemplateStructure layoutPageTemplateStructure = fetchByPrimaryKey(primaryKey);

		if (layoutPageTemplateStructure == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutPageTemplateStructure;
	}

	/**
	 * Returns the layout page template structure with the primary key or throws a {@link NoSuchPageTemplateStructureException} if it could not be found.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateStructure findByPrimaryKey(
		long layoutPageTemplateStructureId)
		throws NoSuchPageTemplateStructureException {
		return findByPrimaryKey((Serializable)layoutPageTemplateStructureId);
	}

	/**
	 * Returns the layout page template structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure, or <code>null</code> if a layout page template structure with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateStructure fetchByPrimaryKey(
		long layoutPageTemplateStructureId) {
		return fetchByPrimaryKey((Serializable)layoutPageTemplateStructureId);
	}

	/**
	 * Returns all the layout page template structures.
	 *
	 * @return the layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template structures
	 */
	@Override
	public List<LayoutPageTemplateStructure> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateStructure> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<LayoutPageTemplateStructure> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateStructure>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE;

				if (pagination) {
					sql = sql.concat(LayoutPageTemplateStructureModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPageTemplateStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateStructure>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout page template structures from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplateStructure layoutPageTemplateStructure : findAll()) {
			remove(layoutPageTemplateStructure);
		}
	}

	/**
	 * Returns the number of layout page template structures.
	 *
	 * @return the number of layout page template structures
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTPAGETEMPLATESTRUCTURE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "layoutPageTemplateStructureId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutPageTemplateStructureModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout page template structure persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LayoutPageTemplateStructureImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE = "SELECT layoutPageTemplateStructure FROM LayoutPageTemplateStructure layoutPageTemplateStructure";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE = "SELECT layoutPageTemplateStructure FROM LayoutPageTemplateStructure layoutPageTemplateStructure WHERE ";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATESTRUCTURE = "SELECT COUNT(layoutPageTemplateStructure) FROM LayoutPageTemplateStructure layoutPageTemplateStructure";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATESTRUCTURE_WHERE = "SELECT COUNT(layoutPageTemplateStructure) FROM LayoutPageTemplateStructure layoutPageTemplateStructure WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutPageTemplateStructure.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutPageTemplateStructure exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutPageTemplateStructure exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutPageTemplateStructurePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "data"
			});
}