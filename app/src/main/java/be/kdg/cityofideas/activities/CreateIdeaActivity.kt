package be.kdg.cityofideas.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.YOUTUBE_API
import be.kdg.cityofideas.login.loggedInUser
import be.kdg.cityofideas.model.ideations.Ideation
import be.kdg.cityofideas.rest.RestClient
import com.google.android.gms.maps.MapView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreateIdeaActivity : BaseActivity(), YouTubePlayer.OnInitializedListener {

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PICK_PERMISSION_CODE = 1001
        private const val IMAGE_TAKE_CODE = 1002
        private const val TAKE_PERMISSION_CODE = 1003
    }

    private lateinit var titleText: EditText
    private lateinit var layout: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var submit: Button
    private lateinit var url: String
    private val itemList: ArrayList<String> = arrayListOf()
    private var image_rui: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_idea)
        Log.d("ideationId->create",intent.getIntExtra(IDEATION_ID, 1).toString())
        Log.d("projectId->create",intent.getIntExtra(PROJECT_ID, 1).toString())
        getIdeation()
        //createLayout()
    }

    private fun initialiseViews(ideation: Ideation) {
        fillList(ideation)
        layout = findViewById(R.id.LinearLayoutCreateIdea)
        getRequiredFields(layout, ideation)
        titleText = findViewById(R.id.editIdeaTitle)
        spinner = findViewById(R.id.PossibleFields)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, itemList)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("spinnerItem", itemList[p2])
                when (itemList[p2]) {
                    "Omschrijving" -> createTextField(layout)
                    "Foto" -> createImageField(layout)
                    "Youtube Link" -> createVideoField(layout)
                    "Kaart" -> createMapField(layout)
                }
            }
        }
        submit = findViewById(R.id.createIdea)
        submit.setOnClickListener {
            if (validateData(layout, ideation, titleText.text.toString())) {
                finish()
            }else{
                Toast.makeText(this,"er is geen idee opgeslagen",Toast.LENGTH_LONG).show()
            }

        }
    }


    private fun validateData(layout: LinearLayout, ideation: Ideation, title: String): Boolean {
        val parameters = mutableMapOf<String, Array<String>>()
        for (i in 1..layout.childCount) {
            when (val param = layout.getChildAt(i - 1)) {
                is EditText -> {
                    if (!param.text.isNullOrBlank()) {
                        parameters["text"] = arrayOf(param.text.toString())
                    }
                }
                is ImageView -> {
                    parameters["image"] = arrayOf(param.contentDescription.toString())

                }
                is com.google.android.youtube.player.YouTubePlayerView -> {
                    parameters["video"] = arrayOf(url)
                }
                is MapView -> {
                }
            }
        }
        if (!title.isBlank()) {
            parameters["title"] = arrayOf(title)
        }
        if (!parameters.isNullOrEmpty()) {
            saveObjects(parameters, ideation)
            return true
        } else {
            return false
        }
    }

    fun saveObjects(parameters: MutableMap<String, Array<String>>, ideation: Ideation) {
        Thread {
            RestClient(this).createIdea(parameters, ideation.IdeationId, loggedInUser!!.UserId)
        }.start()
    }

    private fun fillList(ideation: Ideation) {
        itemList.add("Kies een element om toe te voegen")
        if (ideation.TextAllowed!!) {
            itemList.add("Omschrijving")
        }
        if (ideation.ImageAllowed!!) {
            itemList.add("Foto")
        }
        if (ideation.VideoAllowed!!) {
            itemList.add("Youtube Link")
        }
        if (ideation.MapAllowed!!) {
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
                    val singleIdeation =
                        Ideation(
                            it.IdeationId,
                            it.CentralQuestion,
                            it.Phase,
                            it.InputIdeation,
                            it.Reactions,
                            it.Ideas,
                            it.TextAllowed,
                            it.ImageAllowed,
                            it.VideoAllowed,
                            it.MapAllowed,
                            it.TextRequired,
                            it.ImageRequired,
                            it.VideoRequired,
                            it.MapRequired
                        )
                    Log.d("ideation->create",singleIdeation.toString())

                    initialiseViews(singleIdeation)
                }
            }
    }

    //region CreateFiels

    fun createTextField(layout: LinearLayout) {
        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.hint = "Schrijf hier meer informatie over jouw idee!"
        editText.setSingleLine(false)
        layout.addView(editText)
    }

    fun createImageField(layout: LinearLayout) {

        val button = Button(this)
        button.id = View.generateViewId()
        button.text = getString(R.string.upload)
        layout.addView(button)
        button.setOnClickListener {
            pickImageFromGallery()
            layout.removeView(button)
        }
        val pictureButton = Button(this)
        pictureButton.id = View.generateViewId()
        pictureButton.text = getString(R.string.Take_Picture)
        layout.addView(pictureButton)
        pictureButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions =
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, TAKE_PERMISSION_CODE)
                } else {
                    takePicture()
                }
                takePicture()
                layout.removeView(button)
                layout.removeView(pictureButton)
            }
        }
    }


    private fun createImage(layout: LinearLayout, data: Uri?) {
        val image = ImageView(this)
        image.id = View.generateViewId()
        image.contentDescription = data.toString()
        image.setImageURI(data)
        layout.addView(image)
    }

    fun createVideoField(layout: LinearLayout) {
        val editText = EditText(this)
        editText.id = View.generateViewId()
        editText.hint = "plaats hier een link naar een youtube video"
        layout.addView(editText)
        val button = Button(this)
        button.id = View.generateViewId()
        button.text = getString(R.string.upload_video)
        layout.addView(button)

        button.setOnClickListener {
            if (editText.text.isNotBlank()) {
                url = editText.text.toString()

                val fragmentTransaction = supportFragmentManager.beginTransaction()

                val fragment = YouTubePlayerSupportFragment()
                fragmentTransaction.add(R.id.LinearLayoutCreateIdea, fragment).commit()
                fragment.initialize(YOUTUBE_API, this)
                layout.removeView(button)
                layout.removeView(editText)
            } else {
                Toast.makeText(this, "Geen video gevonden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun createMapField(layout: LinearLayout) {
        val map = MapView(this)
        map.id = View.generateViewId()
        layout.addView(map)
    }

    //endregion

    //region video initialise
    private fun getUrl(): String {
        Log.d("url", url)
        val array = url.split('/')
        val final = array.last().split('=')
        return final.last()
    }


    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        if (!wasRestored) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            player.loadVideo(getUrl())
            player.play()
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, error: YouTubeInitializationResult) {
        // YouTube error
        val errorMessage = error.toString()
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        Log.d("errorMessage:", errorMessage)
    }
    //endregion

    //region picture initialise
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun takePicture() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "new Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
        image_rui = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui)
        startActivityForResult(cameraIntent, IMAGE_TAKE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PICK_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
            TAKE_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val layout = findViewById<LinearLayout>(R.id.LinearLayoutCreateIdea)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            createImage(layout, data?.data)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_TAKE_CODE) {
            createImage(layout, image_rui)
        }
    }

    //endregion
}
