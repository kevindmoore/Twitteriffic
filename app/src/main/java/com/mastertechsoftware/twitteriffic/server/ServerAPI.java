package com.mastertechsoftware.twitteriffic.server;

import com.mastertechsoftware.twitteriffic.models.Login;
import com.mastertechsoftware.twitteriffic.models.Tweet;

import java.util.List;

/**
 * API Calls to server for twitter information
 */
public class ServerAPI {
	public static final String USERNAME = "test";
	public static final String PASSWORD = "password";
	private TweetProvider tweetProvider;

	/**
	 * Set a tweet provider that will return a list of tweets
	 * or be able to post a tweet
	 * @param tweetProvider
	 */
	public void setTweetProvider(TweetProvider tweetProvider) {
		this.tweetProvider = tweetProvider;
	}

	/**
	 * Attempt to login using the passed in credentials and callback
	 * @param login
	 * @param callback
	 */
	public void login(Login login, ServerResponseCallback<Login> callback) {
		if (USERNAME.equalsIgnoreCase(login.getUsername()) && PASSWORD.equalsIgnoreCase(login.getPassword())) {
			callback.onSuccess(new Response<Login>(login));
		} else {
			callback.onFailure(new Response<Login>("Invalid Username or password"));
		}
	}

	/**
	 * Get the full list of tweets.
	 * @param callback
	 */
	public void getTweets(ServerResponseCallback<List<Tweet>> callback) {
		if (tweetProvider == null) {
			throw new IllegalStateException("TweetProvider not provided");
		}
		final List<Tweet> tweets = tweetProvider.getTweets();
		if (tweets == null) {
			callback.onFailure(new Response<List<Tweet>>("Tweets not found"));
		} else {
			callback.onSuccess(new Response<List<Tweet>>(tweets));
		}
	}

	/**
	 * Get the list of tweets with a timestamp greater than or equal to the given stamp
	 * @param timestamp
	 * @param callback
	 */
	public void getTweets(long timestamp, ServerResponseCallback<List<Tweet>> callback) {
		if (tweetProvider == null) {
			throw new IllegalStateException("TweetProvider not provided");
		}
		final List<Tweet> tweets = tweetProvider.getTweets(timestamp);
		if (tweets == null) {
			callback.onFailure(new Response<List<Tweet>>("Tweets not found"));
		} else {
			callback.onSuccess(new Response<List<Tweet>>(tweets));
		}

	}


	/**
	 * Post a tweet to the provider
	 * @param tweet
	 * @param callback
	 */
	public void postTweet(Tweet tweet, ServerResponseCallback<Tweet> callback) {
		if (tweetProvider == null) {
			throw new IllegalStateException("TweetProvider not provided");
		}
		if (tweetProvider.postTweet(tweet)) {
			callback.onSuccess(new Response<Tweet>(tweet));
		} else {
			callback.onFailure(new Response<Tweet>("Could not post tweet"));
		}

	}
}
