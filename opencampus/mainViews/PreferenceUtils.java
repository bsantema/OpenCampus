package com.ss4.opencampus.mainViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Axel Zumwalt
 *
 * Class to hold methods to access shared preferences.
 */
public class PreferenceUtils {

    public static final String PREFERENCE_FILE_KEY = "OpenCampusPreference";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_REVIEW_MESSAGE_LIST = "review_message_list";

    /**
     * Preference Utils constructor
     */
    public PreferenceUtils() {}

    /**
     * Takes a userId, and adds it to shared preferences under the key user_id
     * @param id
     * @param context
     */
    public static void saveUserId(int id, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(KEY_USER_ID, id);
        prefsEditor.apply();
    }

    public static int getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public static void addReviewMessageList(ArrayList<ReviewMessage> messageList, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();

        prefsEditor.putString(KEY_REVIEW_MESSAGE_LIST, gson.toJson(messageList));
        prefsEditor.apply();
    }

    public static List<ReviewMessage> getReviewMessageList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(prefs.getString(KEY_REVIEW_MESSAGE_LIST, ""), new TypeToken<List<ReviewMessage>>() {}.getType());
    }

    public static void deleteMessageList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(KEY_REVIEW_MESSAGE_LIST, "");
        prefsEditor.apply();
    }
}