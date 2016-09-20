package com.bbs.exception;

import com.bbs.util.ApiJsonResult;
import com.bbs.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The request you send to server is not found")
public class NotFoundRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private ApiJsonResult result;

	public NotFoundRequestException(String message) {
		super(message);
		this.result = new ApiJsonResult();
		this.result.setCode(Constants.JSON_RESULT.NOT_FOUND);
		this.result.setMessage(message);
	}

	public ApiJsonResult getResult() {
		return result;
	}

	public void setResult(ApiJsonResult result) {
		this.result = result;
	}
}
