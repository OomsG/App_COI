package be.kdg.cityofideas.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import be.kdg.cityofideas.fragments.ProjectFragment
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.ProjectsAdapter
import be.kdg.cityofideas.adapters.ProjectsAdapter.ProjectsSelectionListener
import be.kdg.cityofideas.adapters.ViewPagerAdapter
import be.kdg.cityofideas.model.projects.getTestProjects

const val PROJECT_POSITION: String = "PROJECT_POSITION"

class ProjectsActivity : AppCompatActivity(), ProjectsSelectionListener {

    private lateinit var image: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var tabLayout: TabLayout

    override fun onProjectsSelected(positionProject: Int) {
        val intent = Intent(this, IdeationActivity::class.java)
        intent.putExtra(PROJECT_POSITION, positionProject)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)
        initialiseViews()
    }

    fun initialiseViews() {
        tabLayout = findViewById(R.id.tab )
        toolbar = findViewById(R.id.include)
        viewPager = findViewById(R.id.pager)
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        //supportFragmentManager.findFragmentById(R.id.ProjectFragment) as ProjectFragment
        //image = findViewById(R.id.AntwerpenId)
        //image.setImageResource(R.drawable.antwerpen)
    }


}
