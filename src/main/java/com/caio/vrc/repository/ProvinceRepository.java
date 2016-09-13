package com.caio.vrc.repository;

import java.util.Collection;

import com.caio.vrc.domain.Position;
import com.caio.vrc.domain.Province;

/**
 * Repository for {@link Province}
 * 
 * @author caio.silva
 *
 */
public interface ProvinceRepository {

	/**
	 * Return a collection of {@link Province} given position from repository.
	 * 
	 * @param point
	 *            {@link Position} filter with coordinates
	 * @return {@link Collection} of {@link Province}
	 */
	Collection<Province> get(Position point);

}
