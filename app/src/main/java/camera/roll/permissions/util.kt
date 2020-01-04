package camera.roll.permissions

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val TAG = "Permissions helper"

private val permissionRequestHandlers = mutableMapOf<String, () -> Unit>()

fun ensurePermission(
    permission: String,
    activity: Activity,
    closure: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            activity,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        permissionRequestHandlers[permission] = closure

        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            System.nanoTime().toInt()
        )
    } else {
        Log.d(TAG, "Internet permission already granted")
        closure()
    }
}

fun requestPermissionsHandler(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
) {
    // this construct supports singular permission request at a time
    if (permissions.isNotEmpty() && grantResults.isNotEmpty()) {
        val permission = permissions[0]
        val grantResult = grantResults[0]

        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            val closure = permissionRequestHandlers.get(permission)
            if (closure != null) {
                closure()
            } else {
                Log.d(TAG, "Permission $permission granted, but no closure defined")
            }
        } else {
            Log.e(TAG, "Permission $permission not granted :(")
        }
    } else {
        Log.e(TAG, "Why is this called with empty/unaligned permissions and grant results")
    }
}
