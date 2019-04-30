package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeationViewPagerAdapter
import be.kdg.cityofideas.adapters.IdeationsRecyclerAdapter.IdeationsSelectionListener
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val IDEATION_ID :String = "IdeationId"


class IdeationActivity : AppCompatActivity(),IdeationsSelectionListener{
    override fun onIdeationSelected(ideationid: Int, projectId:Int ) {
        val intent = Intent(this,IdeaActivity::class.java)
        intent.putExtra(IDEATION_ID,ideationid)
        intent.putExtra(PROJECT_ID,projectId)
        startActivity(intent)
    }

    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: IdeationViewPagerAdapter
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ideation)
        initaliseViews(this, intent.getIntExtra(PROJECT_ID,1))
    }

    @SuppressLint("CheckResult")
    fun initaliseViews(context: Context , id:Int) {
        tabLayout = findViewById(R.id.IdeationsTab)
        toolbar = findViewById(R.id.IdeationsInclude)
        viewPager = findViewById(R.id.IdeationsPager)
        pagerAdapter = IdeationViewPagerAdapter(supportFragmentManager, id)
        viewPager.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPager)
        RestClient(context)
            .getPhases("phases/" + id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (pagerAdapter).phases = it
            })

    }
}
