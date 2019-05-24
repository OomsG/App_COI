package be.kdg.cityofideas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import be.kdg.cityofideas.R
import com.google.zxing.integration.android.IntentIntegrator

class QrActivity : BaseActivity() {
    private lateinit var qrScanIntegrator: IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        // Comment for testing
        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator.setOrientationLocked(false)
        qrScanIntegrator.initiateScan()

        // Uncomment for testing
//        testNavigationToIdea()
//        testNavigationToSurvey()
    }

    //region Tests
    private fun testNavigationToSurvey() {
        val iotCode = "7373048b-ab08-4056-be3e-93760a1a12f7"

        val c = manager.getDetails(
            helper.getIoTEntry().TBL_IOT,
            null,
            "${helper.getIoTEntry().IOT_CODE} = ?",
            arrayOf(iotCode),
            null, null, null
        )

        var ideaId = 0
        var questionId = 0

        if (c.moveToFirst()) {
            ideaId = c.getInt(c.getColumnIndex(helper.getIdeaEntry().IDEA_ID))
            questionId = c.getInt(c.getColumnIndex(helper.getQuestionEntry().QUESTION_ID))
        }

        Log.d("ideaId", ideaId.toString())
        Log.d("questionId", questionId.toString())

        if (ideaId != 0) {
            navigateToIdea(ideaId)
        } else if (questionId != 0) {
            navigateToSurvey(questionId)
        }
    }

    private fun testNavigationToIdea() {
        val iotCode = "ec93a023-c407-4150-9038-3cc90cbe9eaa"

        val c = manager.getDetails(
            helper.getIoTEntry().TBL_IOT,
            null,
            "${helper.getIoTEntry().IOT_CODE} = ?",
            arrayOf(iotCode),
            null, null, null
        )

        var ideaId = 0
        var questionId = 0

        if (c.moveToFirst()) {
            ideaId = c.getInt(c.getColumnIndex(helper.getIdeaEntry().IDEA_ID))
            questionId = c.getInt(c.getColumnIndex(helper.getQuestionEntry().QUESTION_ID))
        }

        Log.d("ideaId", ideaId.toString())
        Log.d("questionId", questionId.toString())

        if (ideaId != 0) {
            navigateToIdea(ideaId)
        } else if (questionId != 0) {
            navigateToSurvey(questionId)
        }
    }
    //endregion

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data. Comment for testing
            if (result.contents == null) {
                Toast.makeText(this, "Geen resultaat gevonden", Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                val content = result.contents
                val iotCode = content.substringAfterLast('=')

                Log.d("iotCode", iotCode)

                val c = manager.getDetails(
                    helper.getIoTEntry().TBL_IOT,
                    null,
                    "${helper.getIoTEntry().IOT_CODE} = ?",
                    arrayOf(iotCode),
                    null, null, null
                )

                var ideaId = 0
                var questionId = 0

                if (c.moveToFirst()) {
                    ideaId = c.getInt(c.getColumnIndex(helper.getIdeaEntry().IDEA_ID))
                    questionId = c.getInt(c.getColumnIndex(helper.getQuestionEntry().QUESTION_ID))
                }

                Log.d("ideaId", ideaId.toString())
                Log.d("questionId", questionId.toString())

                var success = false
                if (ideaId != 0) {
                    success = navigateToIdea(ideaId)
                } else if (questionId != 0) {
                    success = navigateToSurvey(questionId)
                }

                Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()

                if (success) {
                    finish()
                } else {
                    Toast.makeText(this, "Oeps, er ging iets mis. Probeer opnieuw...", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun navigateToIdea(ideaId: Int): Boolean {
        val projectId: Int
        val phaseId: Int
        val ideationId: Int

        val cIdea = manager.getDetails(
            helper.getIdeaEntry().TBL_IDEA,
            null,
            "${helper.getIdeaEntry().IDEA_ID} = ?",
            arrayOf(ideaId.toString()),
            null, null, null
        )

        if (cIdea.moveToFirst()) {
            ideationId = cIdea.getInt(cIdea.getColumnIndex(helper.getIdeationEntry().IDEATION_ID))

            val cIdeation = manager.getDetails(
                helper.getIdeationEntry().TBL_IDEATION,
                null,
                "${helper.getIdeationEntry().IDEATION_ID} = ?",
                arrayOf(ideationId.toString()),
                null, null, null
            )

            if (cIdeation.moveToFirst()) {
                phaseId = cIdeation.getInt(cIdeation.getColumnIndex(helper.getPhaseEntry().PHASE_ID))

                val cPhase = manager.getDetails(
                    helper.getPhaseEntry().TBL_PHASE,
                    null,
                    "${helper.getPhaseEntry().PHASE_ID} = ?",
                    arrayOf(phaseId.toString()),
                    null, null, null
                )

                if (cPhase.moveToFirst()) {
                    projectId = cPhase.getInt(cPhase.getColumnIndex(helper.getProjectEntry().PROJECT_ID))

                    if (projectId != 0) {
                        val intent = Intent(this, IdeaActivity::class.java)
                        intent.putExtra(IDEATION_ID, ideationId)
                        intent.putExtra(PROJECT_ID, projectId)
                        startActivity(intent)

                        return true
                    }
                }
            }
        }

        return false
    }

    private fun navigateToSurvey(questionId: Int): Boolean {
        val surveyId: Int

        val cQuestion = manager.getDetails(
            helper.getQuestionEntry().TBL_QUESTION,
            null,
            "${helper.getQuestionEntry().QUESTION_ID} = ?",
            arrayOf(questionId.toString()),
            null, null, null
        )

        if (cQuestion.moveToFirst()) {
            surveyId = cQuestion.getInt(cQuestion.getColumnIndex(helper.getSurveyEntry().SURVEY_ID))

            if (surveyId != 0) {
                val intent = Intent(this, SurveyActivity::class.java)
                intent.putExtra(SURVEY_ID, surveyId)
                startActivity(intent)

                return true
            }
        }

        return false
    }
}
