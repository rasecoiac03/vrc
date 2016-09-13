package com.caio.vrc.repository.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.caio.vrc.domain.Position;
import com.caio.vrc.domain.Province;

@RunWith(MockitoJUnitRunner.class)
public class FileProvinceRepositoryImplTest {

	private FileProvinceRepositoryImpl impl;

	@Before
	public void setup() {
		impl = new FileProvinceRepositoryImpl();
	}

	@Test
	public void shouldGetProvincesGivenPosition() {
		Collection<Province> provinces = impl.get(new Position(500, 700));
		assertNotNull(provinces);
		assertEquals(provinces.size(), 2); // Its known this position has Gode and Ruja provinces
	}

}
