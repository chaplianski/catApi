package com.example.thecatapi


import android.content.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class CatImage : AppCompatActivity() {

    private var photoLauncher: ActivityResultLauncher<Intent>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_image)

        val catImage = findViewById<ImageView>(R.id.iv_full_cat)
        val catUrl = intent.extras!!.getString("url")
        Glide.with(this)
            .load(catUrl)
            .fitCenter()
            .into(catImage)


        catImage.setOnLongClickListener() {
            val catImageName = getCatName(catUrl)
            val catImageType = getType(catUrl)

            val builder = AlertDialog.Builder(this)
            with (builder){
                setTitle("Do you want to save file to gallery?")
                setPositiveButton("Yes"){
                        dialog, id ->
                    CoroutineScope(Dispatchers.IO).launch {
                     //   SaveImage(catUrl, catImageName, catImageType)
                        copyToGallery(catUrl, catImageName, catImageType)
                    }

                }
                setNegativeButton("Cancel"){
                        dialog, id -> dialog.cancel()
                }
                show()
            }

          //  builder.create()





            return@setOnLongClickListener true

        }
        catImage.setOnClickListener{

            finish()
            overridePendingTransition(R.anim.grow_from_middle,R.anim.shrink_to_middle)
        }

    }

/*
   suspend fun SaveImage(url: String?, name: String, fileType: String) {
        Glide.with(this).asBitmap().load(url).into(object : CustomTarget<Bitmap?>() {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap?>?) {
                Log.d("MyLog", name )
                Log.d("MyLog", fileType )
                saveImageToFile(bitmap,name, fileType)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }
*/
/*

    @Throws(IOException::class)
    private fun saveImageToFile (bitmap: Bitmap, name: String, type: String) {
        val fos: OutputStream?
        fos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = contentResolver
            val contentValues = ContentValues()
            if (type == "jpg"){
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            }
            if (type == "gif"){
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.gif")
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/gif")
            }


            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            Objects.requireNonNull(imageUri).let { resolver.openOutputStream(it!!) }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            val image = File(imagesDir, "$name.jpg")
            FileOutputStream(image)
        }
        if (type == "jpg"){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
      //  bitmap.compress(Bitmap.CompressFormat.GIF, 100, fos)
     //   bitmap.toDrawable()

        Objects.requireNonNull(fos)?.close()
    }
*/
    fun getCatName (url: String?): String{
        val namePostfix = url?.removePrefix("https://cdn2.thecatapi.com/images/")
        val name = namePostfix?.substringBefore(".")
        return name.toString()

    }

    fun getType (url: String?): String {
        val namePostfix = url?.removePrefix("https://cdn2.thecatapi.com/images/")
        val typeImage = namePostfix?.substringAfter(".")
        return typeImage.toString()
    }


    fun copyToGallery (url: String?, name: String, type: String) {

        val inputStream = URL(url).openStream()
        val storeDirectory = this.getExternalFilesDir(Environment.DIRECTORY_DCIM) // DCIM folder
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