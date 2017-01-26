package com.mastertechsoftware.twitteriffic.server;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.mastertechsoftware.twitteriffic.models.Tweet;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Main TweetProvider.
 */
public class ServerTweetProvider implements TweetProvider {

	public static final String TAG = "ServerTweetProvider";
	private final Context context;
	private final AssetManager assetManager;

	/**
	 * Constructor. Provide a context for accessing files
	 * @param context
	 */
	public ServerTweetProvider(Context context) {
		this.context = context;
		assetManager = context.getAssets();
	}

	/**
	 * Get the current list of tweets
	 * @return List of Tweets
	 */
	@Override
	public List<Tweet> getTweets() {
		InputStream inputStream;
		try {
			// First try our file where we will be storing the tweets
			final File tweetFile = getTweetFile();
			if (tweetFile.exists()) {
				inputStream = new FileInputStream(tweetFile);
			} else {
				inputStream = assetManager.open("tweets.json");
			}
			final List<Tweet> tweets = new GsonBuilder().create().fromJson(new InputStreamReader(inputStream),
					new TypeToken<List<Tweet>>() {
					}.getType());
			// If it doesn't exist, create it now
			if (!tweetFile.exists()) {
				writeTweets(tweetFile, tweets);
			}
			return tweets;
		} catch (IOException e) {
			Log.e(TAG, "Problems reading tweets", e);
		}
		return null;
	}

	/**
	 * Get the file for where we store our tweets
	 * @return
	 */
	@NonNull
	private File getTweetFile() {
		return new File(context.getCacheDir(), "tweets.json");
	}

	/**
	 * Write out our tweets
	 * @param tweetFile
	 * @param tweets
	 * @throws IOException
	 */
	private void writeTweets(File tweetFile, List<Tweet> tweets) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(tweetFile);
		final String json = new GsonBuilder().create().toJson(tweets);
		fileOutputStream.write(json.getBytes());
		fileOutputStream.close();
	}

	/**
	 * Delete all tweets
	 */
	public void clearData() {
		final File tweetFile = getTweetFile();
		if (tweetFile.exists()) {
			if (!tweetFile.delete())  {
				Log.e("ServerTweetProvider", "Could not delete tweet file");
			}
		}
	}

	/**
	 * Get the current list of tweets based on timestamps
	 * @param timestamp
	 * @return
	 */
	@Override
	public List<Tweet> getTweets(long timestamp) {
		final List<Tweet> tweets = getTweets();
		if (tweets != null) {
			List<Tweet> afterTimeStampTweets = new ArrayList<>();
			for (Tweet tweet : tweets) {
				if (tweet.getTimestamp() >= timestamp) {
					afterTimeStampTweets.add(tweet);
				}
			}
			return afterTimeStampTweets;
		}
		return null;
	}

	/**
	 * Post a new tweet
	 * @param tweet
	 * @return true if successful
	 */
	@Override
	public boolean postTweet(Tweet tweet) {
		final List<Tweet> tweets = getTweets();
		if (tweets != null) {
			tweets.add(tweet);
			final File tweetFile = getTweetFile();
			try {
				writeTweets(tweetFile, tweets);
			} catch (IOException e) {
				Log.e(TAG, "Problems posting tweet", e);
			}
		}
		return true;
	}
}
