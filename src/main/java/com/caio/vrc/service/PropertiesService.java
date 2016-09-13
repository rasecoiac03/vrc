package com.caio.vrc.service;

import java.util.Collection;

import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;

/**
 * Service implementation layer which work as a façade to repositories. In this layer could get from databases, services, others.
 * 
 * @author caio.silva
 *
 */
public interface PropertiesService {

	/**
	 * Add a {@link Property} in repository
	 * 
	 * @return {@link Property} with a id setted
	 */
	Property add(Property property);

	/**
	 * Get information about a unique id property
	 * 
	 * @param id
	 *            unique id {@link Property.id}
	 * @return {@link Property} full object
	 */
	Property get(int id);

	/**
	 * Finds properties given a filter coordinates
	 * 
	 * @param propertyFilter
	 *            ({@link PropertyFilter})
	 * @return Collection of {@link Property}
	 */
	Collection<Property> find(PropertyFilter propertyFilter);

}
