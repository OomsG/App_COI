package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.MenuItem
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeationViewPagerAdapter
import be.kdg.cityofideas.adapters.IdeationsRecyclerAdapter.IdeationsSelectionListener
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val IDEATION_ID :String = "IdeationId"
const val SURVEY_ID:String = "SurveyId"
const val IDEATION_TYPE :String = "IdeationType"

class IdeationActivity : BaseActivity(), IdeationsSelectionListener {
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: IdeationViewPagerAdapter
    private lateinit var tabLayout: TabLayout

    override fun onShareSelected(id: Int) {
        val shareIntent = Intent(this,ShareActivity::class.java)
        shareIntent.putExtra(IDEATION_ID,id)
        startActivity(shareIntent)
    }

    override fun onSurveySelected(surveyId: Int, projectId: Int) {
        val surveyIntent = Intent(this,SurveyActivity::class.java)
        surveyIntent.putExtra(SURVEY_ID,surveyId)
        surveyIntent.putExtra(PROJECT_ID,projectId)
        startActivity(surveyIntent)
    }

    override fun onIdeationSelected(ideationid: Int, projectId:Int, ideationType: Boolean) {
        val ideationIntent = Intent(this,IdeaActivity::class.java)
        ideationIntent.putExtra(IDEATION_ID,ideationid)
        ideationIntent.putExtra(PROJECT_ID,projectId)
        ideationIntent.putExtra(IDEATION_TYPE,ideationType)
        startActivity(ideationIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ideation)
        initialiseViews(this, intent.getIntExtra(PROJECT_ID,1))

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
    private fun initialiseViews(context: Context, id:Int) {
        tabLayout = findViewById(R.id.IdeationsTab)
        viewPager = findViewById(R.id.IdeationsPager)
        pagerAdapter = IdeationViewPagerAdapter(supportFragmentManager, id)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        RestClient(context)
            .getPhases("phases/$id")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                (pagerAdapter).phases = it
            }
    }
}
