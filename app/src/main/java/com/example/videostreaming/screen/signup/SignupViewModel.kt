package com.example.videostreaming.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videostreaming.screen.model.AuthData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SignupViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authData = MutableStateFlow(AuthData())
    val authData: StateFlow<AuthData> = _authData.asStateFlow()

    private val _signupResult = MutableStateFlow<Result<String>?>(null)
    val signupResult: StateFlow<Result<String>?> = _signupResult.asStateFlow()


    fun updateSignupData(newData: AuthData) {
        _authData.value = newData
    }

    fun signup() {
        val email = _authData.value.email
        val password = _authData.value.confirmPassword

            viewModelScope.launch {
                if (password != null) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            _signupResult.value = Result.success("Signup Successful")
                        } else {
                            _signupResult.value = Result.failure(it.exception!!)
                        }
                    }
                }
            }
    }

}

