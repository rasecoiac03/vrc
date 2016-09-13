package com.caio.vrc.service.impl;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.caio.vrc.domain.Position;
import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.caio.vrc.domain.Province;
import com.caio.vrc.exception.VrcInternalErrorException;
import com.caio.vrc.exception.VrcNotFoundException;
import com.caio.vrc.repository.PropertyRepository;
import com.caio.vrc.repository.ProvinceRepository;
import com.mongodb.MongoException;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesServiceImplTest {

	private PropertiesServiceImpl impl;

	@Mock
	private PropertyRepository propertiesRepository;

	@Mock
	private ProvinceRepository provinceRepository;

	@Before
	public void setup() {
		impl = new PropertiesServiceImpl(propertiesRepository, provinceRepository);
	}

	@Test(expected = VrcInternalErrorException.class)
	public void shouldThrowAnExceptionAddProperty() {
		doThrow(new MongoException("problem")).when(propertiesRepository).add(any(Property.class));
		impl.add(newProperty(0));
	}

	@Test
	public void shouldAddProperty() {
		Long id = 123L;
		String provinceName = "ThatsWhatSheSaid";

		when(provinceRepository.get(any(Position.class))).thenReturn(provinces(provinceName));
		when(propertiesRepository.add(any(Property.class))).thenReturn(newProperty(id));

		Property propertySaved = impl.add(newProperty(0));

		assertNotNull(propertySaved);
		assertEquals(id.longValue(), propertySaved.getId());
		assertEquals(provinceName, propertySaved.getProvinces().iterator().next().getName());
	}

	@Test(expected = VrcNotFoundException.class)
	public void shouldThrowAnExceptionWhenGettingAProperty() {
		Long id = 123L;

		when(provinceRepository.get(any(Position.class))).thenReturn(provinces("ThatsWhatSheSaid"));
		when(propertiesRepository.get(anyInt())).thenReturn(null);

		impl.get(id.intValue());
	}

	@Test
	public void shouldGetAPropertyWithNoProvinces() {
		Long id = 123L;

		when(provinceRepository.get(any(Position.class))).thenReturn(Collections.<Province>emptyList());
		when(propertiesRepository.get(anyInt())).thenReturn(newProperty(id));

		Property property = impl.get(id.intValue());

		assertNotNull(property);
		assertEquals(0, property.getProvinces().size());
	}

	@Test
	public void shouldGetAProperty() {
		Long id = 123L;
		String provinceName = "ThatsWhatSheSaid";

		when(provinceRepository.get(any(Position.class))).thenReturn(provinces(provinceName));
		when(propertiesRepository.get(anyInt())).thenReturn(newProperty(id));

		Property property = impl.get(id.intValue());

		assertNotNull(property);
		assertEquals(id.longValue(), property.getId());
		assertEquals(provinceName, property.getProvinces().iterator().next().getName());
	}

	@Test(expected = VrcNotFoundException.class)
	public void shouldThrowAnExceptionWhenTryingToFindPropertiesAndHasNoProperties() {
		when(provinceRepository.get(any(Position.class))).thenReturn(provinces("ThatsWhatSheSaid"));
		when(propertiesRepository.find(any(PropertyFilter.class))).thenReturn(null);

		impl.find(newFilterParam());
	}

	@Test(expected = VrcNotFoundException.class)
	public void shouldThrowAnExceptionWhenFindPropertiesAndPropertiesCollectionIsEmpty() {
		when(provinceRepository.get(any(Position.class))).thenReturn(provinces("ThatsWhatSheSaid"));
		when(propertiesRepository.find(any(PropertyFilter.class))).thenReturn(Collections.<Property>emptyList());

		impl.find(newFilterParam());
	}

	@Test
	public void shouldFindProperties() {
		Long id = 123L;
		String provinceName = "ThatsWhatSheSaid";

		when(provinceRepository.get(any(Position.class))).thenReturn(provinces(provinceName));
		when(propertiesRepository.find(any(PropertyFilter.class))).thenReturn(Arrays.asList(newProperty(id)));

		Collection<Property> result = impl.find(newFilterParam());

		assertNotNull(result);
		assertEquals(id.longValue(), result.iterator().next().getId());
		assertEquals(provinceName, result.iterator().next().getProvinces().iterator().next().getName());
	}

	private List<Province> provinces(String name) {
		return Arrays.asList(new Province(name, new Position(0, 1000), new Position(600, 500)));
	}

	private Property newProperty(long id) {
		return new Property(id, "title", new BigDecimal(1), "description", 10, 10, 1, 1, 20);
	}

	private PropertyFilter newFilterParam() {
		return new PropertyFilter(1, 2, 2, 1);
	}

}
