package com.example.videostreaming.screen.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.videostreaming.screen.model.AuthData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class SignupViewModel : ViewModel() {
    private val _authData = MutableStateFlow(AuthData())
    val authData: StateFlow<AuthData> = _authData.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()


    fun updateSignupData(newData: AuthData) {
        _authData.value = newData
    }

    private fun validateEmail(email: String) {
        _emailError.value = if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            null
        } else {
            "Invalid Email"
        }
    }


}