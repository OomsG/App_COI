package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Layout
import android.util.Log
import android.widget.*
import be.kdg.cityofideas.R

import be.kdg.cityofideas.adapters.*
import be.kdg.cityofideas.fragments.ReactionFragment
import be.kdg.cityofideas.model.ideations.Idea
import be.kdg.cityofideas.model.ideations.VoteType
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.NullPointerException


class ReactionActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
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

    var idea: Idea = Idea(
        0, null, null, null, "hello", null, null
        , null, null, null
    )
        set(idea) {
            field = idea
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reaction)
        //getIdea(this)
        Log.d("help",idea.toString())
        initialiseViews()
    }

    private fun initialiseViews() {
        toolbar = findViewById(R.id.ReactionToolbar)
        layout = findViewById(R.id.LinearLayoutReactionIdea)
        title = findViewById(R.id.ReactionIdeaTitle)
        //name = findViewById(R.id.IdeaUserName)
        getIdeaDetails(idea, this, layout)
        voteCount = findViewById(R.id.ReactionIdeaVoteCount)
        reactionCount = findViewById(R.id.ReactionIdeaReactionCount)
        shareCount = findViewById(R.id.ReactionIdeaShareCount)
        voteButton = findViewById(R.id.ReactionIdeaVoteButton)
        shareButton = findViewById(R.id.ReactionIdeaShareButton)

        // name.text = idea!!.Title
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
    private fun getIdea(context: Context) {
        RestClient(context)
            .getIdea("idea/" + intent.getIntExtra(IDEA_ID, 2))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe {
                idea = it
            }
    }
}
