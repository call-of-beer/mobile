package call.ofbeer.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import call.ofbeer.R
import call.ofbeer.api.SessionManager

class SplashScreen : AppCompatActivity() {

    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        session = SessionManager(applicationContext)

        Handler().postDelayed({

            if(SessionManager.getInstance(applicationContext).isLoggedIn()){
                val email = SessionManager.getInstance(applicationContext).EMAIL
                if(email=="admin") {
                    startActivity(Intent(this, AdminActivity::class.java))
                    finish()
                }
                if(session.isLoggedIn()) {
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }}
            else
            {startActivity(Intent(this,WelcomeActivity::class.java))
                finish()}
        }, 3000)


    }
}