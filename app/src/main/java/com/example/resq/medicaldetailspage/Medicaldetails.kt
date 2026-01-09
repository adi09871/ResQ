package com.example.resq.medicaldetailspage


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.MedicalInfo
import com.example.resq.ui.theme.pink1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Medicaldetails(
    modifier: Modifier,
    navController: NavController,
    authviewmodel: AuthViewModel
) {
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val dbRef = FirebaseDatabase.getInstance().getReference("medical_info").child(uid ?: "")

    var showForm by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    // 🔑 Decide screen here
    LaunchedEffect(Unit) {
        if (uid == null) {
            navController.navigate("login") {
                popUpTo("medicaldetails") { inclusive = true }
            }
            return@LaunchedEffect
        }

        dbRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // ✅ Medical data already present → skip form
                navController.navigate("qrdownloadpage") {
                    popUpTo("medicaldetails") { inclusive = true }
                }
            } else {
                // ❌ No data → show form
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
        // Loading Screen jab tak check ho raha hai
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFFE50914))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Checking Profile...", modifier = Modifier.padding(top = 40.dp))
        }
        return // Niche ka code run nahi hoga jab tak loading hai
    }

    if (showForm) {
        // --- YAHAN SE TERA PURANA FORM UI SHURU HOTA HAI ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = pink1)
        ) {
            // Header Row
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    text = "ResQ Details", // Title short kiya space ke liye
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
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Mandatory Medical Info",
                        fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                Spacer(modifier = Modifier.height(20.dp))

                    // Form Fields Variables
                    var fullname by remember { mutableStateOf("") }
                    var blodgroup by remember { mutableStateOf("") }
                    var allergies by remember { mutableStateOf("") }
                    var conatct1 by remember { mutableStateOf("") }
                    var conatct2 by remember { mutableStateOf("") } // Fixed: contact2 alag variable
                    var medicalnotes by remember { mutableStateOf("") }
                    var message by remember { mutableStateOf("") }

                    // --- FIELDS UI ---
                    // Name
                    Text("Full Name *", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = fullname, onValueChange = { fullname = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Required") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Blood Group
                    Text("Blood Group *", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = blodgroup, onValueChange = { blodgroup = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Required") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Allergies
                    Text("Allergies", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = allergies, onValueChange = { allergies = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Contact 1
                    Text("Emergency Contact 1 *", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = conatct1, onValueChange = { conatct1 = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Required") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Text("Emergency Contact 2", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = conatct2, onValueChange = { conatct2 = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Text("Medical Notes", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = medicalnotes, onValueChange = { medicalnotes = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (fullname.isEmpty() || blodgroup.isEmpty() || conatct1.isEmpty()) {
                                message = "❌ Please fill all required fields (*)"
                                return@Button
                            }

                            val currentUid = FirebaseAuth.getInstance().currentUser?.uid
                            if (currentUid != null) {
                                val info = MedicalInfo(
                                    fullName = fullname,
                                    bloodGroup = blodgroup,
                                    allergies = allergies,
                                    contact1 = conatct1,
                                    contact2 = conatct2, // Corrected variable
                                    medicalNotes = medicalnotes
                                )

                                val saveRef = FirebaseDatabase.getInstance()
                                    .getReference("medical_info")
                                    .child(currentUid)

                                saveRef.setValue(info)
                                    .addOnSuccessListener {
                                        message = "✅ Saved! Redirecting..."
                                        
                                        navController.navigate("qrdownloadpage") {
                                            popUpTo("medicaldetails") { inclusive = true }
                                        }
                                    }
                                    .addOnFailureListener {
                                        message = "❌ Error: ${it.message}"
                                    }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914))
                    ) {
                        Text("Save & Generate QR")
                    }

                    if (message.isNotEmpty()) {
                        Text(text = message, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}