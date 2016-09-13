package com.caio.vrc.resource.domain;

import java.util.Collection;

public class PropertyResponse {

	private long id;
	private int x;
	private int y;
	private String title;
	private long price;
	private String description;
	private int beds;
	private int baths;
	private long squareMeters;
	private Collection<String> provinces;

	public PropertyResponse(long id, int x, int y, String title, long price, String description, int beds, int baths,
			long squareMeters, Collection<String> provinces) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.title = title;
		this.price = price;
		this.description = description;
		this.beds = beds;
		this.baths = baths;
		this.squareMeters = squareMeters;
		this.provinces = provinces;
	}

	public long getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getTitle() {
		return title;
	}

	public long getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public int getBeds() {
		return beds;
	}

	public int getBaths() {
		return baths;
	}

	public long getSquareMeters() {
		return squareMeters;
	}

	public Collection<String> getProvinces() {
		return provinces;
	}

}
