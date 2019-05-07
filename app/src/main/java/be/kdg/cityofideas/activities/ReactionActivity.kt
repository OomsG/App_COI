package be.kdg.cityofideas.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import be.kdg.cityofideas.R
import be.kdg.cityofideas.fragments.ReactionFragment
import be.kdg.cityofideas.model.ideations.Ideas

class ReactionActivity : AppCompatActivity() {

    private var name: TextView = findViewById(R.id.IdeaUserName)
    private var voteCount: TextView = findViewById(R.id.IdeaVoteCount)
    private var reactionCount: TextView = findViewById(R.id.IdeaReactionCount)
    private var shareCount: TextView = findViewById(R.id.IdeaShareCount)
    private var voteButton: Button = findViewById(R.id.IdeaVoteButton)
    private var shareButton: Button = findViewById(R.id.IdeaShareButton)
    private var idea: Ideas = intent.getSerializableExtra(IDEA) as Ideas

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("reactionActivity", idea.Title)
        super.onCreate(savedInstanceState)
        initialiseViews()
        setContentView(R.layout.activity_reaction)

    }

    fun initialiseViews() {
        name.text = idea.Title
        voteCount
        reactionCount
        shareCount
        voteButton.setOnClickListener { }
        shareButton.setOnClickListener { }
        val fragment = supportFragmentManager.findFragmentById(R.id.ReactionFragment) as ReactionFragment
        fragment.setId(idea.IdeaId)
    }
}
