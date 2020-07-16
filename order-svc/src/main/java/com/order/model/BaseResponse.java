/**
 * 
 */
package com.order.model;

/**
 * @author amake
 *
 */
public class BaseResponse {

	private String referenceId;
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
	public BaseResponse(String referenceId, String result, String message) {
		this.referenceId = referenceId;
		this.result = result;
		this.message = message;
	}

	/**
	 * @return the refrenceId
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * @param refrenceId the refrenceId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
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
