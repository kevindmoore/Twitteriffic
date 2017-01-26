package com.mastertechsoftware.twitteriffic.adapters;

import com.mastertechsoftware.twitteriffic.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Holder for a Tweet Row
 */
public class TwitterHolder extends RecyclerView.ViewHolder {
	private TextView nameView;
	private TextView messageView;


	public TwitterHolder(View itemView) {
		super(itemView);
		nameView = (TextView) itemView.findViewById(R.id.name);
		messageView = (TextView) itemView.findViewById(R.id.message);
	}

	public void setName(String name) {
		nameView.setText(name);
	}

	public void setConversation(String conversation) {
		messageView.setText(conversation);
	}

}
