package be.kdg.cityofideas.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.ProjectsRecyclerAdapter.projectsSelectionListener
import be.kdg.cityofideas.adapters.ProjectsViewPagerAdapter
import be.kdg.cityofideas.model.projects.Projects

const val PROJECT_ID: String = "projectid"

class ProjectsActivity : AppCompatActivity(), projectsSelectionListener {

    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: ProjectsViewPagerAdapter
    private lateinit var tabLayout: TabLayout

    override fun onProjectsSelected(id:Int) {
        val intent = Intent(this, IdeationActivity::class.java)
        intent.putExtra(PROJECT_ID, id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)
        initialiseViews()
    }

    fun initialiseViews() {
        tabLayout = findViewById(R.id.ProjectsTab)
        toolbar = findViewById(R.id.ProjectsInclude)
        viewPager = findViewById(R.id.ProjectsPager)
        pagerAdapter = ProjectsViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
