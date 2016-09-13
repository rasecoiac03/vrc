package com.caio.vrc.domain;

public class PropertyFilter {

	private final int ax;
	private final int ay;
	private final int bx;
	private final int by;

	public PropertyFilter(int ax, int ay, int bx, int by) {
		this.ax = ax;
		this.ay = ay;
		this.bx = bx;
		this.by = by;
	}

	public int getAx() {
		return ax;
	}

	public int getAy() {
		return ay;
	}

	public int getBx() {
		return bx;
	}

	public int getBy() {
		return by;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PropertyFilter[");
		sb.append("ax=").append(ax);
		sb.append(",ay=").append(ay);
		sb.append(",bx=").append(bx);
		sb.append(",by=").append(by);
		sb.append("]");
		return sb.toString();
	}

}
