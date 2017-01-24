package com.mastertechsoftware.twitteriffic.models;

/**
 * Model for a Tweet
 */
public class Tweet {
	private String id;
	private String text;
	private long timestamp;

	public Tweet() {
	}

	public Tweet(String id, String text) {
		this.id = id;
		this.text = text;
		timestamp = System.currentTimeMillis();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
