package com.caio.vrc.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.caio.vrc.resource.domain.PropertiesResponse;
import com.caio.vrc.resource.domain.PropertyFilterParam;
import com.caio.vrc.resource.domain.PropertyRequest;
import com.caio.vrc.resource.domain.PropertyResponse;

public class ApiUtil {

	public static PropertyResponse propertyResponseFrom(Property property) {
		return new PropertyResponse(property.getId(), property.getLat(), property.getLon(), property.getTitle(),
				property.getPrice().longValue(), property.getDescription(), property.getBeds(), property.getBaths(),
				property.getSquareMeters(),
				provinces(property));
	}

	private static List<String> provinces(Property property) {
		return property.getProvinces() != null
				? property.getProvinces().stream().map(p -> p.getName()).collect(Collectors.toList())
				: Collections.<String>emptyList();
	}

	public static Property propertyFrom(PropertyRequest request) {
		return new Property(request.getTitle(), new BigDecimal(request.getPrice()), request.getDescription(),
				Integer.valueOf(request.getX()),
				Integer.valueOf(request.getY()), Integer.valueOf(request.getBeds()),
				Integer.valueOf(request.getBaths()), Integer.valueOf(request.getSquareMeters()));
	}

	public static PropertiesResponse propertiesResponseFrom(Collection<Property> properties) {
		long foundProperties = properties != null ? properties.size() : 0;
		return new PropertiesResponse(foundProperties,
				properties.parallelStream().map(p -> propertyResponseFrom(p)).collect(Collectors.toList()));
	}

	public static PropertyFilter propertyFilterFrom(PropertyFilterParam propertyFilterParam) {
		return new PropertyFilter(toInt(propertyFilterParam.getAx()), toInt(propertyFilterParam.getAy()),
				toInt(propertyFilterParam.getBx()), toInt(propertyFilterParam.getBy()));
	}

	private static int toInt(String value) {
		return Integer.valueOf(value);
	}

}
