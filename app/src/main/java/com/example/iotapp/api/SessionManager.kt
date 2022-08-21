package com.example.iotapp.api

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.iotapp.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USERNAME = "username"
        const val USER_TOKEN = "user_token"
        const val NOW_FAMILY = "now_family"
        const val NOW_FAMILY_MEMBER = "now_family_member"
        const val NOW_FAMILY_ID = "now_family_id"
    }


    fun saveUserName(userName: String) {
        prefs.edit().putString(USERNAME, userName).apply()
    }
    fun fetchUserName(): String? {
        return prefs.getString(USERNAME, null)
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Function to save family id
     */

    fun saveFamilyName(familyId: String?) {
        val editor = prefs.edit()
        editor.putString(NOW_FAMILY, familyId)
        editor.apply()
    }

    fun saveFamilyid(familyId: String?) {
        val editor = prefs.edit()
        editor.putString(NOW_FAMILY_ID, familyId)
        editor.apply()
    }

    /**
     * Function to fetch family id
     */
    fun fetchFamilyName(): String? {
        return prefs.getString(NOW_FAMILY, null)
    }

    fun fetchFamilyid(): String? {
        return prefs.getString(NOW_FAMILY_ID, null)
    }

    /**
     * Function to clear family id token
     */
    fun clearFamilyId() {
        val editor = prefs.edit()
        editor.remove(NOW_FAMILY)
        editor.apply()
    }

    /**
     * Function to store family members
     */
    @SuppressLint("CommitPrefEdits")
    fun storeFamilyMembers(familyMembers: ArrayList<String>) {
        Log.d("putIn", familyMembers.toString())
        val editor = prefs.edit()
        editor.putStringSet(NOW_FAMILY_MEMBER, familyMembers.toSet())
        editor.apply()
    }

    /**
     * Function to fetch family members
     */
    fun fetchFamilyMembers(): Set<String>? {
        return prefs.getStringSet(NOW_FAMILY_MEMBER, null)
    }

    /**
     * Function to clear auth token
     */
    fun clearAuthToken() {
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }
}