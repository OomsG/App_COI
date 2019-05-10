package be.kdg.cityofideas.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.surveys.Question
import kotlinx.android.synthetic.*

class SurveyFragment : Fragment() {
    private lateinit var layout: LinearLayout

    var questions: Array<Question> = arrayOf()
        set(question) {
            field = question
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_survey, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inialiseView(view)
    }

    fun inialiseView(view: View) {
        layout = view.findViewById(R.id.LinearLayoutIdea)
        getAllQuestions()
    }

    fun getAllQuestions() {

    }

}
