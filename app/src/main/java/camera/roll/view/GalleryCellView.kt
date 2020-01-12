package camera.roll.view

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import camera.roll.R
import camera.roll.viewmodel.GalleryViewModel

class GalleryCellView(
    context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: GalleryViewModel
) : ImageView(context) {

    private var currentImageUrl: String? = null

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
        reset()
    }

    private fun reset() {
        setImageResource(R.drawable.default_image)
    }

    fun loadUrl(imageUrl: String) {
        if (currentImageUrl == imageUrl) {
            Log.d(TAG, "The view is already loaded from $imageUrl, skipping...")
        }

        currentImageUrl = imageUrl
        reset()

        //TODO find a way to cancel unfinished jobs
        viewModel.getImageFromCache(imageUrl).observe(lifecycleOwner, Observer { bitmap ->
            if (bitmap != null) {
                if (currentImageUrl == imageUrl) {
                    setImageBitmap(bitmap)
                } else {
                    Log.d(
                        TAG,
                        "The cell view has moved on ($currentImageUrl != $imageUrl), skipping..."
                    )
                }
            }
        })


    }

    companion object {
        val TAG = GalleryCellView::class.java.simpleName
    }
}