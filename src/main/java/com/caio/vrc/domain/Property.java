package com.caio.vrc.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import com.google.gson.annotations.SerializedName;

public class Property implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private final String title;
	private final BigDecimal price;
	private final String description;
	private final int lat;
	@SerializedName("long")
	private final int lon;
	private final int beds;
	private final int baths;
	private final int squareMeters;
	private Collection<Province> provinces;

	public Property(String title, BigDecimal price, String description, int lat, int lon, int beds,
			int baths,
			int squareMeters) {
		this(0L, title, price, description, lat, lon, beds, baths, squareMeters);
	}

	public Property(long id, String title, BigDecimal price, String description, int lat, int lon, int beds,
			int baths, int squareMeters) {
		this(id, title, price, description, lat, lon, beds, baths, squareMeters, null);
	}

	public Property(long id, String title, BigDecimal price, String description, int lat, int lon, int beds,
			int baths, int squareMeters, Collection<Province> provinces) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.description = description;
		this.lat = lat;
		this.lon = lon;
		this.beds = beds;
		this.baths = baths;
		this.squareMeters = squareMeters;
		this.provinces = provinces;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public int getLat() {
		return lat;
	}

	public int getLon() {
		return lon;
	}

	public int getBeds() {
		return beds;
	}

	public int getBaths() {
		return baths;
	}

	public int getSquareMeters() {
		return squareMeters;
	}

	public Collection<Province> getProvinces() {
		return provinces;
	}

	public void setProvinces(Collection<Province> provinces) {
		this.provinces = provinces;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Property[");
		sb.append("id=").append(id);
		sb.append(",title=").append(title);
		sb.append(",price=").append(price);
		sb.append(",description=").append(description);
		sb.append(",lat=").append(lat);
		sb.append(",lon=").append(lon);
		sb.append(",beds=").append(beds);
		sb.append(",baths=").append(baths);
		sb.append(",squareMeters=").append(squareMeters);
		sb.append(",provinces=").append(provinces);
		sb.append("]");
		return sb.toString();
	}

}
