package camera.roll.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import camera.roll.R
import camera.roll.model.LoadingException
import camera.roll.model.PictureItem
import camera.roll.network.loadBitmapFromUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PictureListAdapter(private var data: List<PictureItem>) :
    RecyclerView.Adapter<PictureViewHolder>() {

    fun refreshData(newData: List<PictureItem>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.gallery_item,
            parent,
            false
        )

        return PictureViewHolder(container = layout)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val item = data[position]
        val textView = holder.container.findViewById<TextView>(R.id.gallery_item_text)
        val imageView = holder.container.findViewById<ImageView>(R.id.gallery_item_image)
        GlobalScope.launch {
            try {
                val bitmap = loadBitmapFromUrl(item.imageUrl)

                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
                }
            } catch (e: LoadingException) {
                Log.e(TAG, "Leaving the default picture...")
            }
        }
        imageView.setImageResource(R.drawable.default_image)
        textView.text = item.imageUrl
    }

    override fun getItemCount(): Int = data.size

    companion object {
        val TAG = PictureListAdapter::class.java.simpleName
    }
}

class PictureViewHolder(val container: View) : RecyclerView.ViewHolder(container)