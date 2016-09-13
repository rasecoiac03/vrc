package com.caio.vrc.exception;

import com.caio.vrc.util.HttpStatus;

public class VrcInternalErrorException extends VrcException {

	private static final long serialVersionUID = 4785353053246486011L;

	public VrcInternalErrorException(Throwable e, String message) {
		super(e, HttpStatus.INTERNAL_SERVER_ERROR, "generic error");
	}

	public VrcInternalErrorException(Throwable e) {
		super(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
