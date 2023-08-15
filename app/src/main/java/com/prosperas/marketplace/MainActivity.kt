package com.prosperas.marketplace;

import SDKSettingsView
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
class MainActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Camera permission is already granted
            // You can proceed with your camera-related functionality
        } else {
            // Camera permission is not granted
            // Request the permission from the user
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }


        val initialLocale = "es-rMX"
        val initialApikey = "f0627e97-48fb-456b-ba05-c20ce8c56105"
        val initialSessionId = "d/fvIRnSFf2gQslGnCVm/pz7u8K5JTkEFY5w7KJ1rSawvF0tFj68wuL+Zuy2DrEkr8I/dqUj27iW0fg4RyMaU+jsqimIAsTVZnJjjYWpymAwyFRXa7rZyk8nz0jL1DWG"
        val initialUrl = "https://creditos-web.prosperas.com"

        val sdkSettingsView = SDKSettingsView(
            this,
            initialLocale,
            initialApikey,
            initialSessionId,
            initialUrl
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                val result = data!!.extras!!
                val success = result.getString("result")
                Toast.makeText(applicationContext, success, Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission is granted
                // You can proceed with your camera-related functionality
            } else {
                // Camera permission is denied
                // You can show a message or handle it accordingly
            }
        }
    }
}
