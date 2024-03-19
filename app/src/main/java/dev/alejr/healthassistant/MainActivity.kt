package dev.alejr.healthassistant

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import dev.alejr.healthassistant.navigation.AppDestination
import dev.alejr.healthassistant.screens.SignInScreen
import dev.alejr.healthassistant.screens.SignUpScreen
import dev.alejr.healthassistant.screens.TaskSummaryScreen
import dev.alejr.healthassistant.service.getUser

class MainActivity : ComponentActivity() {

    private val context: Context = this

    private fun getStartDestination(): String {
        var startDestination: String = AppDestination.SignIn.route
        val u = getUser(context);
        if (u !== null) {
            startDestination = AppDestination.TaskSummaryScreen.route
        }

        return startDestination
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = getStartDestination()

                    ) {
                        composable(AppDestination.SignIn.route) {
                            SignInScreen(
                                navController,
                                context
                            )
                        }

                        composable(AppDestination.SignUp.route) {
                            SignUpScreen(navController, context)
                        }

                        composable(AppDestination.TaskSummaryScreen.route) {
                            TaskSummaryScreen(navController, context)
                        }

                    }
                }
            }
        }
    }
}
