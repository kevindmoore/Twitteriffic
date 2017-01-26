package com.mastertechsoftware.twitteriffic.adapters;

import com.mastertechsoftware.twitteriffic.R;
import com.mastertechsoftware.twitteriffic.activities.MainActivity;
import com.mastertechsoftware.twitteriffic.models.Tweet;
import com.mastertechsoftware.twitteriffic.utils.Prefs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Adapter for list of Tweets
 */
public class MainAdapter extends RecyclerView.Adapter<TwitterHolder> {
	public static final int RECIPIENT_TYPE = 101;
	public static final int SENDER_TYPE = 102;
	private List<Tweet> tweets;
	private final String userName;

	/**
	 * Pass in our initial list of tweets
	 * Get the user's name to compare with tweets
	 * @param tweets
	 */
	public MainAdapter(List<Tweet> tweets) {
		this.tweets = tweets;
		userName = Prefs.getPrefs().getString(MainActivity.USER_NAME);
	}

	/**
	 * Create the View holder based on the type (user sent or recievied)
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public TwitterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case RECIPIENT_TYPE:
				return new TwitterHolder(LayoutInflater.from(parent.getContext())
						.inflate(R.layout.message_bubble_in, parent, false));
			default:
				return new TwitterHolder(LayoutInflater.from(parent.getContext())
						.inflate(R.layout.message_bubble_out, parent, false));

		}
	}

	/**
	 * get the type for the given position. Is the user receiving or sending
	 * @param position
	 * @return
	 */
	@Override
	public int getItemViewType(int position) {
		final Tweet tweet = tweets.get(position);
		if (userName.equalsIgnoreCase(tweet.getRecipientName())) {
			return RECIPIENT_TYPE;
		}
		return SENDER_TYPE;
	}

	/**
	 * Bind the holder to the fields
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(TwitterHolder holder, int position) {
		final Tweet tweet = tweets.get(position);
		holder.setName(tweet.getSenderName());
		holder.setConversation(tweet.getText());
	}

	/**
	 * Return how many tweets we currently have
	 * @return
	 */
	@Override
	public int getItemCount() {
		return tweets.size();
	}

	/**
	 * Add a new tweet after posting
	 * @param tweet
	 */
	public void addTweet(Tweet tweet) {
		tweets.add(tweet);
		notifyItemInserted(tweets.size()-1);
	}

	/**
	 * Replace all tweets
	 * @param tweets
	 */
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
		notifyDataSetChanged();
	}

	/**
	 * Add a new set of tweets
	 * @param tweets
	 */
	public void addTweets(List<Tweet> tweets) {
		this.tweets.addAll(tweets);
		notifyDataSetChanged();
	}

	public List<Tweet> getTweets() {
		return tweets;
	}
}
