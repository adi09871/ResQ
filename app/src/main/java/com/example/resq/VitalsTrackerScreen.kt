package com.example.resq

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun VitalsTrackerScreen(navController: NavController, authViewModel: AuthViewModel) {
    var bp by remember { mutableStateOf("") }
    var sugar by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        authViewModel.checkSubscription()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFFEbee)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { navController.popBackStack() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                Text("⬅ Back", color = Color.Black)
            }
        }

        if (authViewModel.isCheckingSubscription.value) {
            CircularProgressIndicator(color = Color(0xFFE50914), modifier = Modifier.padding(50.dp))
        } else if (authViewModel.subscribedDoctor.value == null) {
            Text("👨‍⚕️ Choose a Doctor", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE50914))
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(authViewModel.doctorsList.value) { doctor ->
                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(doctor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("${doctor.hospital} | ${doctor.experience} | ${doctor.specialty}", color = Color.Gray, fontSize = 14.sp)

                            Button(
                                onClick = {
                                    authViewModel.subscribeToDoctor(doctor) { _, msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                            ) {
                                Text("Subscribe @ ₹${doctor.fee}/month")
                            }
                        }
                    }
                }
            }
        } else {
            val doctor = authViewModel.subscribedDoctor.value!!

            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)), modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("👨‍⚕️ Connected to ${doctor.name}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
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
                            if(bp.isNotEmpty() || sugar.isNotEmpty() || heartRate.isNotEmpty()) {
                                authViewModel.saveVitalsAndNotify(bp, sugar, heartRate, doctor) { success, msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    if(success) navController.popBackStack()
                                }
                            } else {
                                Toast.makeText(context, "Please enter at least one value", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914))
                    ) {
                        Text("Send to ${doctor.name}")
                    }
                }
            }
        }
    }
}