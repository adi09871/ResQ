package com.example.resq.prrofiledialogbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.resq.AuthViewModel
import com.example.resq.MedicalInfo
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfiledialog(onDismiss: () -> Unit, authviewmodel: AuthViewModel
, onSignOut: () -> Unit) {

    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    var medicalInfo by remember { mutableStateOf<MedicalInfo>(null) }
    var isLoading by remember { mutableStateOf(true) }


}