package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeationViewPagerAdapter
import be.kdg.cityofideas.adapters.IdeationsRecyclerAdapter
import be.kdg.cityofideas.adapters.ProjectsViewPagerAdapter
import be.kdg.cityofideas.fragments.IdeationFragment
import be.kdg.cityofideas.listener.SelectionListener
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class IdeationActivity : AppCompatActivity(), SelectionListener {

    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: IdeationViewPagerAdapter
    private lateinit var tabLayout: TabLayout

    override fun onSelected(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("help", "Hello form IdeationActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ideation)
        initaliseViews(this)

    }

    @SuppressLint("CheckResult")
    fun initaliseViews(context: Context) {
        tabLayout = findViewById(R.id.IdeationsTab)
        toolbar = findViewById(R.id.IdeationsInclude)
        viewPager = findViewById(R.id.IdeationsPager)
        pagerAdapter = IdeationViewPagerAdapter(supportFragmentManager, intent.getIntExtra(PROJECT_ID, 1))
        viewPager.adapter = pagerAdapter

        Log.d("help",intent.getIntExtra(PROJECT_ID,3).toString())
        tabLayout.setupWithViewPager(viewPager)
        RestClient(context)
            .getPhases("phases/" + (intent.getIntExtra(PROJECT_ID, 1)))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (pagerAdapter).phases = it
            })

    }
}
