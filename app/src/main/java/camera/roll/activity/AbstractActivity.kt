package camera.roll.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import camera.roll.ui.PermissionsFragment
import camera.roll.viewmodel.PermissionViewModel

abstract class AbstractActivity(private val mainContainerId: Int) : FragmentActivity() {
    private val permissionModel by lazy {
        ViewModelProvider(this).get(PermissionViewModel::class.java)
    }

    protected fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(mainContainerId, fragment)
            .commit()
    }

    protected fun withPermissions(closure: () -> Unit) {
        // TODO validate that the permissions have already been granted
        //  and proceed right away instead of waiting for user interaction

        // TODO replace this generic logic of attaching the fragment to
        //  main container by a popover dialog
        loadFragment(PermissionsFragment())

        permissionModel.permissionsGranted.observe(this, Observer { permissionsGranted ->
            if (permissionsGranted) {
                supportFragmentManager.popBackStack()

                closure()
            }
        })
    }
}