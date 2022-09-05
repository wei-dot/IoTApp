package com.iotApp.repository

import android.content.Context
import android.content.SharedPreferences
import com.iotApp.R

//import com.iotApp.api.SessionManager

object SessionManager {

    private const val USER_TOKEN = "user_token"
    private const val USERNAME = "username"
    private const val EMAIL = "email"
    private const val NOW_FAMILY = "now_family"
    private const val NOW_FAMILY_ID = "now_family_id"
    private const val NOW_FAMILY_MEMBERS = "now_family_members"
    private const val MY_OWN_FAMILY = "my_own_family"
    private const val MODE_KEY_NAME = "mode_key_name"

    fun saveAuthToken(context: Context, token: String) = saveString(context, USER_TOKEN, token)
    fun getToken(context: Context): String? = getString(context, USER_TOKEN)

    fun saveUsername(context: Context, username: String) = saveString(context, USERNAME, username)
    fun getUsername(context: Context): String? = getString(context, USERNAME)
    fun saveEmail(context: Context, email: String) = saveString(context, EMAIL, email)
    fun getEmail(context: Context): String? = getString(context, EMAIL)

    /**
     * 保存、获取、清除当前家庭名稱
     */
    fun saveFamilyName(context: Context, familyName: String) =
        saveString(context, NOW_FAMILY, familyName)

    fun getFamilyName(context: Context): String? = getString(context, NOW_FAMILY)
    fun clearFamilyName(context: Context) = clearString(context, NOW_FAMILY)


    /**
     * 保存、获取、清除当前家庭ID
     */
    fun saveFamilyId(context: Context, familyId: String) =
        saveString(context, NOW_FAMILY_ID, familyId)

    fun getFamilyId(context: Context): String? = getString(context, NOW_FAMILY_ID)
    fun clearFamilyId(context: Context) = clearString(context, NOW_FAMILY_ID)

    /**
     * 保存、获取、清除当前家庭成员
     */
    fun saveFamilyMembers(context: Context, familyMembers: ArrayList<String>) =
        saveStringSet(context, NOW_FAMILY_MEMBERS, familyMembers)

    fun getFamilyMembers(context: Context): ArrayList<String>? =
        getStringSet(context, NOW_FAMILY_MEMBERS)?.toCollection(ArrayList())

    fun clearFamilyMembers(context: Context) = clearString(context, NOW_FAMILY_MEMBERS)

    /**
     * 保存、获取、清除自己的家庭
     */
    fun saveMyOwnFamily(context: Context, myOwnFamily: List<String>) =
        saveStringSet(context, MY_OWN_FAMILY, myOwnFamily)

    fun getMyOwnFamily(context: Context): ArrayList<String>? =
        getStringSet(context, MY_OWN_FAMILY)?.toCollection(ArrayList())

    fun clearMyOwnFamily(context: Context) = clearString(context, MY_OWN_FAMILY)

    /**
     * 保存、获取、清除組合建
     */
    fun saveModeKeyName(context: Context, modeKeyName: ArrayList<String>) =
        saveStringSet(context, MODE_KEY_NAME, modeKeyName)

    fun getModeKeyName(context: Context): ArrayList<String>? =
        getStringSet(context, MODE_KEY_NAME)?.toCollection(ArrayList())

    fun clearModeKeyName(context: Context) = clearString(context, MODE_KEY_NAME)


//    fun saveModeKeyData(modeKeyData: ArrayList<GetModeKeyDataInfo>) {
//        SessionManager.MODE_KEY_NAME.clear()
//        SessionManager.MODE_KEY_NAME.addAll(modeKeyData)
//    }


    fun clearAllData(context: Context) {
        val editor =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .edit()
        editor.clear()
        editor.apply()
    }

    private fun saveString(context: Context, key: String, value: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()

    }

    private fun saveStringSet(context: Context, key: String, values: List<String>) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putStringSet(key, values.toSet())
        editor.apply()
    }


    private fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    private fun getStringSet(context: Context, key: String): Set<String>? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getStringSet(key, null)
    }

    private fun clearString(context: Context, key: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()
    }
}
