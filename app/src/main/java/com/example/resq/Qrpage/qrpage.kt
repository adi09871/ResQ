package com.example.resq.medicaldetailspage

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R
import com.example.resq.ui.theme.pink1
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun generateQrCode(data: String): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        // QR size 512x512
        val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                // AndroidColor.BLACK use karne se 'Int' wala error nahi aayega
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) AndroidColor.BLACK else AndroidColor.WHITE)
            }
        }
        bitmap
    } catch (e: Exception) {
        null
    }
}

@Composable
fun Qrpage(modifier : Modifier
) {
    // 1. Firebase se User UID fetch karein
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "No User"

    // 2. CALL THE FUNCTION HERE: Is line se aapka function active (colored) ho jayega
    val qrBitmap = remember(uid) { generateQrCode(uid) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = pink1)
    ) {
        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.logo), //
                    contentDescription = "Logo",
                    tint = Color(0xFFE50914),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "ResQ",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE50914)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.profile), //
                contentDescription = "Profile",
                modifier = Modifier.size(40.dp).align(Alignment.TopEnd)
            )
        }

        Text(
            text = "Your Emergency QR Code",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        // 3. QR Code Display Box
        Box( modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth()
            .height(350.dp)
            .border(width = 2.dp, color = Color(0xFF008C3D), shape = RoundedCornerShape(16.dp)) //
            .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (qrBitmap != null) {
                // Bitmap ko Image mein convert karke display karein
                Image(
                    bitmap = qrBitmap.asImageBitmap(),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(250.dp)
                )
            } else {
                Text("Generating QR...")
            }
        }
    }
}