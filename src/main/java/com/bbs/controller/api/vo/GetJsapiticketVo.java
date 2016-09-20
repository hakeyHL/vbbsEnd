package com.bbs.controller.api.vo;
/**
 * jsapi_ticket
 * @author Enhon
 *
 */
public class GetJsapiticketVo {

	private String request_client_id;
	private String nonce_str;
	private String sign;
	private String createtime;
	private String action;
	private String valueId;
	public String getRequest_client_id() {
		return request_client_id;
	}
	public void setRequest_client_id(String request_client_id) {
		this.request_client_id = request_client_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getValueId() {
		return valueId;
	}
	public void setValueId(String valueId) {
		this.valueId = valueId;
	}
	
	
}
