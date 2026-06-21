package com.example.mp0705

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mp0705.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var filePath: String
    var imageUriString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageUriString =
            savedInstanceState?.getString(
                "imageUri"
            )

        imageUriString?.let {

            binding.userImageView
                .setImageURI(
                    Uri.parse(it)
                )

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            val uri = result.data?.data ?: return@registerForActivityResult
            imageUriString = uri.toString()
            try{
                val imgSize = resources.getDimensionPixelSize(R.dimen.imgSize)
                val options = BitmapFactory.Options().apply {
                    inSampleSize = calculateInSampleSize(uri, imgSize, imgSize)
                }
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream, null, options)
                }?.let { bitmap ->
                    binding.userImageView.setImageBitmap(bitmap)
                } ?: Log.e("mpex", "Decoded bitmap is null")
            } catch (e: Exception) {
                Log.e("mpex", "Error decoding image", e)
            }
        }

        binding.galleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(galleryIntent)
        }

        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            // TODO: get camera file from filepath
            try {
                val imageUri = Uri.fromFile(File(filePath))
                imageUriString = imageUri.toString()
                val imgSize = resources.getDimensionPixelSize(R.dimen.imgSize)
                val options = BitmapFactory.Options().apply {
                    inSampleSize = calculateInSampleSize(imageUri, imgSize, imgSize)
                }
                val bitmap = BitmapFactory.decodeFile(filePath, options)
                if (bitmap != null) {
                    binding.userImageView.setImageBitmap(bitmap)
                } else {
                    Log.e("mpex", "Failed to decode bitmap from file: $filePath")
                }
            } catch (e: Exception) {
                Log.e("mpex", "Error loading image from file", e)
            }
        }

        binding.cameraButton.setOnClickListener {
            try {
                val timeStamp: String =
                    SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val file = File.createTempFile(
                    "JPEG_${timeStamp}_", ".jpg", storageDir
                ).apply {
                    filePath = absolutePath
                }
                val photoURI: Uri = FileProvider.getUriForFile(
                    this, "${packageName}.fileProvider", file
                )

                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                }
                requestCameraFileLauncher.launch(cameraIntent)
            } catch (e: IOException) {
                Log.e("mpex0705", "File Creation Failed", e)
            } catch (e: ActivityNotFoundException) {
                Log.e("mpex0705", "No Camera App Fount", e)
            }
        }
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        // TODO: calculate inSampleSize
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        try {
            contentResolver.openInputStream(fileUri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
            }
        } catch (e: IOException) {
            Log.e("mpex","Failed to decode image bounds", e)
            return 1
        }
        val (height, width) = options.outHeight to options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight &&
            halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
    override fun onSaveInstanceState(
        outState: Bundle
    ) {

        super.onSaveInstanceState(outState)

        outState.putString(
            "imageUri",
            imageUriString
        )

    }
}