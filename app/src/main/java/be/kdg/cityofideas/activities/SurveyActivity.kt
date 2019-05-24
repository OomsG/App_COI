package be.kdg.cityofideas.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.fragments.QuestionsFragment

class SurveyActivity : BaseActivity() {
    private lateinit var submit: Button
    private lateinit var fragment: QuestionsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        initialiseViews()
        addEventHandlers()
    }

    private fun initialiseViews() {
        submit = findViewById(R.id.SurveySubmit)

        fragment = supportFragmentManager.findFragmentById(R.id.SurveyFragment) as QuestionsFragment
        fragment.setSurveyId(intent.getIntExtra(SURVEY_ID,-1))
    }

    private fun addEventHandlers() {
        submit.setOnClickListener {
            if (fragment.validateAnswers()) {
                finish()
            } else {
                Toast.makeText(this, "Niet alle vragen werden correct beantwoord", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
