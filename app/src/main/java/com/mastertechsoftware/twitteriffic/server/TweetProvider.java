package com.mastertechsoftware.twitteriffic.server;

import com.mastertechsoftware.twitteriffic.models.Tweet;

import java.util.List;

/**
 * Tweet Provider interface. Used to provide tweets from
 * different places depending on whether it is coming from unit tests
 * or server data
 */
public interface TweetProvider {
	List<Tweet> getTweets();
	List<Tweet> getTweets(long timestamp);
	boolean postTweet(Tweet tweet);
}
