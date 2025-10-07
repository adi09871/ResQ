package com.example.resq.responderhome

import android.R
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.google.firebase.annotations.concurrent.Background
import kotlinx.coroutines.delay

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

    LaunchedEffect(Unit) {
        while (true) {
            val now = java.text.SimpleDateFormat("hh:mm:ss a", java.util.Locale.getDefault())
                .format(java.util.Date())
            currentTime = now
            delay(1000)
        }
    }

    if (showScanner) {
        // ✅ QR Scanner screen dikh raha hai
        QRCodeScannerScreen(
            onResult = { value ->
                scannedResult = value
                showScanner = false // scan hone ke baad scanner band
            }
        )
    } else {
        // ✅ Normal Home UI
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
                    .padding(16.dp) // inner padding
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

            // ✅ QR Scan Button
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
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
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // ✅ Agar scan result mila hai to show kare
            scannedResult?.let {
                Text(
                    text = "Scanned Result: $it",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}


