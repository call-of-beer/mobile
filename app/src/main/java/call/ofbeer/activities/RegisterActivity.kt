package call.ofbeer.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import call.ofbeer.R
import call.ofbeer.api.RegisterResponse
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.Validation
import call.ofbeer.requests.RegisterRequest
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {

            val email = input_email.text.toString().trim()
            val password = input_password.text.toString().trim()
            val passwordConfirmation = input_pass_conf.text.toString().trim()
            val firstname = input_firstname.text.toString().trim()
            val surname = input_surname.text.toString().trim()

            if (email.isEmpty()) {
                input_email.error = "Email required"
                input_email.requestFocus()
                return@setOnClickListener
            }


            if (!Validation.isValidEmail(email)) {
                input_email.error = "Enter correct email"
                input_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                input_password.error = "Password required"
                input_password.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 5) {
                input_password.error = "Password has to be at least 5 char long"
                input_password.requestFocus()
                return@setOnClickListener
            }


            if (passwordConfirmation.isEmpty()) {
                input_pass_conf.error = "Password confirmation required"
                input_pass_conf.requestFocus()
                return@setOnClickListener
            }

            if (passwordConfirmation != password) {
                input_pass_conf.error = "Passwords fields are not equals"
                input_pass_conf.requestFocus()
                return@setOnClickListener
            }

            if (firstname.isEmpty()) {
                input_firstname.error = "Name required"
                input_firstname.requestFocus()
                return@setOnClickListener
            }

            if (surname.isEmpty()) {
                input_surname.error = "Surname required"
                input_surname.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.register( RegisterRequest(email, password, passwordConfirmation, firstname, surname))
                .enqueue(object: Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.code() == 200){
                            Toast.makeText(applicationContext, "Register successful. Now you can login.", Toast.LENGTH_LONG).show()

                            val i = Intent(applicationContext, LoginActivity::class.java)
                            applicationContext.startActivity(i)
                            }
                        else
                            Toast.makeText(applicationContext, "Registration failed. Please, make sure you put in a correct credential and you already have not account with this email.", Toast.LENGTH_LONG).show()

                    }

                })




        }

        btn_signIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


}