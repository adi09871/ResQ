package com.example.resq

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authstate = MutableLiveData<Authstate>()
    val authstate: LiveData<Authstate> = _authstate

    // Responder Login Variables
    var loggedInResponderID = mutableStateOf("")
    var loginSuccess = mutableStateOf(false)

    init {

        auth.signOut()
        _authstate.value = Authstate.Unauthenticated
    }

    // Login Function
    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authstate.value = Authstate.Error("Email or password can't be empty")
            return
        }
        _authstate.value = Authstate.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authstate.value = Authstate.Authenticated
                } else {
                    _authstate.value = Authstate.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authstate.value = Authstate.Error("Email or password can't be empty")
            return
        }
        _authstate.value = Authstate.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authstate.value = Authstate.Authenticated
                } else {
                    _authstate.value = Authstate.Error(task.exception?.message ?: "Signup failed")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authstate.value = Authstate.Unauthenticated
        loggedInResponderID.value = ""
        loginSuccess.value = false
    }

    fun accessSystem(id: String, pass: String) {
        if (id == "admin" && pass == "1234") {
            loginSuccess.value = true
            loggedInResponderID.value = id
        } else {
            loginSuccess.value = false
            _authstate.value = Authstate.Error("Invalid Responder ID or Password")
        }
    }
}

sealed class Authstate {
    object Authenticated : Authstate()
    object Unauthenticated : Authstate()
    object Loading : Authstate()
    data class Error(val message: String) : Authstate()
}