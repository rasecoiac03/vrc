package com.caio.vrc.repository.impl;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.caio.vrc.repository.impl.FilePropertyRepositoryImpl.Properties;

@RunWith(MockitoJUnitRunner.class)
public class FilePropertyRepositoryImplTest {

	private FilePropertyRepositoryImpl impl;

	@Before
	public void setup() {
		impl = new FilePropertyRepositoryImpl(new AtomicLong(3), createProperties());
	}

	private Properties createProperties() {
		Properties props = new Properties(3, new ArrayList<Property>(Arrays.asList( //
				newProperty(1, 10, 10), //
				newProperty(2, 20, 20), //
				newProperty(3, 30, 30) //
		)));
		return props;
	}

	private Property newProperty(long id, int lat, int lon) {
		return new Property(id, "title", new BigDecimal(1), "description", lat, lon, 1, 1, 20);
	}

	@Test
	public void shouldGetById() {
		Property property = impl.get(1);
		assertNotNull(property);
	}

	@Test
	public void shouldAddProperty() {
		Property property = impl.add(newProperty(0, 10, 10));
		assertNotNull(property);
		assertEquals(property.getId(), 4);
	}

	@Test
	public void shouldFindProperties() {
		Collection<Property> props = impl.find(new PropertyFilter(5, 15, 15, 5));
		assertNotNull(props);
		assertEquals(props.size(), 1);
		assertEquals(props.iterator().next().getId(), 1);
	}

}
