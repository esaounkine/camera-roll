package camera.roll.activity

import RandomuserApiRequestHandler
import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import camera.roll.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AbstractActivity() {

    private val apiService = RandomuserApiRequestHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = this.findViewById<TextView>(R.id.main_title)

        withPermission(Manifest.permission.INTERNET) {
            CoroutineScope(Dispatchers.IO).launch {
                val data = apiService.getData(500)

                Log.d(TAG, data.toString())
                withContext(Dispatchers.Main) {
                    view.text = data.toString()
                }
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
