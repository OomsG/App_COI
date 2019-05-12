package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import be.kdg.cityofideas.R
import be.kdg.cityofideas.fragments.SurveyFragment
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SurveyActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var fragment: SurveyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        initialiseViews()
    }

    fun initialiseViews() {
        toolbar = findViewById(R.id.SurveyInclude)
        val fragment = supportFragmentManager.findFragmentById(R.id.SurveyFragment) as SurveyFragment
      //  getQuestions()
    }

   /* @SuppressLint("CheckResult")
    fun getQuestions() {
        RestClient(this)
            .getQuestions("questions/" + intent.getIntExtra(IDEATION_ID, 0))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                (fragment).questions = it
            }
    }*/
}
