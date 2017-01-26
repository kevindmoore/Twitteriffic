package com.mastertechsoftware.twitteriffic.server;

import com.mastertechsoftware.tasker.DefaultTask;
import com.mastertechsoftware.tasker.Tasker;
import com.mastertechsoftware.twitteriffic.models.Login;
import com.mastertechsoftware.twitteriffic.models.Tweet;

import java.util.List;

/**
 * API Calls to server for twitter information
 */
public class ServerAPI {
	public static final String USERNAME = "test";
	public static final String PASSWORD = "password";
	public static final int SLEEP_TIME = 3000;
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
	public void login(final Login login, final ServerResponseCallback<Login> callback) {
		// Simulate Network call
		Tasker.create().addTask(new DefaultTask() {
			@Override
			public Object run() {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
				}
				return null;
			}
		}).addUITask(new DefaultTask() {
			@Override
			public Object run() {
				if (USERNAME.equalsIgnoreCase(login.getUsername()) && PASSWORD.equalsIgnoreCase(login.getPassword())) {
					callback.onSuccess(new Response<Login>(login));
				} else {
					callback.onFailure(new Response<Login>("Invalid Username or password"));
				}
				return null;
			}
		}).run();
	}

	/**
	 * Get the full list of tweets.
	 * @param callback
	 */
	public void getTweets(final ServerResponseCallback<List<Tweet>> callback) {
		if (tweetProvider == null) {
			throw new IllegalStateException("TweetProvider not provided");
		}
		// Simulate Network call
		Tasker.create().addTask(new DefaultTask() {
			@Override
			public Object run() {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
				}
				final List<Tweet> tweets = tweetProvider.getTweets();
				return tweets;
			}
		}).addUITask(new DefaultTask() {
			@Override
			public Object run() {
				if (getPreviousResult() == null) {
					callback.onFailure(new Response<List<Tweet>>("Tweets not found"));
				} else {
					callback.onSuccess(new Response<List<Tweet>>((List<Tweet>)getPreviousResult()));
				}
				return null;
			}
		}).run();
	}

	/**
	 * Get the list of tweets with a timestamp greater than or equal to the given stamp
	 * @param timestamp
	 * @param callback
	 */
	public void getTweets(final long timestamp, final ServerResponseCallback<List<Tweet>> callback) {
		if (tweetProvider == null) {
			throw new IllegalStateException("TweetProvider not provided");
		}
		// Simulate Network call
		Tasker.create().addTask(new DefaultTask() {
			@Override
			public Object run() {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
				}
				final List<Tweet> tweets = tweetProvider.getTweets(timestamp);
				return tweets;
			}
		}).addUITask(new DefaultTask() {
			@Override
			public Object run() {
				if (getPreviousResult() == null) {
					callback.onFailure(new Response<List<Tweet>>("Tweets not found"));
				} else {
					callback.onSuccess(new Response<List<Tweet>>((List<Tweet>)getPreviousResult()));
				}
				return null;
			}
		}).run();
	}


	/**
	 * Post a tweet to the provider
	 * @param tweet
	 * @param callback
	 */
	public void postTweet(final Tweet tweet, final ServerResponseCallback<Tweet> callback) {
		if (tweetProvider == null) {
			throw new IllegalStateException("TweetProvider not provided");
		}
		// Simulate Network call
		Tasker.create().addTask(new DefaultTask() {
			@Override
			public Object run() {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
				}
				return tweetProvider.postTweet(tweet);
			}
		}).addUITask(new DefaultTask() {
			@Override
			public Object run() {
				if (((Boolean)getPreviousResult())) {
					callback.onSuccess(new Response<Tweet>(tweet));
				} else {
					callback.onFailure(new Response<Tweet>("Could not post tweet"));
				}
				return null;
			}
		}).run();

	}
}
