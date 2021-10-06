package com.example.thecatapi

import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class CatImage : AppCompatActivity() {

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

    private fun copyToGallery(url: String?, name: String, type: String) {

        val inputStream = URL(url).openStream()
        val storeDirectory = this.getExternalFilesDir(Environment.DIRECTORY_DCIM)
        val outputFile = File(storeDirectory, "$name.$type")
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }
}
