package camera.roll.activity

import androidx.appcompat.app.AppCompatActivity
import camera.roll.permission.PermissionHelper

abstract class AbstractActivity : AppCompatActivity() {

    private val permissionHelper = PermissionHelper()

    protected fun withPermission(permission: String, closure: () -> Unit) =
        permissionHelper.ensurePermission(
            permission = permission,
            activity = this,
            closure = closure
        )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) = permissionHelper.requestPermissionsHandler(
        requestCode = requestCode,
        permissions = permissions,
        grantResults = grantResults
    )
}