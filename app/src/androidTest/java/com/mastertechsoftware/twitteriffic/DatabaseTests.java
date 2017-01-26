package com.mastertechsoftware.twitteriffic;

import com.mastertechsoftware.easysqllibrary.sql.DatabaseHelper;
import com.mastertechsoftware.easysqllibrary.sql.DatabaseManager;
import com.mastertechsoftware.twitteriffic.models.Tweet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

/**
 * This class tests all the database calls that we would need to make
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTests {

	public static final String DB_NAME = "Tweets";
	private DatabaseHelper databaseHelper;

	@Before
	public void setup() {
		Context appContext = InstrumentationRegistry.getTargetContext();
		DatabaseManager.getInstance(appContext);// Needs a context
		databaseHelper = new DatabaseHelper(DB_NAME);
		// Create the database
		databaseHelper.createDatabase("Tweet", Tweet.class);
		databaseHelper.removeAll(Tweet.class);
	}

	@After
	public void tearDown() {
		databaseHelper.removeAll(Tweet.class);
	}

	@Test
	public void oneTweetAdded() {
		databaseHelper.add(Tweet.class, new Tweet("test message"));
		assertEquals(1, databaseHelper.getAll(Tweet.class).size());
	}

	@Test
	public void twoTweetsAdded() {
		databaseHelper.add(Tweet.class, new Tweet("test message"));
		databaseHelper.add(Tweet.class, new Tweet("test message2"));
		assertEquals(2, databaseHelper.getAll(Tweet.class).size());
	}

	@Test
	public void addOneDeleteOneTweet() {
		final Tweet tweet = new Tweet("test message");
		final long id = databaseHelper.add(Tweet.class, tweet);
		tweet.setId((int)id);
		databaseHelper.delete(Tweet.class, tweet);
		assertEquals(0, databaseHelper.getAll(Tweet.class).size());
	}

	@Test
	public void updateOneTweet() {
		final Tweet tweet = new Tweet("test message");
		final long id = databaseHelper.add(Tweet.class, tweet);
		tweet.setId((int)id);
		tweet.setText("test 2 message");
		databaseHelper.update(Tweet.class, tweet);
		final Tweet actual = (Tweet)databaseHelper.get(Tweet.class, tweet.getId());
		assertEquals("test 2 message", actual.getText());
	}
}
