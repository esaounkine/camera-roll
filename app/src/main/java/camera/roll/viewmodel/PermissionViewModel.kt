package camera.roll.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import camera.roll.permission.HandlerEntry

class PermissionViewModel : ViewModel() {
    val permissionRequestHandlers = mutableMapOf<String, HandlerEntry>()
    val permissionsGranted = MutableLiveData<Boolean>(false)
    val permissionDenied = MutableLiveData<Boolean>(false)

    fun grantPermission() {
        permissionsGranted.value = true
    }

    fun denyPermission() {
        permissionDenied.value = true
    }
}