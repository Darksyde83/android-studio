package fr.isen.soda.androidtoolbox

import android.content.Context
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
        bts.setOnClickListener {
            formulaire()
        }
        deco.setOnClickListener{
            deconnection()
        }
        btp.setOnClickListener{
            permition()
        }
        btw.setOnClickListener {
            website()
        }
    }

    private fun cycle() {
        val message = (R.string.cdv)
        Toast.makeText(this , message , Toast.LENGTH_LONG).show()
        startActivity(Intent(this, CycleVieActivity::class.java))
    }
    private fun deconnection() {
        val sharedPreferences = getSharedPreferences(LoginActivity.USER_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent (this , LoginActivity::class.java)
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
    private fun formulaire() {
        val message = (R.string.form)
        Toast.makeText(this , message , Toast.LENGTH_LONG).show()
        startActivity(Intent(this, FormulaireActivity::class.java))
    }
    private fun permition() {
        val message = (R.string.perm)
        Toast.makeText(this , message , Toast.LENGTH_LONG).show()
        startActivity(Intent(this, AffichageActivity::class.java))
    }
    private fun website() {
        val message = (R.string.web)
        Toast.makeText(this , message , Toast.LENGTH_LONG).show()
        startActivity(Intent(this, WebSiteActivity::class.java))
    }
}
