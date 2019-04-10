package be.kdg.cityofideas.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import be.kdg.cityofideas.fragments.ProjectFragment
import be.kdg.cityofideas.R

class ProjectsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)
        Log.d("help", "Hello van ProjectActivity")
        initialiseViews()
    }
    fun initialiseViews(){
        supportFragmentManager.findFragmentById(R.id.ProjectFragment) as ProjectFragment
    }
}
