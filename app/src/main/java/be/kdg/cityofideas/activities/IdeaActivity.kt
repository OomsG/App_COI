package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter
import be.kdg.cityofideas.fragments.IdeaFragment
import be.kdg.cityofideas.model.ideations.Ideas

class IdeaActivity : AppCompatActivity(), IdeaRecyclerAdapter.ideaSelectionListener {


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
    fun initialiseViews(context:Context) {
        toolbar = findViewById(R.id.IdeaToolbar)
        val fragment = supportFragmentManager.findFragmentById(R.id.IdeaFragment) as IdeaFragment
        /*RestClient(context)
            .getIdeation("ideation/"+intent.getIntExtra(IDEATION_ID, 1))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (fragment).ideation = it
            })
            */
    }
}
