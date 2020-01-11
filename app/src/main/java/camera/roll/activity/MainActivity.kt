package camera.roll.activity

import android.os.Bundle
import android.util.Log
import camera.roll.R
import camera.roll.ui.GalleryFragment

class MainActivity : AbstractActivity(R.id.main_container) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        withPermissions {
            Log.d(TAG, "Secured permissions, loading the gallery fragment")

            loadFragment(GalleryFragment())
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
