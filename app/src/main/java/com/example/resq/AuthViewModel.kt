package com.example.resq

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authstate = MutableLiveData<Authstate>()
    val authstate: LiveData<Authstate> = _authstate

    var loggedInResponderID = mutableStateOf("")
    var loginSuccess = mutableStateOf(false)

    init {
        // Automatically check if user is already logged in
        if (auth.currentUser != null) {
            _authstate.value = Authstate.Authenticated
        } else {
            _authstate.value = Authstate.Unauthenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authstate.value = Authstate.Error("Email or password can't be empty")
            return
        }
        _authstate.value = Authstate.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authstate.value = Authstate.Authenticated
                } else {
                    _authstate.value = Authstate.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authstate.value = Authstate.Error("Email or password can't be empty")
            return
        }
        _authstate.value = Authstate.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authstate.value = Authstate.Authenticated
                } else {
                    _authstate.value = Authstate.Error(task.exception?.message ?: "Signup failed")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authstate.value = Authstate.Unauthenticated
        loggedInResponderID.value = ""
        loginSuccess.value = false
    }

    fun accessSystem(id: String, pass: String) {
        if (id == "admin" && pass == "1234") {
            loginSuccess.value = true
            loggedInResponderID.value = id
        } else {
            loginSuccess.value = false
            _authstate.value = Authstate.Error("Invalid Responder ID or Password")
        }
    }

    fun saveMedicalInfo(
        fullName: String,
        bloodGroup: String,
        allergies: String,
        contact1: String,
        contact2: String,
        medicalNotes: String,
        insuranceProvider: String,
        policyNumber: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(false, "User not logged in")
            return
        }

        val info = MedicalInfo(
            fullName = fullName,
            bloodGroup = bloodGroup,
            allergies = allergies,
            contact1 = contact1,
            contact2 = contact2,
            medicalNotes = medicalNotes,
            insuranceProvider = insuranceProvider,
            policyNumber = policyNumber
        )

        FirebaseDatabase.getInstance().getReference("medical_info").child(uid)
            .setValue(info)
            .addOnSuccessListener {
                onResult(true, "Profile Saved Successfully")
            }
            .addOnFailureListener {
                onResult(false, it.message ?: "Failed to save data")
            }
    }

    // --- NEW: UPLOAD REPORT FUNCTION (Inside Class) ---
    fun uploadReport(reportName: String, fileUri: Uri, onResult: (Boolean, String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(false, "User not logged in")
            return
        }

        val fileName = "reports/${uid}/${System.currentTimeMillis()}_${reportName}"
        val storageRef = FirebaseStorage.getInstance().reference.child(fileName)

        storageRef.putFile(fileUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val report = Report(
                        id = System.currentTimeMillis().toString(),
                        reportName = reportName,
                        fileUrl = uri.toString(),
                        date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                    )

                    // Database mein Link Save karo
                    FirebaseDatabase.getInstance().getReference("users").child(uid).child("reports").push().setValue(report)
                        .addOnSuccessListener { onResult(true, "Report Uploaded!") }
                        .addOnFailureListener { onResult(false, "DB Error: ${it.message}") }
                }
            }
            .addOnFailureListener {
                onResult(false, "Upload Failed: ${it.message}")
            }
    }

    // --- NEW: SAVE VITALS FUNCTION (Inside Class) ---
    fun saveVitals(bp: String, sugar: String, heartRate: String, onResult: (Boolean, String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(false, "User not logged in")
            return
        }

        val vital = VitalSign(
            date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
            bp = bp,
            sugar = sugar,
            heartRate = heartRate
        )

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("vitals").push().setValue(vital)
            .addOnSuccessListener { onResult(true, "Vitals Sent to Doctor!") }
            .addOnFailureListener { onResult(false, "Failed: ${it.message}") }
    }
}

// --- DATA CLASSES ---
sealed class Authstate {
    object Authenticated : Authstate()
    object Unauthenticated : Authstate()
    object Loading : Authstate()
    data class Error(val message: String) : Authstate()
}

