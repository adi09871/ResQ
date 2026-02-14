package com.example.resq.com.example.resq


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.ui.theme.pink1

@Composable
fun VitalsTrackerScreen(navController: NavController, authViewModel: AuthViewModel) {
    var bp by remember { mutableStateOf("") }
    var sugar by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(pink1).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- DOCTOR SUBSCRIPTION BANNER (BUSINESS MODEL) ---
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("👨‍⚕️ Connected to Dr. Sharma", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Premium Subscription Active", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Your readings are monitored daily.", color = Color.White, fontSize = 12.sp)
            }
        }

        Text("❤️ Daily Vitals Tracker", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE50914))

        Card(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(value = bp, onValueChange = { bp = it }, label = { Text("Blood Pressure (e.g. 120/80)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = sugar, onValueChange = { sugar = it }, label = { Text("Sugar Level (e.g. 90 mg/dL)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = heartRate, onValueChange = { heartRate = it }, label = { Text("Heart Rate (e.g. 72 bpm)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        authViewModel.saveVitals(bp, sugar, heartRate) { success, msg ->
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            if(success) navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914))
                ) {
                    Text("Submit to Doctor")
                }
            }
        }
    }
}