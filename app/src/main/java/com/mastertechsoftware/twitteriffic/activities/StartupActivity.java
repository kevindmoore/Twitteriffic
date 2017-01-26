package com.mastertechsoftware.twitteriffic.activities;

import com.mastertechsoftware.easysqllibrary.sql.DatabaseManager;
import com.mastertechsoftware.twitteriffic.utils.Prefs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.mastertechsoftware.twitteriffic.activities.MainActivity.LOGGED_IN;

public class StartupActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Prefs.setContext(getApplicationContext());
		DatabaseManager.getInstance(getApplicationContext());// Needs a context

		if (!Prefs.getPrefs().getBoolean(LOGGED_IN)) {
			startLogin();
			finish();
			return;
		}

		startMainActivity();
		finish();
	}
	private void startLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	private void startMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
