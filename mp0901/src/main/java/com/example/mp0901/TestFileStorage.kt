package com.example.mp0901

import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.OutputStreamWriter

fun testFileStorageEx01(context: Context) {
    // File 객체를 이용해 쓰기
    val file = File(context.filesDir, "test.txt")
    val writeStream: OutputStreamWriter = file.writer()
    writeStream.write("hello world")
    writeStream.flush()
    writeStream.close()

    // File 객체를 이용해 읽기
    val readStream: BufferedReader = file.reader().buffered()
    readStream.forEachLine {
        Log.d("mp0901", "File Read:$it")
    }
    readStream.close()
}

fun testFileStorageEx02(context: Context) {
    // openFileOutput 을 이용해 쓰기 (엎어쓰기)
    context.openFileOutput("test.txt", Context.MODE_PRIVATE).use {
        it.write("new content".toByteArray())
    }
    // openFileInput 을 이용해 읽기
    context.openFileInput("test.txt").bufferedReader().forEachLine {
        Log.d("mp0901", "OpenFileInput Read:$it")
    }
}

fun testFileStorageEx03(context: Context) {
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        Log.d("mp0901", "ExternalStorageState.MOUNTED")
    } else {
        Log.d("mp0901", "ExternalStorageState.UNMOUNTED")
    }
    // 파일 쓰기
    val file = File(context.getExternalFilesDir(null), "test.txt")
    Log.d("mp0901", "getExternalFilesDir:${file.absolutePath}")
    val writeStream: OutputStreamWriter = file.writer()
    writeStream.write("hello world")
    writeStream.flush()
    writeStream.close()

    // 파일 읽기
    val readStream: BufferedReader = file.reader().buffered()
    readStream.forEachLine {
        Log.d("mp0901", "readStream: $it")
    }
    readStream.close()
}

fun testFileStorageEx04(context: Context) {
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.SIZE,
        MediaStore.Images.Media.MIME_TYPE,
    )
    val collectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    context.contentResolver.query(
        collectionUri,
        projection,
        null,
        null,
        null
    )?.use { cursor ->
        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            val size = cursor.getLong(2)
            val mimeType = cursor.getString(3)
            val contentUri = Uri.withAppendedPath(collectionUri, id.toString())
            Log.d("mp0901", "Uri: $contentUri, Name: $name, Size: $size, mimeType: $mimeType")

            val contentUri2: Uri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )
            val resolver = context.contentResolver
            resolver.openInputStream(contentUri).use { stream ->
                // stream 객체에서 작업 수행
                val option = BitmapFactory.Options()
                option.inSampleSize = 10
                val bitmap = BitmapFactory.decodeStream(stream, null, option)
                // binding.imageView.setImageBitmap(bitmap)
            }

        }
    }

}


