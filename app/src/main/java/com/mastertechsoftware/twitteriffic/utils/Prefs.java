package com.mastertechsoftware.twitteriffic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Handle System Preferences
 */
public class Prefs {
    protected static Context mContext;
    private static Prefs singleton;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences currentSharedPreferences;

    public static Prefs getPrefs() {
        if (singleton == null) {
            singleton = new Prefs();
        }
        return singleton;
    }

	/**
     * Do we already have a Context
     * @return true if we have a context
     */
    public boolean hasContext() {
        return mContext != null;
    }

    /**
     * This should be called in your application 1x (Using the Application Context).
     * It could be called in an activity but if that activity goes away
     * then this context won't be valid in other places.
     * @param context
     */
    public static void setContext(Context context) {
        mContext = context;
    }

	/**
     * Set the preference name so that we can use a different preference file
     * @param preferenceName
     */
    public void setSharedPreferences(String preferenceName) {
        mSharedPreferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        currentSharedPreferences = mSharedPreferences;
    }

    private Prefs() {
        if (mContext == null) {
            throw new IllegalStateException("Context not set");
        }
        setupSharedPrefs();
    }

    private void setupSharedPrefs() {
        if (mSharedPreferences == null && mContext != null) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            currentSharedPreferences = mSharedPreferences;
        }
    }

    public boolean containsKey(String key) {
        return currentSharedPreferences.contains(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (!checkSharedPreferences()) {
            return false;
        }
        return currentSharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        if (!checkSharedPreferences()) {
            return false;
        }
        return currentSharedPreferences.getBoolean(key, false);
    }


    public String getString(String key, String defaultValue) {
        if (!checkSharedPreferences()) {
            return null;
        }
        return currentSharedPreferences.getString(key, defaultValue);
    }

    public String getString(String key) {
        if (!checkSharedPreferences()) {
            return null;
        }
        return currentSharedPreferences.getString(key, null);
    }


    public int getInt(String key, int defaultValue) {
        if (!checkSharedPreferences()) {
            return -1;
        }
        return currentSharedPreferences.getInt(key, defaultValue);
    }

    public int getInt(String key) {
        if (!checkSharedPreferences()) {
            return -1;
        }
        return currentSharedPreferences.getInt(key, -1);
    }


    public long getLong(String key, long defaultValue) {
        if (!checkSharedPreferences()) {
            return -1;
        }
        return currentSharedPreferences.getLong(key, defaultValue);
    }

    public long getLong(String key) {
        if (!checkSharedPreferences()) {
            return -1;
        }
        return currentSharedPreferences.getLong(key, -1);
    }


    public Float getFloat(String key) {
        if (!checkSharedPreferences()) {
            return null;
        }
        return currentSharedPreferences.getFloat(key, -1);
    }

    public Double getDouble(String key) {
        if (!checkSharedPreferences()) {
            return null;
        }
        return (double) currentSharedPreferences.getFloat(key, -1);
    }


    public void putBoolean(String key, boolean value) {
        if (!checkSharedPreferences()) {
            return;
        }
        currentSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public void putString(String key, String value) {
        if (!checkSharedPreferences()) {
            return;
        }
        currentSharedPreferences.edit().putString(key, value).commit();
    }

    public void putInt(String key, int value) {
        if (!checkSharedPreferences()) {
            return;
        }
        currentSharedPreferences.edit().putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        if (!checkSharedPreferences()) {
            return;
        }
        currentSharedPreferences.edit().putLong(key, value).commit();
    }


	public void removePref(String key) {
		if (!checkSharedPreferences()) {
			return;
		}
		currentSharedPreferences.edit().remove(key).commit();
	}

    private boolean checkSharedPreferences() {
        if (currentSharedPreferences == null) {
            setupSharedPrefs();
            if (currentSharedPreferences == null) {
                Log.e("Prefs", "Shared Preferences not set");
                return false;
            }
        }
        return true;
    }

    public void putFloat(String key, Float value) {
        if (!checkSharedPreferences()) {
            return;
        }
        currentSharedPreferences.edit().putFloat(key, value).commit();
    }

    public void putDouble(String key, Double value) {
        if (!checkSharedPreferences()) {
            return;
        }
        currentSharedPreferences.edit().putFloat(key, value.floatValue()).commit();

    }
}
