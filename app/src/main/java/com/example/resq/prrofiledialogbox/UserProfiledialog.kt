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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    LaunchedEffect(Unit) {
        currentUser?.uid?.let { uid ->
            val dbRef = FirebaseDatabase.getInstance().getReference("medical_info").child(uid)
            dbRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    medicalInfo = snapshot.getValue(MedicalInfo::class.java)
                }
                isLoading = false
            }.addOnFailureListener {
                isLoading = false
            }
        } ?: run { isLoading = false }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "My Profile",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF008C3D) // ResQ Green Theme
            )
        },
        text = {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFE50914))
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Email (Auth से)
                    ProfileRow(label = "Email", value = currentUser?.email ?: "N/A")


}