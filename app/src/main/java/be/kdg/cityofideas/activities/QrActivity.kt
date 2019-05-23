package be.kdg.cityofideas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import be.kdg.cityofideas.R
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject

class QrActivity : BaseActivity() {
    private lateinit var qrScanIntegrator: IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator.setOrientationLocked(false)
        qrScanIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, "Geen resultaat gevonden", Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                val content = result.contents
                val type = content.substringAfterLast('/').substringBeforeLast('?')

                Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
