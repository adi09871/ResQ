package com.example.resq

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.database.database




class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppNavigation(
                modifier = Modifier,
authViewModel = authViewModel
            )


            // Test Realtime Database
            val database = Firebase.database.reference
            database.child("test").setValue("Hello Firebase")
                .addOnSuccessListener { Log.d("FirebaseCheck", "Database Connected!") }
                .addOnFailureListener { Log.d("FirebaseCheck", "Database Failed") }
        }
    }
}

