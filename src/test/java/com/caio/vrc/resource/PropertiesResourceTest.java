package com.caio.vrc.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.caio.vrc.resource.domain.PropertiesResponse;
import com.caio.vrc.resource.domain.PropertyFilterParam;
import com.caio.vrc.resource.domain.PropertyRequest;
import com.caio.vrc.resource.domain.PropertyResponse;
import com.caio.vrc.service.PropertiesService;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesResourceTest {

	@Mock
	PropertiesService propertiesService;

	PropertiesResource resource;

	@Before
	public void setup() {
		resource = new PropertiesResource(propertiesService);
	}

	@Test
	public void shouldGetAValidProperty() {
		final Long id = 123L;

		when(propertiesService.get(Mockito.anyInt())).thenReturn(newProperty(id));

		PropertyResponse propertyResponse = resource.get(id.toString());
		assertNotNull(propertyResponse);
		assertEquals(propertyResponse.getId(), id.longValue());
	}

	@Test
	public void shouldAddAProperty() {
		final Long id = 123L;

		when(propertiesService.add(Mockito.any(Property.class))).thenReturn(newProperty(id));

		PropertyResponse propertyResponse = resource.add(newPropertyRequest());
		assertNotNull(propertyResponse);
		assertEquals(propertyResponse.getId(), id.longValue());
	}

	@Test
	public void shouldFindProperties() {
		final Long id = 123L;

		when(propertiesService.find(Mockito.any(PropertyFilter.class))).thenReturn(Arrays.asList(newProperty(id)));

		PropertiesResponse response = resource.find(newFilterParam());
		assertNotNull(response);
		assertEquals(response.getProperties().iterator().next().getId(), id.longValue());
	}

	private PropertyFilterParam newFilterParam() {
		return new PropertyFilterParam("1", "2", "2", "1");
	}

	private PropertyRequest newPropertyRequest() {
		return new PropertyRequest("1", "2", "title", "123", "description", "1", "1", "200");
	}

	private Property newProperty(long id) {
		return new Property(id, "title", new BigDecimal(1), "description", 10, 10, 1, 1, 20);
	}

}
