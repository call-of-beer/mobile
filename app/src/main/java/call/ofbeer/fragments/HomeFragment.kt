package call.ofbeer.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.activities.TastingActivity
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.UserResponse
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var session: SessionManager
    private var isFirstBackPressed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Start"

        val fragmentTransaction = fragmentManager?.beginTransaction()

        session = SessionManager(requireContext())
        session.getfragmentRedirect(0)

        view.findViewById<View>(R.id.beersCollection).setOnClickListener {
            fragmentTransaction!!.replace(R.id.nav_host_fragment, BeerFragment())
                .addToBackStack(null).commit()
        }

        view.findViewById<View>(R.id.createTesting).setOnClickListener {
            val i = Intent(requireContext(), TastingActivity::class.java)
            requireContext().startActivity(i)
        }

        view.findViewById<View>(R.id.createGroup).setOnClickListener {
            fragmentTransaction!!.replace(R.id.nav_host_fragment, AddGroupFragment())
                .addToBackStack(null).commit()
        }


        //2 click back button
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isFirstBackPressed) {

                        activity?.finish()
                    } else {
                        isFirstBackPressed = true
                        Toast.makeText(
                            requireContext(),
                            "Wciśnij ponownie by wyjść",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler().postDelayed({
                            isFirstBackPressed = false

                        }, 3000)
                    }
                }

            })

    }

    override fun onStart() {

        session = SessionManager(requireContext())

        if (System.currentTimeMillis() >= session.ExpiredDate) {

            session.Logout()
        }
        super.onStart()

        val TOKEN = SessionManager.getInstance(requireContext()).TOKEN

        RetrofitClient.instance.getAccount(TOKEN)
            .enqueue(object : retrofit2.Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {

                    if (response.body()?.result?.error != null || response.code() == 403 || response.body() == null) {
                        session.Logout()
                        Toast.makeText(activity, "Zaloguj się by móc korzystać z aplikacji.", Toast.LENGTH_LONG).show()
                    } else {
                        session.getDetailOfUser(response.body()?.result?.email!!)
                        session.getUserEmail(response.body()?.result?.email!!)
                        session.getIdUser(response.body()?.result?.id!!)
                        session.getUserSurname(response.body()?.result?.surname!!)
                        session.getUserName(response.body()?.result?.firstname!!)

                    }
                }
            })
    }
}