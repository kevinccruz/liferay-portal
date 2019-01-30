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

package com.liferay.portal.search.legacy.stats;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.search.stats.StatsResponse;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface StatsResultsTranslator {

	/**
	 * Provides a legacy StatsResults object based off a StatsResponse object.
	 *
	 * @param statsResponse the StatsResponse object to be converted
	 * @return the converted legacy StatsResults object
	 *
	 * @review
	 */
	public StatsResults translate(StatsResponse statsResponse);

}