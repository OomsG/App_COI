package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.*
import be.kdg.cityofideas.database.DatabaseManager
import be.kdg.cityofideas.fragments.ReactionFragment
import be.kdg.cityofideas.login.LoggedInUserView
import be.kdg.cityofideas.login.loggedInUser
import be.kdg.cityofideas.model.ideations.Idea
import be.kdg.cityofideas.model.ideations.Vote
import be.kdg.cityofideas.model.ideations.VoteType
import be.kdg.cityofideas.rest.RestClient
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_reaction.*

class ReactionActivity : BaseActivity(), YouTubePlayer.OnInitializedListener {
    private lateinit var layout: LinearLayout
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reaction)
        manager = DatabaseManager(this)
        helper = manager.dbHelper
        getIdea(this)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home)
            this.finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initialiseViews(idea: Idea) {
        layout = findViewById(R.id.LinearLayoutReactionIdea)
        getIdeaDetails(idea, this, layout)
        idea.IdeaObjects!!.forEach {
            it.Url?.let {
                url = it
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = YouTubePlayerSupportFragment()
                fragmentTransaction.add(R.id.LinearLayoutReactionIdea, fragment).commit()
                fragment.initialize(YOUTUBE_API, this)
            }
        }

        // name.text = idea.
        ReactionIdeaTitle.text = idea.Title
        ReactionIdeaVoteCount.text = getIdeaVoteCount(idea)
        ReactionIdeaReactionCount.text = getReactionCount(idea)
        ReactionIdeaShareCount.text = getIdeaShareCount(idea)
        ReactionIdeaVoteButton.setOnClickListener {
            if (loggedInUser != null) {
                if (validVote(idea)) {
                    Thread {
                        RestClient(this).createVote(idea.IdeaId, VoteType.VOTE, loggedInUser!!.UserId)
                    }.start()
                    Toast.makeText(it.context, "U heeft gestemd!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(it.context, "U bent niet ingelogd!", Toast.LENGTH_LONG).show()
            }
        }


        ReactionIdeaShareButton.setOnClickListener {
            if (loggedInUser != null) {
                shareButtonPressed(idea.IdeaId, loggedInUser!!)
            } else {
                Toast.makeText(it.context, "U bent niet ingelogd!", Toast.LENGTH_LONG).show()
            }
        }

        createReaction.setOnClickListener {
            if (!ReactionText.text.isNullOrEmpty()) {
                if (loggedInUser != null) {
                    Thread {
                        RestClient(this)
                            .createReaction(
                                ReactionText.text.toString(),
                                loggedInUser!!.UserId,
                                idea.IdeaId,
                                "idea"
                            )
                    }.start()
                }
            } else {
                Toast.makeText(this, "U bent niet aangemeld", Toast.LENGTH_SHORT).show()
            }
        }

        val fragment = supportFragmentManager.findFragmentById(R.id.ReactionFragment) as? ReactionFragment
        fragment?.id = intent.getIntExtra(IDEA_ID, 1)
    }

    private fun shareButtonPressed(id: Int, loggedInUser: LoggedInUserView) {
        val shareIntent = Intent(this, ShareVoteActivity::class.java)
        shareIntent.putExtra(IDEA_ID, id)
        shareIntent.putExtra(LOGGEDIN_USER, loggedInUser.UserId)
        startActivity(shareIntent)
    }

    @SuppressLint("CheckResult")
    private fun getIdea(context: Context) {
        RestClient(context)
            .getIdea("idea/" + intent.getIntExtra(IDEA_ID, 1))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe {
                val idea = Idea(
                    it.IdeaId,
                    it.Position,
                    it.IdeaObjects,
                    it.IdeaTags,
                    it.Reported,
                    it.Title,
                    it.Ideation,
                    it.User,
                    it.IoTSetup,
                    it.Votes,
                    it.Reactions
                )
                initialiseViews(idea)
            }
    }


    private fun getUrl(): String {
        val array = url!!.split('/')
        val final = array.last().split('=')
        return final.last()
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        if (!wasRestored) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            Log.d("URL", getUrl())
            player.loadVideo(getUrl())
            player.play()
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, error: YouTubeInitializationResult) {
        // YouTube error
        val errorMessage = error.toString()
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        Log.d("errorMessage:", errorMessage)
    }
}
