package com.mastertechsoftware.twitteriffic.server;

/**
 * Response Object for Callbacks
 */
public class Response<T> {
	private String error;
	private T data;
	private boolean isSuccessful;

	/**
	 * Constructor with a successful result
	 * @param data
	 */
	public Response(T data) {
		this.data = data;
		isSuccessful = true;
	}

	/**
	 * Constructor with the error
	 * @param error
	 */
	public Response(String error) {
		this.error = error;
		isSuccessful = false;
	}

	public T getData() { return data; }

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public String getError() {
		return error;
	}

	@Override
	public String toString() {
		return "Response{" +
				"data=" + data +
				", error='" + error + '\'' +
				", isSuccessful=" + isSuccessful +
				'}';
	}
}
