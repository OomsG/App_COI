package be.kdg.cityofideas.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.ProjectsRecyclerAdapter.ProjectsSelectionListener
import be.kdg.cityofideas.adapters.ProjectsViewPagerAdapter

const val PROJECT_ID: String = "projectId"

class ProjectsActivity : BaseActivity(), ProjectsSelectionListener {


    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: ProjectsViewPagerAdapter
    private lateinit var tabLayout: TabLayout

    override fun onProjectsSelected(id:Int) {
        val intent = Intent(this, IdeationActivity::class.java)
        intent.putExtra(PROJECT_ID, id)
        startActivity(intent)
    }
    override fun onShareSelected(id: Int) {
        val shareIntent = Intent(this, ShareActivity::class.java)
        shareIntent.putExtra(PROJECT_ID, id)
        startActivity(shareIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)
        initialiseViews()
    }

    override fun onResume() {
        invalidateOptionsMenu()
        super.onResume()
    }

    private fun initialiseViews() {
        tabLayout = findViewById(R.id.ProjectsTab)
        viewPager = findViewById(R.id.ProjectsPager)
        pagerAdapter = ProjectsViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
