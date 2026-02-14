package com.example.resq.Qrpage

import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.R
import com.example.resq.prrofiledialogbox.UserProfiledialog
import com.example.resq.ui.theme.pink1
import com.example.resq.utils.FileHelper // Ensure this import is correct
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

// --- Helper Data & Function ---

data class ChatMessage(val text: String, val isUser: Boolean)

fun generateQrCode(data: String): Bitmap? {
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

// --- Chat Bot Dialog Component ---

@Composable
fun ResQChatBotDialog(onDismiss: () -> Unit) {
    var message by remember { mutableStateOf("") }
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
                TextButton(onClick = onDismiss) {
                    Text("✕", fontSize = 20.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            }

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

// --- Main Screen ---

@Composable
fun Qrdownloadpage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authviewmodel: AuthViewModel
) {
    val context = LocalContext.current
    var showProfileDialog by remember { mutableStateOf(false) }
    var showChatDialog by remember { mutableStateOf(false) }

    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "No User"
    val qrBitmap = remember(uid) { generateQrCode(uid) }

    Box(modifier = modifier.fillMaxSize()) {

        // Main Content Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = pink1)
        ) {
            // --- Header Section ---
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
            }

            // Profile Dialog Logic
            if (showProfileDialog) {
                UserProfiledialog(
                    authviewmodel = authviewmodel,
                    onDismiss = { showProfileDialog = false },
                    onSignOut = {
                        authviewmodel.signout()
                        navController.navigate("login") { popUpTo(0) }
                    }
                )
            }

            // --- QR Code Section ---
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
                    .height(280.dp) // Adjusted height
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

            // --- Download & Print Buttons ---
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        qrBitmap?.let {
                            FileHelper.saveBitmapAsJpg(context, it, "ResQ_ID_$uid")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007ACC)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text("Save Image")
                }

                Button(
                    onClick = {
                        qrBitmap?.let {
                            val cardDetails = "ID: $uid\nType: Emergency Profile\nKeep this card in your wallet."
                            FileHelper.saveBitmapAsPdf(context, it, cardDetails, "ResQ_Card_$uid")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text("Print Card")
                }
            }

            // --- HEALTH TOOLS SECTION (Added Here Correctly) ---
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Health Tools",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 24.dp),
                color = Color(0xFFE50914)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate("upload_report") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text("📂 Upload Reports")
                }

                Button(
                    onClick = { navController.navigate("vitals_tracker") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text("❤️ Vitals Tracker")
                }
            }

        } // End of Column

        // --- Chat FAB (Floating Action Button) ---
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

        // --- Chat Dialog Overlay ---
        if (showChatDialog) {
            Dialog(onDismissRequest = { showChatDialog = false }) {
                ResQChatBotDialog(onDismiss = { showChatDialog = false })
            }
        }
    }
}