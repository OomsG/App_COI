package be.kdg.cityofideas.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import be.kdg.cityofideas.R
import be.kdg.cityofideas.fragments.ReactionFragment
import be.kdg.cityofideas.model.ideations.Ideas

class ReactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseViews()
        setContentView(R.layout.activity_reaction)
    }
    fun initialiseViews(){
        val fragment = supportFragmentManager.findFragmentById(R.id.ReactionFragment) as ReactionFragment

        fragment.setIdea((intent.getSerializableExtra(IDEA) as Ideas))
    }
}
