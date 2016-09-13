package com.caio.vrc.domain;

import java.io.Serializable;

public class Province extends Rectangle implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public Province(String name, Position upperLeft, Position bottomRight) {
		super(upperLeft, bottomRight);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Province[");
		sb.append("name=").append(name);
		sb.append(",upperLeft=").append(upperLeft);
		sb.append(",bottomRight=").append(bottomRight);
		sb.append("]");
		return sb.toString();
	}

}
