package call.ofbeer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import call.ofbeer.R
import call.ofbeer.api.LogoutResponse
import call.ofbeer.api.RegisterResponse
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    btn_logout.setOnClickListener{

        session = SessionManager(applicationContext)

        RetrofitClient.instance.logout(session.TOKEN)
            .enqueue(object: Callback<LogoutResponse> {
                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                    if (response.code() == 200){
                        Toast.makeText(applicationContext, "You were logout", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else
                        Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_LONG).show()

                }

            })


    }
}
}