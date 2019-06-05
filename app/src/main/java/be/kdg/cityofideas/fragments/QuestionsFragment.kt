package be.kdg.cityofideas.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.QuestionRecyclerAdapter
import be.kdg.cityofideas.model.surveys.Question
import be.kdg.cityofideas.model.surveys.QuestionType
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuestionsFragment : Fragment() {
    private lateinit var mView: View
    private var surveyId: Int = -1
    private lateinit var rvSurvey: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_questions, container, false)
        mView = view
        return view
    }

    @SuppressLint("CheckResult")
    private fun initialiseViews(view: View) {
        rvSurvey = view.findViewById(R.id.rvQuestions)
        rvSurvey.layoutManager = LinearLayoutManager(context)
        rvSurvey.adapter = QuestionRecyclerAdapter(context)

        RestClient(this.context)
            .getQuestions("questions/$surveyId")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                (rvSurvey.adapter as QuestionRecyclerAdapter).questions =
                    it.sortedBy { question: Question -> question.QuestionNr }.toTypedArray()
            }
    }

    fun setSurveyId(id: Int) {
        surveyId = id
        initialiseViews(mView)
    }

    @SuppressLint("NewApi")
    fun validateAnswers(): Boolean {
        val questions = (rvSurvey.adapter as QuestionRecyclerAdapter).questions
        val answers = mutableMapOf<Int, Array<String>>()

        for (i in 1..questions.size) {
            val viewGroup = (rvSurvey.getChildAt(i - 1))

            if (viewGroup != null) {
                val layout = (viewGroup as ViewGroup).getChildAt(2) as? LinearLayout

                if (layout != null) {
                    for (j in 1..layout.childCount) {
                        when (val answer = layout.getChildAt(j - 1)) {
                            is EditText -> {
                                if (questions[i - 1].QuestionType == QuestionType.EMAIL) {
                                    if (Patterns.EMAIL_ADDRESS.matcher(answer.text.toString()).matches())
                                        answers[questions[i - 1].QuestionNr] = arrayOf(answer.text.toString())
                                } else if (!answer.text.isNullOrBlank()) {
                                    answers[questions[i - 1].QuestionNr] = arrayOf(answer.text.toString())
                                }

                                answer.text.clear()
                            }

                            is RadioGroup -> {
                                if (answer.checkedRadioButtonId != -1) {
                                    answers[questions[i - 1].QuestionNr] =
                                        arrayOf(mView.findViewById<RadioButton>(answer.checkedRadioButtonId).text.toString())
                                }

                                answer.clearCheck()
                            }

                            is CheckBox -> {
                                if (answer.isChecked) {
                                    if (answers[questions[i - 1].QuestionNr] != null)
                                        answers[questions[i - 1].QuestionNr] =
                                            answers[questions[i - 1].QuestionNr]!!.plus(arrayOf(answer.text.toString()))
                                    else
                                        answers[questions[i - 1].QuestionNr] = arrayOf(answer.text.toString())
                                }

                                answer.isChecked = false
                            }

                            is Spinner -> answers[questions[i - 1].QuestionNr] = arrayOf(answer.selectedItem.toString())

                            else -> return false
                        }
                    }
                }
            }
        }

        Log.d("questions size", questions.size.toString())
        Log.d("answers size", answers.size.toString())

        answers.forEach {
            it.value.forEach {
                Log.d("answer", it)
            }
        }

        return if (answers.size == questions.size) {
            saveAnswers(answers)
            true
        } else false
    }

    private fun saveAnswers(answers: MutableMap<Int, Array<String>>) {
        Thread {
            RestClient(context).postAnswers("questions/answers/save", surveyId, answers)
        }.start()
    }
}

