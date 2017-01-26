package com.mastertechsoftware.twitteriffic.activities;

import com.mastertechsoftware.easysqllibrary.sql.DatabaseHelper;
import com.mastertechsoftware.twitteriffic.R;
import com.mastertechsoftware.twitteriffic.adapters.MainAdapter;
import com.mastertechsoftware.twitteriffic.models.Tweet;
import com.mastertechsoftware.twitteriffic.server.Response;
import com.mastertechsoftware.twitteriffic.server.ServerAPI;
import com.mastertechsoftware.twitteriffic.server.ServerResponseCallback;
import com.mastertechsoftware.twitteriffic.server.ServerTweetProvider;
import com.mastertechsoftware.twitteriffic.utils.Prefs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

	public static final String LOGGED_IN = "LoggedIn";
	public static final String LAST_TIME_STAMP = "LAST_TIME_STAMP";
	public static final String USER_NAME = "USER_NAME";
	public static final String DB_NAME = "Tweets";
	public static final String RECIPIENT_NAME = "Test";
	public static final String SENDER_NAME = "Computer";
	private MainAdapter adapter;
	private RecyclerView recyclerView;
	private TextView newTweet;
	private String userName;
	private ServerTweetProvider tweetProvider;
	private ServerAPI serverAPI;
	private DatabaseHelper databaseHelper;
	private String[] responses;
	private Random responseRandomizer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		responses = getResources().getStringArray(R.array.resources);
		responseRandomizer = new Random(new Date().getTime());
		databaseHelper = new DatabaseHelper(DB_NAME);
		// Create the database
		databaseHelper.createDatabase("Tweet", Tweet.class);

		userName = Prefs.getPrefs().getString(MainActivity.USER_NAME);
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		serverAPI = new ServerAPI();
		tweetProvider = new ServerTweetProvider(this);
		serverAPI.setTweetProvider(tweetProvider);
		// Load tweet data
		loadData();
		newTweet = (TextView) findViewById(R.id.new_tweet);
		Button post = (Button) findViewById(R.id.post);
		post.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Tweet tweet = new Tweet(newTweet.getText().toString());
				tweet.setSenderName(userName);
				postTweet(tweet);
				newTweet.setText("");
				final Tweet responseTweet = new Tweet(responses[responseRandomizer.nextInt(responses.length)]);
				responseTweet.setSenderName(SENDER_NAME);
				responseTweet.setRecipientName(RECIPIENT_NAME);
				postTweet(responseTweet);
			}
		});
	}

	/**
	 * Post a new Tweet
	 */
	private void postTweet(final Tweet tweet) {
		databaseHelper.add(Tweet.class, tweet);
		adapter.addTweet(tweet);
		recyclerView.scrollToPosition(adapter.getItemCount()-1);
		serverAPI.postTweet(tweet, new ServerResponseCallback<Tweet>() {
			@Override
			public void onSuccess(Response<Tweet> response) {
				Toast.makeText(MainActivity.this, "Tweet Posted", Toast.LENGTH_LONG)
						.show();
				Prefs.getPrefs().putLong(LAST_TIME_STAMP, System.currentTimeMillis());
			}

			@Override
			public void onFailure(Response<Tweet> response) {
				Toast.makeText(MainActivity.this, "Problems posting Tweet", Toast.LENGTH_LONG)
						.show();

			}
		});
	}

	/**
	 * Load the twitter data
	 */
	private void loadData() {
		final List<Tweet> tweets = (List<Tweet>) databaseHelper.getAll(Tweet.class);
		if (tweets != null && tweets.size() > 0) {
			adapter = new MainAdapter(tweets);
			recyclerView.setAdapter(adapter);
			recyclerView.scrollToPosition(adapter.getItemCount()-1);
		}
		if (Prefs.getPrefs().containsKey(LAST_TIME_STAMP)) {
			serverAPI.getTweets(Prefs.getPrefs().getLong(LAST_TIME_STAMP), new ServerResponseCallback<List<Tweet>>() {
				@Override
				public void onSuccess(Response<List<Tweet>> response) {
					final List<Tweet> tweets = response.getData();
					if (adapter == null) {
						adapter = new MainAdapter(tweets);
						recyclerView.setAdapter(adapter);
					} else {
						adapter.addTweets(tweets);
					}
					databaseHelper.removeAll(Tweet.class);
					databaseHelper.addAll(Tweet.class, adapter.getTweets());
					recyclerView.scrollToPosition(adapter.getItemCount()-1);
					Prefs.getPrefs().putLong(LAST_TIME_STAMP, tweets.get(tweets.size()-1).getTimestamp());
				}

				@Override
				public void onFailure(Response<List<Tweet>> response) {
					Toast.makeText(MainActivity.this, "Could not get Tweets", Toast.LENGTH_LONG)
					.show();
				}
			});
		} else {
			serverAPI.getTweets(new ServerResponseCallback<List<Tweet>>() {
				@Override
				public void onSuccess(Response<List<Tweet>> response) {
					final List<Tweet> tweets = response.getData();
					if (adapter == null) {
						adapter = new MainAdapter(tweets);
						recyclerView.setAdapter(adapter);
					} else {
						adapter.setTweets(tweets);
					}
					databaseHelper.removeAll(Tweet.class);
					databaseHelper.addAll(Tweet.class, adapter.getTweets());
					recyclerView.scrollToPosition(adapter.getItemCount()-1);
				}

				@Override
				public void onFailure(Response<List<Tweet>> response) {
					Toast.makeText(MainActivity.this, "Could not get Tweets", Toast.LENGTH_LONG)
							.show();
				}
			});
		}
	}

	private void startLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_logout) {
			logout();
			return true;
		} else if (id == R.id.action_clear) {
			Prefs.getPrefs().removePref(MainActivity.LAST_TIME_STAMP);
			tweetProvider.clearData();
			logout();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void logout() {
		Prefs.getPrefs().removePref(MainActivity.LOGGED_IN);
		Prefs.getPrefs().removePref(MainActivity.USER_NAME);
		startLogin();
		finish();
	}
}
