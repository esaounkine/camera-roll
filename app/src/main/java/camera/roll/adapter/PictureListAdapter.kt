package camera.roll.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import camera.roll.model.PictureItem
import camera.roll.view.GalleryCellView

class PictureListAdapter(private var data: List<PictureItem>) :
    RecyclerView.Adapter<PictureViewHolder>() {

    fun refreshData(newData: List<PictureItem>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(view = GalleryCellView(parent.context))
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val item = data[position]

        holder.view.loadUrl(item.imageUrl)
    }

    override fun getItemCount(): Int = data.size

    companion object {
        val TAG = PictureListAdapter::class.java.simpleName
    }
}

class PictureViewHolder(val view: GalleryCellView) : RecyclerView.ViewHolder(view)