package dev.alejr.healthassistant.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import dev.alejr.healthassistant.components.SignUpScreenForm
import dev.alejr.healthassistant.model.SignInResponse
import dev.alejr.healthassistant.model.SignUpPayload
import dev.alejr.healthassistant.navigation.AppDestination
import dev.alejr.healthassistant.service.ApiClient
import dev.alejr.healthassistant.service.saveUser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignUpScreen(navController: NavController, context: Context) {
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    fun handleSignup(name: String, email: String, password: String) {
        loading = true
        error = ""

        val signUpPayload = SignUpPayload(name, email, password)
        val call = ApiClient.apiService.signUp(signUpPayload)
        call.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>, response: Response<SignInResponse>
            ) {
                loading = false
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    saveUser(context, signInResponse!!)
                    navController.navigate(AppDestination.TaskSummaryScreen.route)
                } else {
                    val errorObject = JSONObject(
                        response.errorBody()!!.string()
                    )
                    error = errorObject.getString("message")
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                loading = false
                error = "Não foi possível logar, tente novamente"
            }

        })


    }

    SignUpScreenForm(onSignupClick = { name, email, password ->
        handleSignup(name, email, password)
    }, onLoginClick = {
        navController.navigate(AppDestination.SignIn.route)
    }, loading, error)
}