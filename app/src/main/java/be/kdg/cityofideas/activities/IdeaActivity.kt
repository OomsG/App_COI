package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter.ideaSelectionListener
import be.kdg.cityofideas.fragments.IdeaFragment

const val IDEA_ID : String = "idea"

class IdeaActivity : AppCompatActivity(), ideaSelectionListener {
    private lateinit var toolbar: Toolbar
    private lateinit var Title: TextView

    override fun onIdeaSelected(id: Int) {
        val intent = Intent(this, ReactionActivity::class.java)
        intent.putExtra(IDEA_ID,id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idea)
        initialiseViews(this)
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(context: Context) {
        toolbar = findViewById(R.id.IdeaToolbar)
        val fragment = supportFragmentManager.findFragmentById(R.id.IdeaFragment) as IdeaFragment
        fragment.setId(intent.getIntExtra(IDEATION_ID,1),intent.getIntExtra(PROJECT_ID,1))
    }
}
