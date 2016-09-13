package com.caio.vrc.domain;

import java.io.Serializable;

public class Rectangle implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Position upperLeft;
	protected Position bottomRight;

	public Rectangle(Position upperLeft, Position bottomRight) {
		this.upperLeft = upperLeft;
		this.bottomRight = bottomRight;
	}

	public Position getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(Position upperLeft) {
		this.upperLeft = upperLeft;
	}

	public Position getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(Position bottomRight) {
		this.bottomRight = bottomRight;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Rectangle[");
		sb.append("upperLeft=").append(upperLeft);
		sb.append(",bottomRight=").append(bottomRight);
		sb.append("]");
		return sb.toString();
	}

}
