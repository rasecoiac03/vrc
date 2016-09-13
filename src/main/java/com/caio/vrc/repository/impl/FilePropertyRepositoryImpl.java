package com.caio.vrc.repository.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.caio.vrc.domain.Position;
import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.caio.vrc.domain.Rectangle;
import com.caio.vrc.repository.PropertyRepository;

/**
 * File property repository implementation. Use a static file to load properties.
 * 
 * @author caio.silva
 * @see {@link PropertyRepository}
 */
public class FilePropertyRepositoryImpl implements PropertyRepository {

	private static AtomicLong sequence;
	private static Properties properties;

	public FilePropertyRepositoryImpl() {
		this(FileRepositoryUtil.getSequence(), FileRepositoryUtil.getProperties());
	}

	public FilePropertyRepositoryImpl(AtomicLong sequence, Properties properties) {
		FilePropertyRepositoryImpl.sequence = sequence;
		FilePropertyRepositoryImpl.properties = properties;
	}

	public static class Properties implements Serializable {

		private static final long serialVersionUID = 8616848869720449795L;

		private long totalProperties;
		private final List<Property> properties;

		public Properties(long totalProperties, List<Property> properties) {
			this.totalProperties = totalProperties;
			this.properties = properties;
		}

		public long getTotalProperties() {
			return totalProperties;
		}

		public Collection<Property> getProperties() {
			return properties;
		}

		public void add(Property property) {
			this.properties.add(property);
			this.totalProperties += 1;
		}

	}

	@Override
	public Property add(Property property) {
		property.setId(sequence.incrementAndGet());
		properties.add(property);
		return property;
	}

	@Override
	public Property get(int id) {
		try {
			return properties.getProperties().parallelStream().filter(p -> id == p.getId()).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Collection<Property> find(PropertyFilter propertyFilter) {
		Rectangle rectangle = new Rectangle(new Position(propertyFilter.getAx(), propertyFilter.getAy()),
				new Position(propertyFilter.getBx(), propertyFilter.getBy()));
		return properties.getProperties().parallelStream().filter(p -> inside(p, rectangle))
				.collect(Collectors.toList());
	}

	private boolean inside(Property p, Rectangle r) {
		int x = p.getLat();
		int y = p.getLon();
		return (x >= r.getUpperLeft().getX() && x <= r.getBottomRight().getX()) &&
				(y >= r.getBottomRight().getY() && y <= r.getUpperLeft().getY());
	}

}
