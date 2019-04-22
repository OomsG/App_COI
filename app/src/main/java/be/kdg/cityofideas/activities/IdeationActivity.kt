package be.kdg.cityofideas.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeationsAdapter
import be.kdg.cityofideas.fragments.IdeationFragment

class IdeationActivity : AppCompatActivity(), IdeationsAdapter.IdeationsSelectionListener {
    override fun onIdeationsSelected(positionIdeation: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ideation)
        initaliseViews()
    }
    fun initaliseViews(){
        supportFragmentManager.findFragmentById(R.id.ideationFragment) as IdeationFragment
        Log.d("help","activity")
    }

}
