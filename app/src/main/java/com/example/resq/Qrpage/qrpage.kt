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
fun ResQChatBotDialog(onDismiss: () -> Unit) {
    var message by remember { mutableStateOf("") }
    // Dummy Logic for "AI"
    val chatHistory = remember { mutableStateListOf(
        ChatMessage("Hello! ResQ Assistant here 🤖.\nNeed help with 'Ambulance', 'Police' or 'First Aid'?", false)
    )}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("ResQ Assistant", fontWeight = FontWeight.Bold, color = Color(0xFFE50914), fontSize = 18.sp)
                // Replaced Icon with Text Button to avoid "Unresolved Reference: Icons"
                TextButton(onClick = onDismiss) {
                    Text("✕", fontSize = 20.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            }

            // Replaced Divider with HorizontalDivider (Material 3 Fix)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Chat List
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(chatHistory) { msg ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
                    ) {
                        Surface(
                            color = if (msg.isUser) Color(0xFFE50914) else Color(0xFFF0F0F0),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.widthIn(max = 250.dp)
                        ) {
                            Text(
                                text = msg.text,
                                modifier = Modifier.padding(10.dp),
                                color = if (msg.isUser) Color.White else Color.Black
                            )
                        }
                    }
                }
            }

            // Input Area
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type here...") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (message.isNotEmpty()) {
                            chatHistory.add(ChatMessage(message, true))
                            // Simple Logic (No Dependency)
                            val lowerMsg = message.lowercase()
                            val reply = when {
                                lowerMsg.contains("ambulance") -> "🚨 Calling Ambulance (102)... Location Shared."
                                lowerMsg.contains("police") -> "🚓 Calling Police (100)... Report Sent."
                                lowerMsg.contains("fire") -> "🚒 Calling Fire Dept (101)..."
                                lowerMsg.contains("first aid") -> "🩹 CPR Guide: Push hard & fast in center of chest."
                                else -> "I can help with: Ambulance, Police, Fire, First Aid."
                            }
                            chatHistory.add(ChatMessage(reply, false))
                            message = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914)),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text("Send")
                }
            }
        }
    }
}

// --- 4. MAIN SCREEN (Updated with FAB) ---
@Composable
fun Qrdownloadpage(
    modifier: Modifier = Modifier,
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

            Box(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth()
                    .height(350.dp)
                    .border(width = 2.dp, color = Color(0xFF008C3D), shape = RoundedCornerShape(16.dp))
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

        FloatingActionButton(
            onClick = { showChatDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color(0xFFE50914),
            contentColor = Color.White
        ) {
            Text("🤖", fontSize = 24.sp)
        }

        if (showChatDialog) {
            Dialog(onDismissRequest = { showChatDialog = false }) {
                ResQChatBotDialog(onDismiss = { showChatDialog = false })
            }
        }
    }
}