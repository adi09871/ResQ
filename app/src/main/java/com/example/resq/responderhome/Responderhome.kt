package com.example.resq.responderhome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Responderhome(
    modifier: Modifier,
    navController: NavController,
    authviewmodel: AuthViewModel,
) {
    val responderID = authviewmodel.loggedInResponderID.value
    var currentTime by remember { mutableStateOf("") }

    // ✅ QR Scanner states
    var scannedResult by remember { mutableStateOf<String?>(null) }
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
                // Database Path: users -> {UID} -> (Data)
                val dbRef = FirebaseDatabase.getInstance().getReference("users").child(scannedUID!!)

                dbRef.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        // Data ko MedicalInfo class mein convert karein
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
                scannedResult = value
                showScanner = false
            }
        )
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFE9FDF1))
        ) {
            Row(
                modifier = Modifier.padding(top = 22.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(com.example.resq.R.drawable.logo),
                    modifier = modifier.size(30.dp),
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

                IconButton(
                    onClick = { /* TODO: action */ }
                ) {
                    Icon(
                        painter = painterResource(id = com.example.resq.R.drawable.exitlogo),
                        contentDescription = "Responder Logo",
                        tint = Color(0xFF008C3D),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFE9FDF1),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF00C853),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Welcome, ${responderID}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Current Date & Time:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = currentTime,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
            Text(
                "Emergency Scanner",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                "Scan patient QR codes for emergency medical information",
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )


            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(
                        color = Color(0xFF00C853),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { showScanner = true }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = com.example.resq.R.drawable.blankqrimage),
                        contentDescription = "qrcode",
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Scan QR Code",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            scannedResult?.let {
                Text(
                    text = "Scanned Result: $it",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF0FFF4),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF00C853),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            ){
                Text(
                    text = "Instructions:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "1. Tap the 'Scan QR Code' box to open the scanner.\n" +
                            "2. Align the patient's QR code within the frame.\n" +
                            "3. The app will automatically scan and display the patient's emergency information.\n" +
                            "4. Use this information to provide appropriate medical assistance.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                )

            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF0FFF4),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFFCDD2),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Authorized Personnel Only",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF880E4F), textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.size(8.dp))
                Text(
                    "This information is confidential and protected by medical privacy laws.",
                    fontSize = 11.sp

                )
            }

        }
    }
}



