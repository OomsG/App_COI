package be.kdg.cityofideas.activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import be.kdg.cityofideas.R

class CreateIdeaActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_idea)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels
        window.setLayout((width * .85).toInt(), (height * .85).toInt())
        window.attributes.gravity = Gravity.CENTER

    }
}
