package com.example.videostreaming.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videostreaming.screen.model.AuthData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _authData = MutableStateFlow(AuthData())
    val authData: StateFlow<AuthData> = _authData.asStateFlow()

    private val _loginResult = MutableStateFlow<Result<String>?>(null)
    val loginResult: StateFlow<Result<String>?> = _loginResult.asStateFlow()


    fun updateLoginData(newData: AuthData) {
        _authData.value = newData
    }

    fun login() {
        val email = _authData.value.email
        val password = _authData.value.password
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    _loginResult.value = Result.success("Login Successful")
                } else {
                    _loginResult.value = Result.failure(it.exception ?: Exception("Login failed"))
                }
            }
        }
    }
}