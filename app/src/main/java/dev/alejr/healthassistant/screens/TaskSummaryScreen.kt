package dev.alejr.healthassistant.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.alejr.healthassistant.components.DisplayError
import dev.alejr.healthassistant.components.TaskSummary
import dev.alejr.healthassistant.model.Task
import dev.alejr.healthassistant.model.TaskSummaryResponse
import dev.alejr.healthassistant.navigation.AppDestination
import dev.alejr.healthassistant.service.ApiClient
import dev.alejr.healthassistant.service.deleteUser
import dev.alejr.healthassistant.service.getUser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


val dummyTaskSummary = TaskSummaryResponse(emptyArray(), emptyArray(), emptyArray())

@Composable
fun TaskSummaryScreen(navController: NavController, context: Context) {

    var summary by remember {
        mutableStateOf<TaskSummaryResponse>(dummyTaskSummary)
    }

    var loading by remember {
        mutableStateOf(false)
    }


    var error by remember {
        mutableStateOf("")
    }
    val user = getUser(context)
    var patientName = ""
    if (user !== null) patientName = user.name


    fun handleLogout() {
        deleteUser(context);
        navController.navigate(AppDestination.SignIn.route)
    }

    fun fetchTasks() {
        loading = true
        val call = ApiClient.apiService.fetchTasks(user?.token)

        call.enqueue(object : Callback<TaskSummaryResponse> {
            override fun onResponse(
                call: Call<TaskSummaryResponse>,
                response: Response<TaskSummaryResponse>
            ) {
                loading = false
                if (response.isSuccessful) {
                    val summaryResponse = response.body()
                    if (summaryResponse !== null) {
                        summary = summaryResponse
                        return
                    }
                }
                val errorObject = JSONObject(
                    response.errorBody()!!.string()
                )
                error = errorObject.getString("message")
            }

            override fun onFailure(call: Call<TaskSummaryResponse>, t: Throwable) {
                loading = false
                error = "Não foi possível obter tarefas, tente novamente"
                Log.d(
                    "Summary",
                    "deu ruim antes ${t.message}"
                )
            }

        })
    }

    LaunchedEffect(Unit) {
        fetchTasks()
    }


    fun handleAccomplishTask(task: Task) {
        loading = true
        error = ""
        val call = ApiClient.apiService.completeTask(user?.token, task.id)

        call.enqueue(object : Callback<TaskSummaryResponse> {
            override fun onResponse(
                call: Call<TaskSummaryResponse>,
                response: Response<TaskSummaryResponse>
            ) {
                loading = false
                if (response.isSuccessful) {
                    val summaryResponse = response.body()
                    if (summaryResponse !== null) {
                        summary = summaryResponse
                        return
                    }
                }
                val errorObject = JSONObject(
                    response.errorBody()!!.string()
                )
                error = errorObject.getString("message")
            }

            override fun onFailure(call: Call<TaskSummaryResponse>, t: Throwable) {
                loading = false
                error = "Não foi possível concluir a tarefa, tente novamente"
                Log.d(
                    "Summary",
                    "deu ruim antes ${t.message}"
                )
            }

        })
    }


    if (error.isNotEmpty()) {
        DisplayError(
            message = error,
            retry = { fetchTasks() })
    } else {
        TaskSummary(summary, loading, patientName, onAccomplishTask = { task ->
            handleAccomplishTask(task)
        }, onSignOut = { handleLogout() })
    }


}