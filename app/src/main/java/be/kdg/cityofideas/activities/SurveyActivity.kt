package be.kdg.cityofideas.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import be.kdg.cityofideas.R
import be.kdg.cityofideas.fragments.SurveyFragment

class SurveyActivity : BaseActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        initialiseViews()
    }

    fun initialiseViews() {
        toolbar = findViewById(R.id.SurveyInclude)
        val fragment = supportFragmentManager.findFragmentById(R.id.SurveyFragment) as SurveyFragment
    }
}
