package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.widget.TextView
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter.ideaSelectionListener
import be.kdg.cityofideas.fragments.IdeaFragment
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IdeaActivity : AppCompatActivity(), ideaSelectionListener {


    private lateinit var toolbar: Toolbar
    private lateinit var Title: TextView

    override fun onIdeaSelected(idea: Ideas) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
