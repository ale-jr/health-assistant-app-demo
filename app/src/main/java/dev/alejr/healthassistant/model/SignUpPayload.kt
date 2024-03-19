package dev.alejr.healthassistant.model

data class SignUpPayload(
    val name: String = "",
    val email: String = "",
    val password: String = ""
)