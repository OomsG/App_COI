package be.kdg.cityofideas.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.QuestionRecyclerAdapter
import be.kdg.cityofideas.model.surveys.Question
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuestionsFragment : Fragment() {

    private lateinit var view1: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_questions, container, false)
        view1 = view
        return view
    }


    @SuppressLint("CheckResult")
    fun initialiseViews(view: View, surveyId: Int) {

        val rvSurvey = view.findViewById<RecyclerView>(R.id.rvQuestions)
        rvSurvey.layoutManager = LinearLayoutManager(context)
        rvSurvey.adapter = QuestionRecyclerAdapter(context)
        RestClient(this.context)
            .getQuestions("questions/" + surveyId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                (rvSurvey.adapter as QuestionRecyclerAdapter).questions =
                    it.sortedBy { question: Question -> question.QuestionNr }.toTypedArray()
            }
    }

    fun setQuestionId(id: Int) {
        initialiseViews(view1, id)
    }
}

