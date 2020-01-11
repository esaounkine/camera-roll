package camera.roll.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

private const val TAG = "Network helper"

suspend fun loadBitmapFromUrl(url: String): Bitmap {
    Log.d(TAG, "Loading image from $url")

    return withContext(Dispatchers.IO) {
        BitmapFactory.decodeStream(URL(url).openStream())
    }
}
