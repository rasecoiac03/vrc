package com.caio.vrc.repository;

import java.util.Collection;

import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;

/**
 * Repository for {@link Property}
 * 
 * @author caio.silva
 *
 */
public interface PropertyRepository {

	/**
	 * Add a {@link Property} to repository
	 * 
	 * @param property
	 *            {@link Property}
	 * @return a {@link Property} with new id setted
	 */
	Property add(Property property);

	/**
	 * Get a {@link Property} from repository.
	 * 
	 * @param id
	 *            unique id for {@link Property}
	 * @return {@link Property}
	 */
	Property get(int id);

	/**
	 * Return a collection of {@link Property} given filter coordinates from repository.
	 * 
	 * @param propertyFilter
	 *            {@link PropertyFilter} filter with coordinates
	 * @return {@link Collection} of {@link Property}
	 */
	Collection<Property> find(PropertyFilter propertyFilter);

}
