package camera.roll.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import camera.roll.model.LoadingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

private const val TAG = "Network helper"

suspend fun loadBitmapFromUrl(url: String): Bitmap? {
    Log.d(TAG, "Loading image from $url")

    return withContext(Dispatchers.IO) {
        try {
            BitmapFactory.decodeStream(URL(url).openStream())
        } catch (e: IOException) {
            Log.e(TAG, "Failed to load the image from $url")
            throw LoadingException(msg = e.message!!)
        }
    }
}
