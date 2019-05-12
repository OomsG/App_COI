package be.kdg.cityofideas.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.surveys.Question
import be.kdg.cityofideas.model.surveys.QuestionType
import kotlinx.android.synthetic.*

class SurveyFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_survey, container, false)
        return view
    }
    fun inialiseView(view: View) {
    }
}
