package camera.roll.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import camera.roll.model.PictureItem
import camera.roll.view.GalleryCellView
import camera.roll.viewmodel.GalleryViewModel

class PictureListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: GalleryViewModel
) :
    RecyclerView.Adapter<PictureViewHolder>() {

    init {
        viewModel.content.observe(lifecycleOwner, Observer<List<PictureItem>> {
            Log.d(TAG, "Refreshing data in the adapter")
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(
            view = GalleryCellView(
                context = parent.context,
                lifecycleOwner = lifecycleOwner,
                viewModel = viewModel
            )
        )
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val item = viewModel.content.value!![position]

        holder.view.loadUrl(item.imageUrl)
    }

    override fun getItemCount(): Int = viewModel.content.value?.size ?: 0

    companion object {
        val TAG = PictureListAdapter::class.java.simpleName
    }
}

class PictureViewHolder(val view: GalleryCellView) : RecyclerView.ViewHolder(view)