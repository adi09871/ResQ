package com.example.resq.medicaldetailspage

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.ui.theme.pink1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Medicaldetails(
    modifier: Modifier = Modifier,
    navController: NavController,
    authviewmodel: AuthViewModel
) {
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val dbRef = FirebaseDatabase.getInstance().getReference("medical_info").child(uid ?: "")

    var showForm by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    // Check if profile exists
    LaunchedEffect(Unit) {
        if (uid == null) {
            navController.navigate("login") {
                popUpTo("medicaldetails") { inclusive = true }
            }
            return@LaunchedEffect
        }

        dbRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // Profile exists, go to QR page directly
                navController.navigate("qrdownloadpage") {
                    popUpTo("medicaldetails") { inclusive = true }
                }
            } else {
                // Profile missing, show form
                isLoading = false
                showForm = true
            }
        }.addOnFailureListener {
            isLoading = false
            showForm = true
        }
    }

    BackHandler(enabled = showForm) {
        Toast.makeText(context, "Please complete your profile first!", Toast.LENGTH_SHORT).show()
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFFE50914))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Checking Profile...", modifier = Modifier.padding(top = 40.dp))
        }
        return
    }

    if (showForm) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = pink1)
                .verticalScroll(rememberScrollState()) // Allow scrolling for new fields
        ) {
            // Header Row
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    text = "ResQ Details",
                    color = Color(0xFFE50914),
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Complete Your Profile",
                        fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // --- Variables ---
                    var fullname by remember { mutableStateOf("") }
                    var blodgroup by remember { mutableStateOf("") }
                    var allergies by remember { mutableStateOf("") }
                    var conatct1 by remember { mutableStateOf("") }
                    var conatct2 by remember { mutableStateOf("") }
                    var medicalnotes by remember { mutableStateOf("") }

                    // NEW INSURANCE VARIABLES
                    var insuranceProvider by remember { mutableStateOf("") }
                    var policyNumber by remember { mutableStateOf("") }

                    var message by remember { mutableStateOf("") }

                    // --- BASIC INFO SECTION ---
                    Text("Basic Information", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Full Name *", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = fullname, onValueChange = { fullname = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Required") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Blood Group *", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = blodgroup, onValueChange = { blodgroup = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. O+, A-") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Allergies", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = allergies, onValueChange = { allergies = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. Peanuts, Penicillin") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // --- INSURANCE SECTION (NEW) ---
                    Text("Insurance & Reports (Optional)", color = Color.Blue, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Insurance Provider", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = insuranceProvider, onValueChange = { insuranceProvider = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. Star Health, LIC") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Policy Number", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = policyNumber, onValueChange = { policyNumber = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. 12345678") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // --- CONTACT SECTION ---
                    Text("Emergency Contacts", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Emergency Contact 1 *", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = conatct1, onValueChange = { conatct1 = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Primary Contact") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Emergency Contact 2", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = conatct2, onValueChange = { conatct2 = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Secondary Contact") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Medical Notes", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = medicalnotes, onValueChange = { medicalnotes = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Any other critical info...") }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- SAVE BUTTON ---
                    Button(
                        onClick = {
                            if (fullname.isEmpty() || blodgroup.isEmpty() || conatct1.isEmpty()) {
                                message = "❌ Please fill all required fields (*)"
                                return@Button
                            }

                            // Using AuthViewModel to save data
                            authviewmodel.saveMedicalInfo(
                                fullName = fullname,
                                bloodGroup = blodgroup,
                                allergies = allergies,
                                contact1 = conatct1,
                                contact2 = conatct2,
                                medicalNotes = medicalnotes,
                                insuranceProvider = insuranceProvider,
                                policyNumber = policyNumber
                            ) { success, msg ->
                                if (success) {
                                    message = "✅ $msg"
                                    navController.navigate("qrdownloadpage") {
                                        popUpTo("medicaldetails") { inclusive = true }
                                    }
                                } else {
                                    message = "❌ Error: $msg"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914))
                    ) {
                        Text("Save & Generate QR")
                    }

                    if (message.isNotEmpty()) {
                        Text(
                            text = message,
                            color = if(message.startsWith("✅")) Color.Green else Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}