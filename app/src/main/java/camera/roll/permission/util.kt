package camera.roll.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper(private val permissionRequestHandlers: MutableMap<String, HandlerEntry>) {

    fun ensurePermission(
        permission: String,
        activity: Activity,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        if (ContextCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionRequestHandlers[permission] =
                HandlerEntry(onGranted = onGranted, onDenied = onDenied)

            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                System.nanoTime().toInt()
            )
        } else {
            Log.d(TAG, "Internet permission already granted")
            onGranted()
        }
    }

    fun requestPermissionsHandler(
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // this construct supports a single permission request at a time
        if (permissions.isNotEmpty() && grantResults.isNotEmpty()) {
            val permission = permissions[0]
            val grantResult = grantResults[0]

            val handlerEntry = permissionRequestHandlers[permission]
            if (handlerEntry != null) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission $permission granted :)")
                    handlerEntry.onGranted()
                } else {
                    Log.e(TAG, "Permission $permission not granted :(")
                    handlerEntry.onDenied()
                }
            } else {
                Log.e(TAG, "Handler entry not found for $permission (grantResult: $grantResult)")
            }
        } else {
            Log.e(TAG, "Why is this called with empty/unaligned permissions and grant results")
        }
    }

    companion object {
        private const val TAG = "Permissions helper"
    }
}

data class HandlerEntry(
    val onGranted: () -> Unit,
    val onDenied: () -> Unit
)
