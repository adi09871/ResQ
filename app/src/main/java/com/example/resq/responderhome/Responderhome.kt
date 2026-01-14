package com.example.resq.responderhome

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.MedicalInfo
import com.example.resq.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Responderhome(
    modifier: Modifier = Modifier,
    navController: NavController,
    authviewmodel: AuthViewModel,
) {
    val responderID = authviewmodel.loggedInResponderID.value
    var currentTime by remember { mutableStateOf("") }
    val context = LocalContext.current

    var scannedUID by remember { mutableStateOf<String?>(null) }
    var showScanner by remember { mutableStateOf(false) }

    var medicalInfo by remember { mutableStateOf<MedicalInfo?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            val now = java.text.SimpleDateFormat("hh:mm:ss a", java.util.Locale.getDefault())
                .format(java.util.Date())
            currentTime = now
            delay(1000)
        }
    }


    LaunchedEffect(scannedUID) {
        if (scannedUID != null) {
            isLoading = true
            try {
                val dbRef = FirebaseDatabase.getInstance().getReference("medical_info").child(scannedUID!!)

                dbRef.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val info = snapshot.getValue(MedicalInfo::class.java)
                        medicalInfo = info
                    } else {
                        Toast.makeText(context, "No medical data found for this ID", Toast.LENGTH_LONG).show()
                    }
                    isLoading = false
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
            } catch (e: Exception) {
                isLoading = false
            }
        }
    }

    if (showScanner) {
        QRCodeScannerScreen(
            onResult = { value ->
                Toast.makeText(context, "Scanned ID: $value", Toast.LENGTH_LONG).show()
                showScanner = false
                scannedUID = value
            } )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFE9FDF1))
                .verticalScroll(rememberScrollState()) // Allow scrolling
        ) {
            Row(
                modifier = Modifier.padding(top = 22.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    modifier = Modifier.size(30.dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xFF008C3D))
                )
                Text(
                    text = "ResQ ",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Logout Logic */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.exitlogo),
                        contentDescription = "Exit",
                        tint = Color(0xFF008C3D),
                    )
                }
            }


            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(color = Color(0xFFE9FDF1), shape = RoundedCornerShape(12.dp))
                    .border(width = 1.dp, color = Color(0xFF00C853), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(text = "Welcome, $responderID", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Current Time: $currentTime", fontSize = 16.sp, color = Color.Black)
            }


            if (medicalInfo == null) {

                Text(
                    "Emergency Scanner",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(color = Color(0xFF00C853), shape = RoundedCornerShape(12.dp))
                        .clickable {
                            showScanner = true


                            scannedUID = null
                        }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White)
                            Text("Fetching Data...", color = Color.White)
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.blankqrimage),
                                contentDescription = "QR",
                                modifier = Modifier.size(50.dp)
                            )
                            Text("Scan QR Code", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            } else {
                 MedicalDataCard(info = medicalInfo!!)


                Button(
                    onClick = {
                        medicalInfo = null
                        scannedUID = null
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))
                ) {
                    Text("Scan Another Patient")
                }
            }


            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(color = Color(0xFFF0FFF4), shape = RoundedCornerShape(12.dp))
                    .border(width = 1.dp, color = Color(0xFF00C853), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text("Instructions:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    "1. Scan patient QR to fetch details.\n2. Verify identity before treatment.\n3. Data is fetched from secure server.",
                    fontSize = 16.sp, color = Color.DarkGray
                )
            }
        }
    }
}


@Composable
fun MedicalDataCard(info: MedicalInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "PATIENT DETAILS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE50914),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )

            InfoRow("Name", info.fullName)
            InfoRow("Blood Group", info.bloodGroup)
            InfoRow("Allergies", info.allergies)
            InfoRow("Emergency Contact", info.contact1)
            InfoRow("Secondary Contact", info.contact2)

            Spacer(modifier = Modifier.height(8.dp))
            Text("Medical Notes:", fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(info.medicalNotes.ifEmpty { "None" }, fontSize = 16.sp)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.Bold, color = Color.Gray)
        Text(text = value.ifEmpty { "N/A" }, fontWeight = FontWeight.Medium, color = Color.Black)
    }
}