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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.AuthViewModel
import com.example.resq.MedicalInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun UserProfiledialog(onDismiss: () -> Unit, authviewmodel: AuthViewModel
, onSignOut: () -> Unit) {

    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    var medicalInfo by remember { mutableStateOf<MedicalInfo?>(null) }
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
                color = Color(0xFF008C3D)
            )
        },
        text = {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFE50914))
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProfileRow(label = "Email", value = currentUser?.email ?: "N/A")


                    ProfileRow(label = "Name", value = medicalInfo?.fullName ?: "N/A")

                    ProfileRow(label = "Phone", value = medicalInfo?.contact1 ?: "N/A")


                    ProfileRow(label = "Blood Group", value = medicalInfo?.bloodGroup ?: "N/A")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSignOut,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914)) // Red for Sign Out
            ) {
                Text("Sign Out")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = Color.Gray)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun ProfileRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
        Text(text = value, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Medium)
    }
}


