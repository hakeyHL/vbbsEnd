package com.bbs.exception;

import com.bbs.util.ApiJsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "The request you send to Server was failed")
public class FailedReqeustException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ApiJsonResult result;

	public FailedReqeustException(ApiJsonResult result) {
		super(result.getMessage());
		this.result = result;
	}

	public ApiJsonResult getResult() {
		return result;
	}

	public void setResult(ApiJsonResult result) {
		this.result = result;
	}
}


