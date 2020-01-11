package camera.roll.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import camera.roll.R
import camera.roll.model.PictureItem
import camera.roll.viewmodel.PictureListViewModel

class MainActivity : AbstractActivity(R.id.main_container) {

    private val pictureListModel by lazy {
        ViewModelProvider(this).get(PictureListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        withPermissions {
            pictureListModel.pictureList.observe(this, Observer<List<PictureItem>> { data ->
                Log.d(TAG, data.toString())


            })
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
