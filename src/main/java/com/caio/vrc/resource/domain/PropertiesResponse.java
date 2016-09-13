package com.caio.vrc.resource.domain;

import java.util.Collection;

public class PropertiesResponse {

	private final long foundProperties;
	private final Collection<PropertyResponse> properties;

	public PropertiesResponse(long foundProperties, Collection<PropertyResponse> properties) {
		this.foundProperties = foundProperties;
		this.properties = properties;
	}

	public long getFoundProperties() {
		return foundProperties;
	}

	public Collection<PropertyResponse> getProperties() {
		return properties;
	}

}
