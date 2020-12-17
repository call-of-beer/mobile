package call.ofbeer.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import call.ofbeer.R
import call.ofbeer.activities.LoginActivity
import call.ofbeer.activities.TestingActivity
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.UserResponse
import call.ofbeer.viewmodels.HomeViewModel
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var session: SessionManager
    private lateinit var homeViewModel: HomeViewModel
    private var isFirstBackPressed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        var root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.appName)
        homeViewModel.textAppName.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
            return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Start"

        view.findViewById<View>(R.id.beersCollection).setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction!!.replace(R.id.nav_host_fragment, BeerFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.findViewById<View>(R.id.createTesting).setOnClickListener{
            val i = Intent(requireContext(), TestingActivity::class.java)
            requireContext().startActivity(i)
        }

        view.findViewById<View>(R.id.createGroup).setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction!!.replace(R.id.nav_host_fragment, AddGroupFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.findViewById<View>(R.id.seeStatistics).setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction!!.replace(R.id.nav_host_fragment, StatisticsFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        //2 click back button
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(isFirstBackPressed){

                        activity?.finish()
                    } else {
                        isFirstBackPressed = true
                        Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({
                            isFirstBackPressed = false

                        }, 3000)
                    }
                }

            })

    }

    override fun onStart() {

        session = SessionManager(requireContext())

        if(System.currentTimeMillis()>=session.ExpiredDate){

            session.Logout()
        }
        super.onStart()

        val TOKEN = SessionManager.getInstance(requireContext()).TOKEN

        RetrofitClient.instance.getAccount(TOKEN)
            .enqueue(object : retrofit2.Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {

                    if (response.body()?.error != null) {
                        session.Logout()
                        val i = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(i)
                        Toast.makeText(activity,"Your session expired. Please login to continue.", Toast.LENGTH_LONG).show()
                    } else {
                        session.getDetailOfUser(response.body()?.email!!)
                        val email = session.EMAIL//response.body()?.email.toString()

                        session.getUserEmail(response.body()?.email!!)
                        session.getIdUser(response.body()?.id!!)
                        session.getUserSurname(response.body()?.surname!!)
                        session.getUserName(response.body()?.firstname!!)

                    }
                }
            })
    }
}