package camera.roll.view

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import camera.roll.R
import camera.roll.adapter.PictureListAdapter
import camera.roll.model.LoadingException
import camera.roll.network.loadBitmapFromUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryCellView(context: Context) : ImageView(context) {

    private fun createLayoutParams(): ViewGroup.LayoutParams {
        val params = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        params.width = context.resources.getDimension(R.dimen.gallery_max_image_width).toInt()

        params.height = context.resources.getDimension(R.dimen.gallery_max_image_height).toInt()

        val margin = context.resources.getDimension(R.dimen.global_spacing_margin).toInt()
        params.setMargins(margin, margin, 0, 0)

        return params
    }

    init {
        layoutParams = createLayoutParams()
    }

    fun loadUrl(imageUrl: String) {
        setImageResource(R.drawable.default_image)
        GlobalScope.launch {
            try {
                val bitmap = loadBitmapFromUrl(imageUrl)

                withContext(Dispatchers.Main) {
                    setImageBitmap(bitmap)
                }
            } catch (e: LoadingException) {
                Log.e(PictureListAdapter.TAG, "Leaving the default picture...")
            }
        }
    }
}