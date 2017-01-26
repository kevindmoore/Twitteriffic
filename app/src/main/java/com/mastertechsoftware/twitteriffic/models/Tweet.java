package com.mastertechsoftware.twitteriffic.models;

import com.mastertechsoftware.easysqllibrary.sql.DefaultReflectTable;

/**
 * Model for a Tweet
 */
public class Tweet extends DefaultReflectTable {
	private String senderName;
	private String recipientName;
	private String text;
	private long timestamp;

	public Tweet() {
	}

	public Tweet(String text) {
		this.text = text;
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

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
}
