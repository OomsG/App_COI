package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.telephony.SmsMessage
import android.text.InputType
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter
import be.kdg.cityofideas.adapters.YOUTUBE_API
import be.kdg.cityofideas.model.ideations.Ideation
import be.kdg.cityofideas.rest.RestClient
import com.google.android.gms.maps.MapView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreateIdeaActivity : BaseActivity() {
    private lateinit var titleText: EditText
    private lateinit var layout: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var submit : Button
    val itemList: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_idea)
        getIdeation()
        //createLayout()
    }

    private fun initialiseViews(ideation: Ideation) {
        fillList(ideation)
        titleText = findViewById(R.id.editIdeaTitle)
        spinner = findViewById(R.id.PossibleFields)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, itemList)
        spinner.adapter = adapter

        layout = findViewById(R.id.LinearLayoutCreateIdea)
        getRequiredFields(layout, ideation)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("spinnerItem",itemList[p2])
                when (itemList[p2]) {
                    "Omschrijving" ->createTextField(layout)
                    "Foto" -> createImageField(layout)
                    "Youtube Link" -> createVideoField(layout)
                    "Kaart" -> createMapField(layout)
                }
            }
        }

        submit = findViewById(R.id.createIdea)
        submit.setOnClickListener {
            requestData(layout)

        }

    }

    private fun requestData(layout:LinearLayout) {
        for ( child in 0..layout.childCount ){
            val v = layout.getChildAt(child)
            if (v is EditText) {
                v.text
            }
        }
    }

    private fun fillList(ideation: Ideation) {
        itemList.add("Kies een element om toe te voegen")
        if (ideation.Text!!) {
            itemList.add("Omschrijving")
        }
        if (ideation.Image!!) {
            itemList.add("Foto")
        }
        if (ideation.Video!!) {
            itemList.add("Youtube Link")
        }
        if (ideation.Map!!) {
            itemList.add("Kaart")
        }
    }

    private fun getRequiredFields(layout: LinearLayout, ideation: Ideation) {
        if (ideation.TextRequired!!) {
            createTextField(layout)
        } else if (ideation.ImageRequired!!) {
            createImageField(layout)
        } else if (ideation.VideoRequired!!) {
            createVideoField(layout)
        } else if (ideation.MapRequired!!) {
            createMapField(layout)
        }
    }

    private fun createLayout() {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels
        window.setLayout((width * .85).toInt(), (height * .85).toInt())
        window.attributes.gravity = Gravity.CENTER
    }

    @SuppressLint("CheckResult")
    private fun getIdeation() {
        RestClient(this)
            .getIdeations("ideations/" + intent.getIntExtra(PROJECT_ID, 0))
            .map {
                it.filter {
                    it.IdeationId.equals(intent.getIntExtra(IDEATION_ID, 0))
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.forEach {
                    val SingleIdeation =
                        Ideation(
                            it.IdeationId,
                            it.CentralQuestion,
                            it.Phase,
                            it.InputIdeation,
                            it.Reactions,
                            it.Ideas,
                            it.Text,
                            it.Image,
                            it.Video,
                            it.Map,
                            it.TextRequired,
                            it.ImageRequired,
                            it.VideoRequired,
                            it.MapRequired
                        )
                    initialiseViews(SingleIdeation)
                }
            }
    }


    fun createTextField(layout: LinearLayout) {

        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.hint = "Schrijf hier meer informatie over jouw idee!"
        editText.setSingleLine(false)
        editText.inputType = InputType.TYPE_CLASS_TEXT  + InputType.TYPE_TEXT_FLAG_MULTI_LINE

        layout.addView(editText)
    }

    fun createImageField(layout: LinearLayout) {
        val image = ImageView(this)
        image.id = View.generateViewId()
        layout.addView(image)

    }

    fun createVideoField(layout: LinearLayout) {

        val editText = EditText(this)
        editText.id = View.generateViewId()
        layout.addView(editText)

    }

    fun createMapField(layout: LinearLayout) {
        val map = MapView(this)
        map.id = View.generateViewId()
        layout.addView(map)
    }



}
