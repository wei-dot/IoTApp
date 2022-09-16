package com.iotApp.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.iotApp.R
import com.iotApp.model.GetModeKeyDataInfo
import com.iotApp.model.UserInfo

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USERNAME = "username"
        const val EMAIL = "email"
        const val USER_TOKEN = "user_token"
        const val NOW_FAMILY = "now_family"
        const val NOW_FAMILY_MEMBER = "now_family_member"
        const val NOW_FAMILY_ID = "now_family_id"
        const val MYOWNFAMILY = "my_own_family"
        val MODE_KEY_NAME: ArrayList<GetModeKeyDataInfo> = ArrayList()
    }

    /** SessionManager 使用者資訊 */
    fun saveUserInfo(userInfo: UserInfo) {
        prefs.edit().putString(USERNAME, userInfo.username).apply()
        prefs.edit().putString(EMAIL, userInfo.email).apply()
    }

    fun fetchUserInfo(): UserInfo? {
        val username = prefs.getString(USERNAME, null)
        val email = prefs.getString(EMAIL, null)
        if (username != null && email != null) {
            return UserInfo(username, "", "", email)
        }
        return null

    }


    /** SessionManager 使用者Token */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }


    /**
     * Function to save family id
     */

    fun saveFamilyName(familyName: String?) {
        val editor = prefs.edit()
        editor.putString(NOW_FAMILY, familyName)
        editor.apply()
    }

    fun saveFamilyId(familyId: String?) {
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

    fun fetchFamilyId(): String? {
        return prefs.getString(NOW_FAMILY_ID, null)
    }

    /**
     * Function to clear family id token
     */

    fun clearFamilyName() {
        val editor = prefs.edit()
        editor.remove(NOW_FAMILY)
        editor.apply()
    }

    fun clearFamilyId() {
        val editor = prefs.edit()
        editor.remove(NOW_FAMILY_ID)
        editor.apply()
    }

    /**
     * Function to store family members
     */
    @SuppressLint("CommitPrefEdits")
    fun storeFamilyMembers(familyMembers: ArrayList<String>) {
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

    fun clearFamilyMembers() {
        val editor = prefs.edit()
        editor.remove(NOW_FAMILY_MEMBER)
        editor.apply()
    }

    fun saveMyOwnFamily(familyIdList: List<String>?) {
        val editor = prefs.edit()
        editor.putStringSet(MYOWNFAMILY, familyIdList?.toSet())
        editor.apply()
    }

    fun fetchMyOwnFamily(): Set<String>? {
        return prefs.getStringSet(MYOWNFAMILY, null)
    }


    // Function to save mode key data
    fun saveModeKeyData(modeKeyData: ArrayList<GetModeKeyDataInfo>) {
        MODE_KEY_NAME.clear()
        MODE_KEY_NAME.addAll(modeKeyData)
    }

    // Function to fetch mode key data
    fun fetchModeKeyData(): ArrayList<GetModeKeyDataInfo> {
        return MODE_KEY_NAME
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    fun storeRequestUserName(userName: String) {
        val editor = prefs.edit()
        editor.putString("requestUserName", userName)
        editor.apply()
    }

    fun fetchRequestUserName(): String? {
        return prefs.getString("requestUserName", null)
    }
}