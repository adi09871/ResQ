package com.example.resq

data class Report(
    val id: String = "",
    val reportName: String = "",
    val fileUrl: String = "",
    val date: String = "",
    val type: String = "image" // 'image' or 'pdf'
)

data class VitalSign(
    val date: String = "",
    val bp: String = "",
    val sugar: String = "",
    val heartRate: String = ""
)