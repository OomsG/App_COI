package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.widget.*
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.*
import be.kdg.cityofideas.fragments.ReactionFragment
import be.kdg.cityofideas.fragments.YoutubeFragment
import be.kdg.cityofideas.model.ideations.Idea
import be.kdg.cityofideas.rest.RestClient
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ReactionActivity : BaseActivity() , YouTubePlayer.OnInitializedListener{
    private lateinit var title: TextView
    private lateinit var name: TextView
    private lateinit var voteCount: TextView
    private lateinit var reactionCount: TextView
    private lateinit var shareCount: TextView
    private lateinit var voteButton: Button
    private lateinit var shareButton: Button
    private lateinit var layout: LinearLayout

    private var voteCounter = 0
    private var shareCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reaction)
        getIdea(this)
    }

    private fun initialiseViews(idea: Idea) {
        Log.d("help", idea.toString())
        layout = findViewById(R.id.LinearLayoutReactionIdea)
        title = findViewById(R.id.ReactionIdeaTitle)
        //name = findViewById(R.id.IdeaUserName)
        getIdeaDetails(idea, this, layout)
        idea.IdeaObjects!!.forEach {
            it.Url?.let {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = YouTubePlayerSupportFragment()
                fragmentTransaction.add(R.id.LinearLayoutReactionIdea,fragment).commit()
                fragment.initialize(YOUTUBE_API,this)
            }
        }
        voteCount = findViewById(R.id.ReactionIdeaVoteCount)
        reactionCount = findViewById(R.id.ReactionIdeaReactionCount)
        shareCount = findViewById(R.id.ReactionIdeaShareCount)
        voteButton = findViewById(R.id.ReactionIdeaVoteButton)
        shareButton = findViewById(R.id.ReactionIdeaShareButton)

        // name.text = idea.
        title.text = idea.Title
        voteCount.text = getIdeaVoteCount(idea, voteCounter)
        reactionCount.text = getReactionCount(idea)
        shareCount.text = getIdeaShareCount(idea, shareCounter)
        voteButton.setOnClickListener { }
        shareButton.setOnClickListener { }

        val fragment = supportFragmentManager.findFragmentById(R.id.ReactionFragment) as? ReactionFragment
        fragment?.setId(intent.getIntExtra(IDEA_ID, 1))
    }

    @SuppressLint("CheckResult")
    private fun getIdea(context: Context)  {
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
    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, wasRestored: Boolean) {
        if (!wasRestored) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            player.loadVideo("6EEW-9NDM5k")
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
