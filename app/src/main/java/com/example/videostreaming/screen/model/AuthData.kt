package com.example.videostreaming.screen.model

data class AuthData(
    val name: String?=null,
    val email:String="",
    val password:String="",
    val confirmPassword:String?=null
)
