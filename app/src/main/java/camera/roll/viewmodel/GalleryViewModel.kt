package camera.roll.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.collection.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import camera.roll.adapter.PictureListAdapter
import camera.roll.model.LoadingException
import camera.roll.model.PictureItem
import camera.roll.network.RandomuserApiRequestHandler
import camera.roll.network.loadBitmapFromUrl

class GalleryViewModel : ViewModel() {

    private val apiService = RandomuserApiRequestHandler()

    // TODO add local storage for the bitmap,
    //  instead of retaining it in the memory
    //  and restricting the cache size
    private val imageCache: LruCache<String, LiveData<Bitmap?>> = LruCache(100)

    val content: LiveData<List<PictureItem>> = fetchData()

    private fun fetchData(): LiveData<List<PictureItem>> = liveData {
        val data = apiService.getData(5000)
        emit(data)
    }

    fun getImageFromCache(imageUrl: String): LiveData<Bitmap?> {
        if (imageCache[imageUrl] == null) {
            // TODO investigate how to migrate this clunky routine to a MediatorLiveData
            //  to gracefully combine local and remote resources into a single data item
            imageCache.put(imageUrl, liveData {
                try {
                    emit(loadBitmapFromUrl(imageUrl))

                } catch (e: LoadingException) {
                    Log.e(
                        PictureListAdapter.TAG,
                        "Leaving the default picture instead of $imageUrl..."
                    )
                }
            })
        }

        return imageCache[imageUrl]!!
    }
}