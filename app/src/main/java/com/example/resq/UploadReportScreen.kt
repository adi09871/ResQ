package com.example.resq

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.resq.ui.theme.pink1

@Composable
fun UploadReportScreen(navController: NavController, authViewModel: AuthViewModel) {
    var reportName by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedFileUri = uri
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFFEbee)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { navController.popBackStack() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                Text("⬅ Back", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("📂 Upload Medical Reports", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE50914))
        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth().padding(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("1. Name your report", fontWeight = FontWeight.Bold)
                OutlinedTextField(value = reportName, onValueChange = { reportName = it }, label = { Text("e.g., Blood Test, X-Ray") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))

                Text("2. Select File (Image)", fontWeight = FontWeight.Bold)
                Button(onClick = { launcher.launch("image/*") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
                    Text(if (selectedFileUri == null) "Choose Image" else "File Selected ✅")
                }
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (reportName.isNotEmpty() && selectedFileUri != null) {
                            isUploading = true
                            authViewModel.uploadReport(reportName, selectedFileUri!!) { success: Boolean, msg: String ->
                                isUploading = false
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                if (success) {
                                    reportName = ""
                                    selectedFileUri = null
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please enter name & select file", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914)),
                    enabled = !isUploading
                ) {
                    if (isUploading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Uploading...")
                    } else {
                        Text("Upload Now")
                    }
                }
            }
        }
    }
}