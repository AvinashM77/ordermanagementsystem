/**
 * 
 */
package com.order.model;

/**
 * @author amake
 *
 */
public class BaseResponse {

	private String refrenceId;
	private String result;
	private String message;

	/**
	 * 
	 */
	public BaseResponse() {
		super();
	}

	/**
	 * @param refrenceId
	 * @param result
	 * @param message
	 */
	public BaseResponse(String refrenceId, String result, String message) {
		this.refrenceId = refrenceId;
		this.result = result;
		this.message = message;
	}

	/**
	 * @return the refrenceId
	 */
	public String getRefrenceId() {
		return refrenceId;
	}

	/**
	 * @param refrenceId the refrenceId to set
	 */
	public void setRefrenceId(String refrenceId) {
		this.refrenceId = refrenceId;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
