package fr.isen.soda.androidtoolbox

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cycle_vie.*

class CycleVieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cycle_vie)

        notification("onCreate", true)
    }

    private fun notification(message: String, isActive: Boolean) {
        if(isActive)
            cycleVieText.text = message
        else
            Log.d("TAG", message)

    }

    override fun onStart() {
        super.onStart()
        notification("onStart",  true)
    }

    override fun onResume() {
        super.onResume()
        notification("onResume",  true)
    }

    override fun onPause() {
        super.onPause()
        notification("onPause", false)

    }

    override fun onStop() {
        super.onStop()
        notification("onStop", false)
    }

    override fun onDestroy() {
        super.onDestroy()
        notification("onDestroy", false)
        Toast.makeText(this, "onDestroy",Toast.LENGTH_LONG).show()
    }


}
