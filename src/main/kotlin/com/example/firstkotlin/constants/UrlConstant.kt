package com.example.firstkotlin.constants

object UrlConstant {
    private const val API_URL = "api/"
    const val BANKS_URL = API_URL + "banks"
    const val USERS_URL = API_URL + "users"
    const val ROLES_URL = API_URL + "roles"
    const val TRANSACTION_URL = API_URL + "transactions"
    const val LOGIN_URL = API_URL + "login"
    const val REFRESH_URL = "$USERS_URL/token/refresh"
    const val REGISTER_URL = "$USERS_URL/register"
}