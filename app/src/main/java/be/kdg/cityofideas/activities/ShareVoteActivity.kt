package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import be.kdg.cityofideas.R
import com.facebook.share.widget.ShareDialog
import com.facebook.CallbackManager
import android.content.Intent
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.Button
import com.facebook.share.model.ShareLinkContent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import be.kdg.cityofideas.adapters.getVotes
import be.kdg.cityofideas.model.ideations.Idea
import be.kdg.cityofideas.model.ideations.Vote
import be.kdg.cityofideas.model.ideations.VoteType
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_share.*


class ShareVoteActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var shareDialog: ShareDialog

    private val sharedUrl: String = "https://localhost:5001/Antwerpen/Project/Idea?ideaId="
    private val message: String = "Dit is geweldig!"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_vote)
        initFacebook()
        createLayout()
        initialiseViews()
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

    private fun initialiseViews() {
        twShare.setOnClickListener {
            if (validTweet(intent.getIntExtra(IDEA_ID,0))) {
                shareTweet(sharedUrl, message)
            }else{
                Toast.makeText(this,"U hebt reeds op deze manier gestemd!",Toast.LENGTH_LONG).show()
            }
        }
        fbShare.setOnClickListener {
            if (validFbShare(intent.getIntExtra(IDEA_ID,0))){
            shareFacebook(sharedUrl, message)
        }else{
            Toast.makeText(this,"U hebt reeds op deze manier gestemd!",Toast.LENGTH_LONG).show()
        }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun shareTweet(sharedUrl: String, message: String) {
        val url =
            "http://www.twitter.com/intent/tweet?url=${sharedUrl + intent.getIntExtra(IDEA_ID, 0)}&text=${message}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
        Thread {
            RestClient(this).createVote(
                intent.getIntExtra(IDEA_ID, 0),
                VoteType.SHARE_TW,
                intent.getStringExtra(LOGGEDIN_USER)
            )
        }.start()
    }

    fun shareFacebook(sharedUrl: String, message: String) {
        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            val linkContent = ShareLinkContent.Builder()
                .setQuote(message)
                .setContentUrl(Uri.parse(sharedUrl + intent.getIntExtra(IDEA_ID, 0)))
                .build()
            shareDialog.show(linkContent)
        }
        Thread {
            RestClient(this).createVote(
                intent.getIntExtra(IDEA_ID, 0),
                VoteType.SHARE_FB,
                intent.getStringExtra(LOGGEDIN_USER)
            )
        }.start()
    }

    fun validFbShare(id: Int): Boolean {
        val votes = getVotes()
        var state: Boolean = false
        votes.forEach {
            if (it.VoteType == VoteType.SHARE_FB || it.Idea!!.IdeaId == id) {
                state = true
            }
        }
        return state
    }

    fun validTweet(id: Int): Boolean {
        val votes = getVotes()
        var state: Boolean = false
        votes.forEach {
            if (it.VoteType == VoteType.SHARE_TW || it.Idea!!.IdeaId == id) {
                state = true
            }
        }
        return state
    }
}

