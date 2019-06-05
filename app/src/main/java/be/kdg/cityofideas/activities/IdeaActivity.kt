package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter.ideaSelectionListener
import be.kdg.cityofideas.fragments.IdeaFragment
import be.kdg.cityofideas.login.LoggedInUserView
import be.kdg.cityofideas.login.loggedInUser

const val IDEA_ID: String = "idea"
const val LOGGEDIN_USER: String = "loggedinUser"


class IdeaActivity : BaseActivity(), ideaSelectionListener {
    override fun shareButtonPressed(id: Int, loggedInUser: LoggedInUserView) {
        val shareIntent = Intent(this, ShareVoteActivity::class.java)
        shareIntent.putExtra(IDEA_ID, id)
        shareIntent.putExtra(LOGGEDIN_USER, loggedInUser.UserId)
        startActivity(shareIntent)
    }

    private lateinit var createIdeaButton: FloatingActionButton

    override fun onIdeaSelected(id: Int) {
        val ideaIntent = Intent(this, ReactionActivity::class.java)
        ideaIntent.putExtra(IDEA_ID, id)
        startActivity(ideaIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idea)
        initialiseViews(this)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home)
            this.finish()
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(context: Context) {
        createIdeaButton = findViewById(R.id.fab)
        createIdeaButton.setOnClickListener {
            if (loggedInUser != null) {
                Log.d("ideationId->ideaA",intent.getIntExtra(IDEATION_ID, 1).toString())
                Log.d("projectId->ideaA",intent.getIntExtra(PROJECT_ID, 1).toString())
                val createIdea = Intent(context, CreateIdeaActivity::class.java)
                createIdea.putExtra(IDEATION_ID, intent.getIntExtra(IDEATION_ID, 1))
                createIdea.putExtra(PROJECT_ID, intent.getIntExtra(PROJECT_ID, 1))
                startActivity(createIdea)
            }else{
                Toast.makeText(this,"U bent niet aangemeld!",Toast.LENGTH_LONG).show()
            }
        }
        val fragment = supportFragmentManager.findFragmentById(R.id.IdeaFragment) as IdeaFragment
        fragment.setId(intent.getIntExtra(IDEATION_ID, 1), intent.getIntExtra(PROJECT_ID, 1))
    }
}
