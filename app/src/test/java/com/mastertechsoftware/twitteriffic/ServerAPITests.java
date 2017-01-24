package com.mastertechsoftware.twitteriffic;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.mastertechsoftware.twitteriffic.models.Login;
import com.mastertechsoftware.twitteriffic.models.Tweet;
import com.mastertechsoftware.twitteriffic.server.Response;
import com.mastertechsoftware.twitteriffic.server.ServerAPI;
import com.mastertechsoftware.twitteriffic.server.ServerResponseCallback;
import com.mastertechsoftware.twitteriffic.server.TweetProvider;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit tests for the Server API
 */
public class ServerAPITests {

	private ServerAPI serverAPI;

	/**
	 * Always create a server API class and it's provider
	 */
	@Before
	public void init() {
		serverAPI = new ServerAPI();
		serverAPI.setTweetProvider(new MockTweetProvider());
	}

	/**
	 * test
	 */
	@Test
	public void loginSuccess() {
		Login login = new Login("test", "password");
		serverAPI.login(login, new ServerResponseCallback<Login>() {
			@Override
			public void onSuccess(Response<Login> response) {
				assertTrue("Response not successful", response.isSuccessful());
			}

			@Override
			public void onFailure(Response<Login> response) {
				fail("Failure with response " + response);
			}
		});
	}

	@Test
	public void loginFailure() {
		Login login = new Login("test2", "password");
		serverAPI.login(login, new ServerResponseCallback<Login>() {
			@Override
			public void onSuccess(Response<Login> response) {
				fail("Received success with response " + response);
			}

			@Override
			public void onFailure(Response<Login> response) {
				assertFalse("Response successful", response.isSuccessful());
			}
		});
	}

	@Test
	public void getTweetSuccess() {
		serverAPI.getTweets(new ServerResponseCallback<List<Tweet>>() {
			@Override
			public void onSuccess(Response<List<Tweet>> response) {
				assertEquals(4, response.getData().size());
			}

			@Override
			public void onFailure(Response<List<Tweet>> response) {
				fail("Received failure response " + response);
			}
		});
	}

	@Test
	public void getTweetTimestampSuccess() {
		serverAPI.getTweets(1485300000, new ServerResponseCallback<List<Tweet>>() {
			@Override
			public void onSuccess(Response<List<Tweet>> response) {
				assertEquals(2, response.getData().size());
			}

			@Override
			public void onFailure(Response<List<Tweet>> response) {
				fail("Received failure response " + response);
			}
		});
	}

	@Test
	public void postTweetSuccess() {
		serverAPI.postTweet(new Tweet("00005", "Sample Tweet 5"), new ServerResponseCallback<Tweet>() {
			@Override
			public void onSuccess(Response<Tweet> response) {
				assertTrue("Post failed", response.isSuccessful());
			}

			@Override
			public void onFailure(Response<Tweet> response) {
				fail("Received failure response " + response);
			}
		});
	}

	/**
	 * Mock Tweet Provider. Return tweets from json file
	 */
	class MockTweetProvider implements TweetProvider {

		@Override
		public List<Tweet> getTweets() {
			final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tweets.json");
			if (inputStream == null) {
				return null;
			} else {
				final List<Tweet> tweets = new GsonBuilder().create().fromJson(new InputStreamReader(inputStream),
						new TypeToken<List<Tweet>>() {
						}.getType());
				return tweets;
			}
		}

		@Override
		public List<Tweet> getTweets(long timestamp) {
			final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tweets.json");
			if (inputStream == null) {
				return null;
			} else {
				final List<Tweet> tweets = new GsonBuilder().create().fromJson(new InputStreamReader(inputStream),
						new TypeToken<List<Tweet>>() {
						}.getType());
				List<Tweet> afterTimeStampTweets = new ArrayList<>();
				for (Tweet tweet : tweets) {
					if (tweet.getTimestamp() >= timestamp) {
						afterTimeStampTweets.add(tweet);
					}
				}
				return afterTimeStampTweets;
			}
		}

		@Override
		public boolean postTweet(Tweet tweet) {
			return true;
		}
	}
}
