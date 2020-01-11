package camera.roll.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import camera.roll.R
import camera.roll.permission.PermissionHelper
import camera.roll.viewmodel.PermissionViewModel

class PermissionsFragment : AbstractFragment() {

    private val model: PermissionViewModel by lazy {
        ViewModelProvider(activity!!).get(PermissionViewModel::class.java)
    }

    private val permissionHelper by lazy {
        PermissionHelper(model.permissionRequestHandlers)
    }

    private val grantPermissionsButton: Button by lazy {
        activity?.findViewById<Button>(R.id.grant_permissions_button)
            ?: throw RuntimeException("Failed to resolve the button")
    }

    private val grantPermissionLabel: TextView by lazy {
        activity?.findViewById<TextView>(R.id.grant_permission_label)
            ?: throw RuntimeException("Failed to resolve the text view")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.permissions_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        grantPermissionsButton.setOnClickListener {
            requestPermissions()
        }

        model.permissionDenied.observe(activity!!, Observer { value ->
            grantPermissionLabel.text =
                getText(if (value) R.string.missing_permissions_text else R.string.grant_permissions_text)
        })

        requestPermissions()
    }

    private fun requestPermissions() = permissionHelper.ensurePermission(
        permission = Manifest.permission.INTERNET,
        activity = activity!!,
        onGranted = model::grantPermission,
        onDenied = model::denyPermission
    )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) = permissionHelper.requestPermissionsHandler(
        permissions = permissions,
        grantResults = grantResults
    )
}