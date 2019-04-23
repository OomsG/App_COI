package be.kdg.cityofideas.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.ProjectsRecyclerAdapter.ProjectsSelectionListener
import be.kdg.cityofideas.adapters.ProjectsViewPagerAdapter

const val PROJECT_ID: String = "PROJECT_ID"

class ProjectsActivity : AppCompatActivity(), ProjectsSelectionListener {

    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: ProjectsViewPagerAdapter
    private lateinit var tabLayout: TabLayout

    override fun onProjectsSelected(projectID: Int) {
        val intent = Intent(this, IdeationActivity::class.java)
        intent.putExtra(PROJECT_ID, projectID)
        Log.d("help",projectID.toString())
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
        pagerAdapter = ProjectsViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }


}
