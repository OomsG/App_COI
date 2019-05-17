package be.kdg.cityofideas.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import be.kdg.cityofideas.R
import be.kdg.cityofideas.fragments.QuestionsFragment

class SurveyActivity : BaseActivity() {
    private lateinit var submit:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        initialiseViews()
    }

    fun initialiseViews() {
        submit = findViewById(R.id.SurveySubmit)
        submit.setOnClickListener {
            if (!validateform()) {
                Toast.makeText(this, "Niet alle vragen werden ingevuld", Toast.LENGTH_SHORT).show()
            }
        }
        val fragment = supportFragmentManager.findFragmentById(R.id.SurveyFragment) as QuestionsFragment
        fragment.setQuestionId(intent.getIntExtra(SURVEY_ID,1))
    }


    fun validateform(): Boolean {
        return false
    }

}
