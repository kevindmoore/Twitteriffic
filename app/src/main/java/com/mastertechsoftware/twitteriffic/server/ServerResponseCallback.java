package com.mastertechsoftware.twitteriffic.server;

/**
 * Generic Server Response Callback.
 * User has to implement just 2 methods.
 * 1 for success and one for failure
 */
public interface ServerResponseCallback<T> {
	void onSuccess(Response<T> response);
	void onFailure(Response<T> response);
}
