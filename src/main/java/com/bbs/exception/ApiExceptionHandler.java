package com.bbs.exception;

import com.bbs.util.ApiJsonResult;
import com.bbs.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
	@ExceptionHandler(FailedReqeustException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiJsonResult handleInvalidRequestError(FailedReqeustException ex) {
		logger.error("handleInvalidRequestError FailedReqeustException :"+ex.getMessage());
		return ex.getResult();
    }  
	
	@ExceptionHandler(NeedLoginRequestException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiJsonResult handleInvalidRequestError(NeedLoginRequestException ex) {
		logger.error("handleInvalidRequestError NeedLoginRequestException :"+ex.getMessage());
		return ex.getResult();
    } 
	
	@ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiJsonResult handleInvalidRequestError(BadRequestException ex) {
		logger.error("handleInvalidRequestError BadRequestRequestException :"+ex.getMessage());
		return ex.getResult();
    } 
	
	@ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiJsonResult handleInvalidRequestError(RuntimeException ex) {
		logger.error("handleInvalidRequestError RuntimeException :" + ex.getMessage(), ex);
		ApiJsonResult result = new ApiJsonResult();
		result.setCode(Constants.JSON_RESULT.SERVER_ERROR);
		result.setMessage("server error");
		return result;
    } 
	
	@ExceptionHandler(NotFoundRequestException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiJsonResult handleInvalidRequestError(NotFoundRequestException ex) {
		logger.error("handleInvalidRequestError  inteface not found :" + ex.getMessage());
		ApiJsonResult result = new ApiJsonResult();
		result.setCode(Constants.JSON_RESULT.NOT_FOUND);
		result.setMessage("inteface not found error");
		return result;
    } 
}
