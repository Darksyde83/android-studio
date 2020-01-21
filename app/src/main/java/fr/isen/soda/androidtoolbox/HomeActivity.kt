package fr.isen.soda.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btc.setOnClickListener{
            cycle()
        }
    }

    private fun cycle() {
        val message = "Cycle de vie"
        Toast.makeText(this , message , Toast.LENGTH_LONG).show()
        startActivity(Intent(this, CycleVieActivity::class.java))
    }
}
