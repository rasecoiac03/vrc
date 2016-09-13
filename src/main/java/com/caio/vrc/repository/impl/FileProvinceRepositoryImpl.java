package com.caio.vrc.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import com.caio.vrc.domain.Position;
import com.caio.vrc.domain.Province;
import com.caio.vrc.repository.ProvinceRepository;

public class FileProvinceRepositoryImpl implements ProvinceRepository {

	private static final Collection<Province> provinces = createProvinces();

	public FileProvinceRepositoryImpl() {
	}

	@SuppressWarnings("unchecked")
	private static Collection<Province> createProvinces() {
		Collection<Province> p = new ArrayList<>();
		Map<String, Object> provinces = FileRepositoryUtil.getProvinces();
		for (String name : provinces.keySet()) {
			Map<String, Object> province = (Map<String, Object>) provinces.get(name);
			Map<String, Object> boundaries = (Map<String, Object>) province.get("boundaries");
			Map<String, Object> upperLeft = (Map<String, Object>) boundaries.get("upperLeft");
			Map<String, Object> bottomRight = (Map<String, Object>) boundaries.get("bottomRight");
			p.add(new Province(name, new Position(toInt(upperLeft.get("x")), toInt(upperLeft.get("y"))),
					new Position(toInt(bottomRight.get("x")), toInt(bottomRight.get("y")))));

		}
		return p;
	}

	private static int toInt(Object value) {
		return new BigDecimal(value.toString()).intValue();
	}

	@Override
	public Collection<Province> get(Position position) {
		try {
			return provinces.stream().filter(p -> inside(p, position)).collect(Collectors.toList());
		} catch (Exception e) {
			return Collections.<Province>emptyList();
		}
	}

	private boolean inside(Province p, Position position) {
		int x = position.getX();
		int y = position.getY();
		return (x >= p.getUpperLeft().getX() && x <= p.getBottomRight().getX()) &&
				(y >= p.getBottomRight().getY() && y <= p.getUpperLeft().getY());
	}

}
