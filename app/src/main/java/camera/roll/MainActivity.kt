package camera.roll

import RandomuserApiRequestHandler
import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import camera.roll.permissions.ensurePermission
import camera.roll.permissions.requestPermissionsHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val apiService = RandomuserApiRequestHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = this.findViewById<TextView>(R.id.main_title)

        ensurePermission(permission = Manifest.permission.INTERNET, activity = this) {
            CoroutineScope(Dispatchers.IO).launch {
                val data = apiService.getData(500)

                Log.d(TAG, data.toString())
                withContext(Dispatchers.Main) {
                    view.text = data.toString()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        requestPermissionsHandler(
            requestCode = requestCode,
            permissions = permissions,
            grantResults = grantResults
        )
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
