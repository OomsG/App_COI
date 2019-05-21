package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter.ideaSelectionListener
import be.kdg.cityofideas.fragments.IdeaFragment
import be.kdg.cityofideas.login.LoggedInUserView

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
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(context: Context) {
        createIdeaButton = findViewById(R.id.fab)
        createIdeaButton.setOnClickListener {
            val createIdea = Intent(this, CreateIdeaActivity::class.java)
            createIdea.putExtra(IDEATION_ID, intent.getIntExtra(IDEATION_ID,1))
            createIdea.putExtra(PROJECT_ID,intent.getIntExtra(PROJECT_ID, 1))
            startActivity(createIdea)
        }
        val fragment = supportFragmentManager.findFragmentById(R.id.IdeaFragment) as IdeaFragment
        fragment.setId(intent.getIntExtra(IDEATION_ID, 1), intent.getIntExtra(PROJECT_ID, 1))
    }
}
