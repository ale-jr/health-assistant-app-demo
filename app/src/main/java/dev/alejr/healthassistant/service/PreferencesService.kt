package dev.alejr.healthassistant.service

import dev.alejr.healthassistant.model.SignInResponse
import android.content.Context
import android.content.SharedPreferences


val KEY = "authentication_preferences"

fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
}

fun saveUser(context: Context, user: SignInResponse) {
    val sharedPreferences = getSharedPreferences(context);
    with(sharedPreferences.edit()) {
        putString("token", user.token)
        putString("name", user.name)
        apply()
    }
}

fun getUser(context: Context): SignInResponse? {
    val sharedPreferences = getSharedPreferences(context);
    val token = sharedPreferences.getString("token", null)
    val name = sharedPreferences.getString("name", null)

    if (token.isNullOrEmpty() || name.isNullOrEmpty()) return null

    return SignInResponse(
        token,
        name
    )
}

fun deleteUser(context: Context) {
    val sharedPreferences = getSharedPreferences(context);
    with(sharedPreferences.edit()) {
        clear()
        apply()
    }
}