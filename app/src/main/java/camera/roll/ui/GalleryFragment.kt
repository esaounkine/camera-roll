package camera.roll.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import camera.roll.adapter.PictureListAdapter
import camera.roll.viewmodel.GalleryViewModel

class GalleryFragment : AbstractFragment() {

    private val model by lazy {
        ViewModelProvider(this).get(GalleryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return RecyclerView(context!!).apply {
            adapter = PictureListAdapter(this@GalleryFragment, model)
            layoutManager = GridLayoutManager(activity, 5)
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    }
}