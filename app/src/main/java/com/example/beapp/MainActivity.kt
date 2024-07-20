package com.example.beapp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.beapp.databinding.ActivityMainBinding
import com.example.beapp.databinding.ItemDialogBinding
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class MainActivity : AppCompatActivity(),  DecoratedBarcodeView.TorchListener {

    private lateinit var barcodeView: DecoratedBarcodeView

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        barcodeView = binding.barcodeScannerView

        binding.barcodeScannerView.setTorchListener(this)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            startScanner()
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanner()
            } else {
                Toast.makeText(
                    this,
                    "Camera permission is required to scan QR codes",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startScanner() {
        barcodeView.decodeContinuous { result ->
            // QR kod aniqlandi, shu joyda kerakli kerakli manbani oling
            runOnUiThread {
//                Toast.makeText(this, "QR Code scanned: ${result.text}", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this)
                val customAlertDialogBinding = ItemDialogBinding.inflate(layoutInflater)

                builder.setView(customAlertDialogBinding.root)

                val alertDialog = builder.create()
                alertDialog.show()

                customAlertDialogBinding.apply {
                    txt.text = result.text
                }
            }
        }
    }


    override fun onTorchOn() {

    }

    override fun onTorchOff() {

    }


    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }
}