package com.caio.vrc.service.impl;

import java.util.Collection;

import javax.inject.Inject;

import com.caio.vrc.cache.KeepCached;
import com.caio.vrc.domain.Position;
import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.caio.vrc.exception.VrcInternalErrorException;
import com.caio.vrc.exception.VrcNotFoundException;
import com.caio.vrc.repository.PropertyRepository;
import com.caio.vrc.repository.ProvinceRepository;
import com.caio.vrc.service.PropertiesService;

/**
 * Implementation add and get information from repositories.
 * 
 * @author caio.silva
 * @see PropertiesService
 *
 */
public class PropertiesServiceImpl implements PropertiesService {

	private final PropertyRepository propertiesRepository;

	private final ProvinceRepository provinceRepository;

	@Inject
	public PropertiesServiceImpl(PropertyRepository propertiesRepository, ProvinceRepository provinceRepository) {
		this.propertiesRepository = propertiesRepository;
		this.provinceRepository = provinceRepository;
	}

	@Override
	public Property add(Property property) {
		try {
			Property propertySaved = propertiesRepository.add(property);
			setProvinces(propertySaved);
			return propertySaved;
		} catch (Exception e) {
			throw new VrcInternalErrorException(e);
		}
	}

	@Override
	@KeepCached(expire = 30)
	public Property get(int id) {
		Property property = propertiesRepository.get(id);
		if (property == null) {
			throw new VrcNotFoundException("no property found");
		}
		setProvinces(property);
		return property;
	}

	private void setProvinces(Property property) {
		property.setProvinces(provinceRepository.get(new Position(property.getLat(), property.getLon())));
	}

	@Override
	@KeepCached(expire = 30)
	public Collection<Property> find(PropertyFilter propertyFilter) {
		final Collection<Property> properties = propertiesRepository.find(propertyFilter);
		if (properties == null || properties.isEmpty()) {
			throw new VrcNotFoundException("no properties found");
		}
		properties.parallelStream().forEach(p -> setProvinces(p));
		return properties;
	}

}
