package com.caio.vrc.repository.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@RunWith(MockitoJUnitRunner.class)
public class MongoDBPropertiesRepositoryImplTest {

	private MongoDBPropertiesRepositoryImpl impl;

	@Mock
	private MongoCollection<Document> collection;

	@Mock
	private FindIterable<Document> result;

	@Before
	public void setup() {
		impl = new MongoDBPropertiesRepositoryImpl(collection, new AtomicLong(0));
	}

	@Test
	public void shouldAddAProperty() {
		Property property = impl.add(newProperty(0L));
		assertNotNull(property);
		assertEquals(1L, property.getId()); // Because its the first test
	}

	@Test
	public void shouldGetProperty() {
		Long id = 123L;

		when(collection.find(any(Bson.class))).thenReturn(result);
		when(result.first()).thenReturn(newDocument(id));

		Property property = impl.get(id.intValue());
		assertEquals(id.longValue(), property.getId());
	}

	@Test
	public void shouldGetProperties() {

		when(collection.find(any(Bson.class))).thenReturn(null);

		Collection<Property> props = impl.find(newFilterParam());
		assertEquals(props.size(), 0);
	}

	private Document newDocument(Long id) {
		return new Document().append("id", id.intValue()) //
				.append("price", 123) //
				.append("lat", 1) //
				.append("long", 1) //
				.append("baths", 1) //
				.append("beds", 1) //
				.append("squareMeters", 1) //
		;
	}

	private Property newProperty(long id) {
		return new Property(id, "title", new BigDecimal(1), "description", 10, 10, 1, 1, 20);
	}

	private PropertyFilter newFilterParam() {
		return new PropertyFilter(1, 2, 2, 1);
	}

}
