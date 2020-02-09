package fr.isen.soda.androidtoolbox


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    companion object{
        private const val GOOD_ID = "admin"
        private const val GOOD_MDP = "123"
        private val USER_ID_KEY = "id"
        private val USER_PASS_KEY = "mdp"
        const val USER_PREF = "user_pref"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        val savedIdentifiant = sharedPreferences.getString(USER_ID_KEY,"")
        val savedMdp = sharedPreferences.getString(USER_PASS_KEY, "")
        //teste de log
        if (savedIdentifiant == GOOD_ID && savedMdp == GOOD_MDP) {
            home(savedIdentifiant, true)
        }
        button.setOnClickListener{
            /*val identifiant = yourId.text.toString()
            val message = "tu as clique $identifiant"
            Toast.makeText(this , message ,Toast.LENGTH_LONG).show()*/

            login()
        }
    }

    private fun login() {
        val name = yourId.text.toString().trim()
        val mdp = yourmdp.text.toString().trim()
        if (yourId.getText().toString().equals(GOOD_ID) && yourmdp.getText().toString().equals(GOOD_MDP)) {
            val message = (R.string.identifié)
            Toast.makeText(this , message ,Toast.LENGTH_LONG).show()
            saveUser(name,mdp)
            startActivity(Intent(this, HomeActivity::class.java))
            home(name,true)
        }
        else {
            val message = (R.string.nidentifié)
            Toast.makeText(this , message ,Toast.LENGTH_LONG).show()
        }
    }
    private fun saveUser(tonid: String ,tonmdp: String){
        val editor = sharedPreferences.edit()
        editor.putString(USER_ID_KEY, tonid)
        editor.putString(USER_PASS_KEY,tonmdp)
        editor.apply()
    }
    private fun home (tonid: String,clear: Boolean){
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        intent.putExtra((R.string.strIdentifiant).toString(),tonid)
        if (clear) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        if (clear){
            finish()
        }
    }


}

