package call.ofbeer.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.models.User
import kotlinx.android.synthetic.main.fragment_account.*
import retrofit2.Call
import retrofit2.Response

class AccountFragment : Fragment() {

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())


        user_name.text = "Witaj " + session.userName

        deleteAcc.setOnClickListener {
            //see delete alert and confirm or dismiss
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage("Are you sure you wan to delete your account?")
                .setCancelable(false)
                .setPositiveButton("Delete") { dialog, id ->
                    RetrofitClient.instance.deleteUser(session.TOKEN, session.userID)
                        .enqueue(object : retrofit2.Callback<User> {
                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }

                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if (response.code() == 200) {
                                    Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT)
                                        .show()
                                    dialog.dismiss()
                                    session.Logout()
                                } else Toast.makeText(
                                    context,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        })

                }
                .setNegativeButton("Dismiss") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = dialogBuilder.create()
            alert.show()

            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)


        }
    }

}