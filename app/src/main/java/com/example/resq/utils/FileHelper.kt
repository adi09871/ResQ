package com.example.resq.utils



import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.OutputStream

object FileHelper {

    // --- FEATURE 1: Save QR as Image (JPG) ---
    fun saveBitmapAsJpg(context: Context, bitmap: Bitmap, fileName: String) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/ResQ")
        }

        try {
            val uri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                val outputStream: OutputStream? = resolver.openOutputStream(it)
                outputStream?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    Toast.makeText(context, "Saved to Gallery: Pictures/ResQ", Toast.LENGTH_LONG)
                        .show()
                }
            } ?: run {
                Toast.makeText(context, "Error creating file", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save Image", Toast.LENGTH_SHORT).show()
        }
    }

    // --- FEATURE 2: Save as PDF (Physical Card) ---
    fun saveBitmapAsPdf(context: Context, qrBitmap: Bitmap, userDetails: String, fileName: String) {
        val pdfDocument = PdfDocument()
        // A4 Size Page
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint()

        // 1. Background
        canvas.drawColor(Color.WHITE)

        // 2. Header
        paint.color = Color.RED
        paint.textSize = 40f
        paint.textAlign = Paint.Align.CENTER
        paint.isFakeBoldText = true
        canvas.drawText("ResQ Emergency Card", 297f, 80f, paint)

        // 3. QR Code Image
        val scaledBitmap = Bitmap.createScaledBitmap(qrBitmap, 300, 300, true)
        canvas.drawBitmap(scaledBitmap, 147f, 150f, paint)

        // 4. User Details
        paint.color = Color.BLACK
        paint.textSize = 18f
        paint.textAlign = Paint.Align.CENTER
        paint.isFakeBoldText = false

        // Multi-line text logic
        var y = 500f
        for (line in userDetails.split("\n")) {
            canvas.drawText(line, 297f, y, paint)
            y += 30f
        }

        // 5. Footer Alert
        paint.color = Color.RED
        paint.textSize = 24f
        paint.isFakeBoldText = true
        canvas.drawText("⚠️ IN EMERGENCY: SCAN THIS QR", 297f, 750f, paint)

        pdfDocument.finishPage(page)

        // 6. Save Logic
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/ResQ")
        }

        try {
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            } else {
                resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
            }

            uri?.let {
                val outputStream = resolver.openOutputStream(it)
                outputStream?.use { stream ->
                    pdfDocument.writeTo(stream)
                    Toast.makeText(context, "Saved PDF to Downloads/ResQ", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error saving PDF", Toast.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
        }
    }
}