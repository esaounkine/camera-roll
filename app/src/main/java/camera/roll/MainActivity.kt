package camera.roll

import RandomuserApiRequestHandler
import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import camera.roll.permissions.ensurePermission
import camera.roll.permissions.requestPermissionsHandler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ensurePermission(permission = Manifest.permission.INTERNET, activity = this) {
            Log.d(TAG, "Callback with permission")
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
