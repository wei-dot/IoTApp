package com.example.iotapp.api

import android.content.Context
import android.content.SharedPreferences
import com.example.iotapp.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val NOW_FAMILY = "now_family"
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

    fun saveFamilyName(familyId: String) {
        val editor = prefs.edit()
        editor.putString(NOW_FAMILY, familyId)
        editor.apply()
    }

    /**
     * Function to fetch family id
     */
    fun fetchFamilyName(): String? {
        return prefs.getString(NOW_FAMILY, null)
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
    fun storeFamilyMembers(familyMembers: ArrayList<String>) {
        val editor = prefs.edit()
        editor.putStringSet(NOW_FAMILY, familyMembers.toSet())
        editor.apply()
    }

    /**
     * Function to fetch family members
     */
    fun fetchFamilyMembers(): Set<String>? {
        return prefs.getStringSet(NOW_FAMILY, null)
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