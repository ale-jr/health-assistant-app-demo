package dev.alejr.healthassistant.navigation

sealed class AppDestination(val route: String) {

    object SignIn : AppDestination("signIn")
    object SignUp : AppDestination("signUp")
    object TaskSummaryScreen : AppDestination("taskSummaryScreen")


}