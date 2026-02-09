package com.example.resq.medicaldetailspage

import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.R
import com.example.resq.ui.theme.pink1
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import android.graphics.Color as AndroidColor
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.example.resq.prrofiledialogbox.UserProfiledialog


fun generateQrCode(data: String): Bitmap?
{
    return try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap[x, y] = if (bitMatrix[x, y]) AndroidColor.BLACK else AndroidColor.WHITE
            }
        }
        bitmap
    } catch (_: Exception) {
        null
    }
}

@Composable
fun Qrdownloadpage(modifier: Modifier = Modifier,
           navController: NavController,
           authviewmodel: AuthViewModel
) {
    var showProfileDialog by remember { mutableStateOf(false ) }

    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "No User"

    val qrBitmap = remember(uid) { generateQrCode(uid) }

    Box(modifier = modifier.fillMaxSize()) {

        // --- EXISTING CONTENT (Inside Column) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = pink1)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo),
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
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .clickable { showProfileDialog = true }
                )
                if (showProfileDialog) {
                    UserProfiledialog(
                        authviewmodel = authviewmodel,
                        onDismiss = { showProfileDialog = false },
                        onSignOut = {
                            authviewmodel.signout()
                            navController.navigate("login") {
                                popUpTo(0)
                            }
                        }
                    )
                }
            }

        Text(
            text = "Your Emergency QR Code",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Box( modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth()
            .height(350.dp)
            .border(width = 2.dp, color = Color(0xFF008C3D), shape = RoundedCornerShape(16.dp)) //
            .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (qrBitmap != null) {

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