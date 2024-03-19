package dev.alejr.healthassistant.service

import dev.alejr.healthassistant.model.SignInPayload
import dev.alejr.healthassistant.model.SignInResponse
import dev.alejr.healthassistant.model.SignUpPayload
import dev.alejr.healthassistant.model.TaskSummaryResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("signin")
    fun signIn(@Body payload: SignInPayload): Call<SignInResponse>

    @POST("signup")
    fun signUp(@Body payload: SignUpPayload): Call<SignInResponse>

    @GET("tasks")
    fun fetchTasks(@Header("Authorization") authorization: String?): Call<TaskSummaryResponse>

    @POST("tasks/{id}/complete")
    fun completeTask(
        @Header("Authorization") authorization: String?,
        @Path("id") taskId: Number
    ): Call<TaskSummaryResponse>
}


object ApiClient {
    private const val BASE_URL = "https://fiap-healthassistant.alejr.dev/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}