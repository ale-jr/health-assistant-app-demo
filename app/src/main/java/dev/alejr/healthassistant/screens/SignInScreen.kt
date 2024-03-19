package dev.alejr.healthassistant.screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import dev.alejr.healthassistant.components.SignInScreenForm
import dev.alejr.healthassistant.model.SignInPayload
import dev.alejr.healthassistant.model.SignInResponse
import dev.alejr.healthassistant.navigation.AppDestination
import dev.alejr.healthassistant.service.ApiClient
import dev.alejr.healthassistant.service.saveUser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignInScreen(navController: NavController, context: Context) {
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    fun handleLogin(email: String, password: String) {
        loading = true;
        error = "";

        Log.d(
            "LoginScreen",
            "handleLogin"
        )

        val signInPayload = SignInPayload(email, password)
        val call = ApiClient.apiService.signIn(signInPayload)

        call.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                loading = false

                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    Log.d(
                        "LoginScreen",
                        "deu bom ${signInResponse?.token}"
                    )

                    saveUser(context, signInResponse!!)
                    navController.navigate(AppDestination.TaskSummaryScreen.route)
                } else {
                    val errorObject = JSONObject(
                        response.errorBody()!!.string()
                    )

                    error = errorObject.getString("message")

                    Log.d(
                        "LoginScreen",
                        "deu ruim ${errorObject.getString("message")}"
                    )

                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                loading = false
                error = "Não foi possível logar, tente novamente"
                Log.d(
                    "LoginScreen",
                    "deu ruim antes ${t.message}"
                )
            }

        })

    }

    SignInScreenForm(onSignupClick = {
        navController.navigate(AppDestination.SignUp.route)
    }, onLoginClick = { email, password ->
        handleLogin(email, password)
    }, loading = loading, error = error)


}