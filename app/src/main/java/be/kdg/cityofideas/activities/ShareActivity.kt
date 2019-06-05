package be.kdg.cityofideas.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.widget.Button
import be.kdg.cityofideas.R
import com.facebook.CallbackManager
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog

class ShareActivity : AppCompatActivity() {
    private val host = "10.0.2.2"
    private val port = 5001
    private val HTTPS_PREFIX = "https://"
    private val platform = "Antwerpen"
    private val project = "/Project/"
    private val projectinit = "Project?projectId="
    private val ideationinit = "Ideation?ideationId="

    private lateinit var callbackManager: CallbackManager
    private lateinit var shareDialog: ShareDialog
    private lateinit var fbButton: Button
    private lateinit var twButton: Button
    private val text = "Dit is geweldig!"
    private var url: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        val projectId: Int = intent.getIntExtra(PROJECT_ID, -1)
        val ideationId = intent.getIntExtra(IDEATION_ID, -1)
        if (!projectId.equals(-1)) {
            url = HTTPS_PREFIX + host + ":" + port + platform + project + projectinit + projectId
        }
        if (!ideationId.equals(-1)) {
            url = HTTPS_PREFIX + host + ":" + port + platform + project + ideationinit + ideationId
        }
        initFacebook()
        createLayout()
        initialiseViews(url)
    }

    private fun initFacebook() {
        callbackManager = CallbackManager.Factory.create()
        shareDialog = ShareDialog(this)
    }

    private fun createLayout() {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels
        window.setLayout((width * .85).toInt(), (height * .25).toInt())
        window.attributes.gravity = Gravity.BOTTOM
    }

    private fun initialiseViews(url:String) {
        fbButton = findViewById(R.id.fbShare)
        twButton = findViewById(R.id.twShare)
        Log.d("url", url)
        twButton.setOnClickListener {

            val shareUrl = "http://www.twitter.com/intent/tweet?url=${url}&text=${text}"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(shareUrl)
            startActivity(i)
        }
        fbButton.setOnClickListener {
            if (ShareDialog.canShow(ShareLinkContent::class.java)) {
                val linkContent = ShareLinkContent.Builder()
                    .setQuote(text)
                    .setContentUrl(Uri.parse(url))
                    .build()
                shareDialog.show(linkContent)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}
