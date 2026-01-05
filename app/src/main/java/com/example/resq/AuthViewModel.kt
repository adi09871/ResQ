package com.example.resq

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth :  FirebaseAuth = FirebaseAuth.getInstance()
    private val _authstate = MutableLiveData<Authstate>()
    val authstate : LiveData<Authstate> = _authstate
    var justLoggedIn = false

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus (){
        if (auth.currentUser==null){
            _authstate.value= Authstate.Unauthenticated
        }else {
            _authstate.value = Authstate.Autheticated
            justLoggedIn = false
        }

    }

    fun login(email : String,password : String){
        if (email.isEmpty() || password.isEmpty()){
            _authstate.value = Authstate.Error("Email or password can't be empty ")
            return
        }

        _authstate.value = Authstate.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if ( task.isSuccessful){_authstate.value = Authstate.Autheticated

            }else {

                    _authstate.value = Authstate.Error(task.exception?.message ?: "Something Went Wrong")
                }
            }
    }


    fun signup(email : String,password : String){
        if (email.isEmpty() || password.isEmpty()){
            _authstate.value = Authstate.Error("Email or password can't be empty ")
        }

        _authstate.value = Authstate.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if ( task.isSuccessful){
                }else {
                    _authstate.value = Authstate.Error(task.exception?.message ?: "Something Went Wrong")
                }
            }
    }

    fun signout (){
        auth.signOut()
        _authstate.value = Authstate.Unauthenticated
    }
    private val validResponders = mapOf(
        "responder001" to "pass001",
        "responder002" to "pass002",
        "responder003" to "pass003"
    )

    var loginSuccess = mutableStateOf(false)
    var loginError = mutableStateOf("")
    var loggedInResponderID = mutableStateOf("")
    fun aceessystem(responderID: String, password: String) {
        if (responderID.isEmpty() || password.isEmpty()) {
            loginError.value = "Responder ID or Password cannot be empty"
            loginSuccess.value = false
            return
        }

        if (validResponders[responderID] == password) {
            loginSuccess.value = true
            loginError.value = ""
            loggedInResponderID.value = responderID
        } else {
            loginSuccess.value = false
            loginError.value = "Invalid Responder ID or Password"
            loggedInResponderID.value = ""
        }
    }
}

sealed class Authstate {
    object Autheticated : Authstate()
    object  Unauthenticated : Authstate()
    object  Loading : Authstate()
    data class  Error (val message: String) : Authstate()



}