package camera.roll.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import camera.roll.R
import camera.roll.adapter.PictureListAdapter
import camera.roll.model.PictureItem
import camera.roll.viewmodel.GalleryViewModel

class GalleryFragment : AbstractFragment() {

    private val model by lazy {
        ViewModelProvider(this).get(GalleryViewModel::class.java)
    }

    private val pictureListAdapter: PictureListAdapter =
        PictureListAdapter(emptyList())

    private val viewManager by lazy {
        LinearLayoutManager(activity)
    }

    private val galleryRecyclerView: RecyclerView by lazy {
        (activity?.findViewById<RecyclerView>(R.id.gallery_recycler_view)
            ?: throw RuntimeException("Failed to resolve the recycler view"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gallery_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model.pictureList.observe(this, Observer<List<PictureItem>> { data ->
            Log.d(TAG, "Refreshing data in the adapter")
            pictureListAdapter.refreshData(data)
        })

        galleryRecyclerView.apply {
            adapter = pictureListAdapter
            layoutManager = viewManager
        }

    }

    companion object {
        private val TAG = GalleryFragment::class.java.simpleName
    }
}