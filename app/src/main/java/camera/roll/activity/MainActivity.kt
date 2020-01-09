package camera.roll.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import camera.roll.R
import camera.roll.model.PictureItem
import camera.roll.viewmodel.PictureListViewModel

class MainActivity : AbstractActivity() {

    private val textView: TextView by lazy {
        findViewById<TextView>(R.id.main_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        withPermission(Manifest.permission.INTERNET) {
            val model = ViewModelProvider(this).get(PictureListViewModel::class.java)
            model.pictureList.observe(this, Observer<List<PictureItem>> { data ->
                Log.d(TAG, data.toString())

                textView.text = data.toString()
            })

        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
