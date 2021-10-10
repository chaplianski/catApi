package com.example.thecatapi

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


class CatImage : AppCompatActivity() {

    val REQUEST_EXTERNAL_STORAGE = 1
    val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_image)

        val catImage = findViewById<ImageView>(R.id.iv_full_cat)
        val catUrl = intent.extras!!.getString("url")
        Glide.with(this)
            .load(catUrl)
            .fitCenter()
            .into(catImage)

        catImage.setOnLongClickListener {
            val catImageName = getCatName(catUrl)
            val catImageType = getType(catUrl)


            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("Do you want to save file to gallery?")
                setPositiveButton("Yes") {
                    dialog, id ->
                    CoroutineScope(Dispatchers.IO).launch {
                        //   SaveImage(catUrl, catImageName, catImageType)
                        copyToGallery(catUrl, catImageName, catImageType)
                    }
                }
                setNegativeButton("Cancel") {
                    dialog, id ->
                    dialog.cancel()
                }
                show()
            }

            return@setOnLongClickListener true
        }
        catImage.setOnClickListener {

            finish()
            overridePendingTransition(R.anim.grow_from_middle, R.anim.shrink_to_middle)
        }
    }

    private fun getCatName(url: String?): String {
        val namePostfix = url?.removePrefix("https://cdn2.thecatapi.com/images/")
        val name = namePostfix?.substringBefore(".")
        return name.toString()
    }

    private fun getType(url: String?): String {
        val namePostfix = url?.removePrefix("https://cdn2.thecatapi.com/images/")
        val typeImage = namePostfix?.substringAfter(".")
        return typeImage.toString()
    }

    private suspend fun copyToGallery(url: String?, name: String, type: String) {

        verifyStoragePermissions(this)
        val inputStream = URL(url).openStream()
        val storeDirectory = getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

        val outputFile = File(storeDirectory, "$name.$type")
        Log.d("MyLog", outputFile.toString())
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            try {
                outputStream.use { output ->
                    val buffer = ByteArray(8 * 1024) // buffer size
                    while (true) {
                        val byteCount = input.read(buffer)
                        if (byteCount < 0) break
                        output.write(buffer, 0, byteCount)
                    }
                    output.flush()
          //          Toast.makeText(this, "directory mount",Toast.LENGTH_SHORT).show()
                }
            }catch (e: IOException) {
                // Unable to create file, likely because external storage is
                // not currently mounted.
                Log.w("ExternalStorage", "Error writing " + outputFile, e);
            }
        }
    }
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}
