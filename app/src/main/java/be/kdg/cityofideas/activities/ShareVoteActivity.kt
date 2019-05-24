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
import be.kdg.cityofideas.model.ideations.Vote
import be.kdg.cityofideas.model.ideations.VoteType
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.schedulers.Schedulers


class ShareVoteActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var shareDialog: ShareDialog
    private lateinit var fbButton: Button
    private lateinit var twButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_vote)
        initFacebook()
        createLayout()
        getdata()
    }

    @SuppressLint("CheckResult")
    private fun getdata() {
        Log.d("userId",intent.getStringExtra(LOGGEDIN_USER))
        val votes: MutableList<Vote>? = null
        RestClient(this)
            .getSharedVotes("sharedVotes/" + intent.getIntExtra(IDEA_ID, 0))
            .map {
                it.forEach {
                    it.User!!.Id == intent.getStringExtra(LOGGEDIN_USER)
                    votes!!.add(Vote(it.VoteId, it.Confirmed, it.VoteType, it.User, it.Idea))
                }
            }
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                initialiseViews(votes)
            }

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

    private fun initialiseViews(votes: MutableList<Vote>?) {
        fbButton = findViewById(R.id.fbShare)
        twButton = findViewById(R.id.twShare)
        Log.d("shareType", votes?.size.toString())
        if (votes != null) {
            votes.forEach {
                Log.d("shareType", it.VoteType.toString())
                when (it.VoteType) {

                    VoteType.VOTE -> {
                        twButton.setOnClickListener {
                            val url = "http://www.twitter.com/intent/tweet?url=YOURURL&text=YOURTEXT"
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
                        fbButton.setOnClickListener {
                            if (ShareDialog.canShow(ShareLinkContent::class.java)) {
                                val linkContent = ShareLinkContent.Builder()
                                    .setQuote("Dit is een geweldig idee!")
                                    .setContentUrl(Uri.parse("http://facebook.com"))
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
                    }
                    VoteType.SHARE_FB -> {
                        fbButton.setOnClickListener {
                            Toast.makeText(it.context, "U reeds op dewe manier gestemd", Toast.LENGTH_LONG).show()
                            twButton.setOnClickListener {
                                val url = "http://www.twitter.com/intent/tweet?url=YOURURL&text=YOURTEXT"
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
                        }
                    }
                    VoteType.SHARE_TW -> {
                        twButton.setOnClickListener {
                            Toast.makeText(it.context, "U reeds op dewe manier gestemd", Toast.LENGTH_LONG).show()
                            fbButton.setOnClickListener {
                                if (ShareDialog.canShow(ShareLinkContent::class.java)) {
                                    val linkContent = ShareLinkContent.Builder()
                                        .setQuote("Dit is een geweldig idee!")
                                        .setContentUrl(Uri.parse("http://facebook.com"))
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
                        }
                    }
                    VoteType.IOT -> {
                        twButton.setOnClickListener {
                            val url = "http://www.twitter.com/intent/tweet?url=YOURURL&text=YOURTEXT"
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
                        fbButton.setOnClickListener {
                            if (ShareDialog.canShow(ShareLinkContent::class.java)) {
                                val linkContent = ShareLinkContent.Builder()
                                    .setQuote("Dit is een geweldig idee!")
                                    .setContentUrl(Uri.parse("http://facebook.com"))
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
                    }
                    null -> return
                }
            }
        } else {
            twButton.setOnClickListener {
                val url = "http://www.twitter.com/intent/tweet?url=YOURURL&text=YOURTEXT"
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
            fbButton.setOnClickListener {
                if (ShareDialog.canShow(ShareLinkContent::class.java)) {
                    val linkContent = ShareLinkContent.Builder()
                        .setQuote("Dit is een geweldig idee!")
                        .setContentUrl(Uri.parse("http://facebook.com"))
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
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}
