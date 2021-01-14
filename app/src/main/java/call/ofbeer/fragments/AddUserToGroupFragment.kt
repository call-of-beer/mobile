package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.SuccesfulResponse
import call.ofbeer.requests.AddUserToGroupRequest
import kotlinx.android.synthetic.main.fragment_add_user.*
import retrofit2.Call
import retrofit2.Response

class AddUserToGroupFragment : Fragment() {

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())
        val fragmentTransaction = fragmentManager?.beginTransaction()

        btn_add.setOnClickListener {
            val email = input_email.text.toString().trim()

            if (email.isEmpty()) {
                input_email.error = "Name of group required"
                input_email.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.addUserToGroup(
                AddUserToGroupRequest(email),
                session.goupID,
                session.TOKEN
            )
                .enqueue(object : retrofit2.Callback<SuccesfulResponse> {
                    override fun onFailure(call: Call<SuccesfulResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<SuccesfulResponse>,
                        response: Response<SuccesfulResponse>
                    ) {
                        Toast.makeText(context, "Użytkownik dodany do grupy", Toast.LENGTH_SHORT)
                            .show()
                        //fragmentManager?.popBackStackImmediate()


                        if (session.fragmentRedirect == 0) {
                            fragmentTransaction?.replace(R.id.nav_host_fragment,ManageGroupFragment())
                                ?.addToBackStack(null)
                                ?.commit()
                        }
                        if (session.fragmentRedirect == 1) {
                            fragmentTransaction?.replace(R.id.fragmentTasting,ManageGroupFragment())
                                ?.addToBackStack(null)
                                ?.commit()

                        }

                    }

                })


        }
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    if (session.fragmentRedirect == 0) {
                        fragmentTransaction?.replace(R.id.nav_host_fragment,ManageGroupFragment())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                    if (session.fragmentRedirect == 1) {
                        fragmentTransaction?.replace(R.id.fragmentTasting,ManageGroupFragment())
                            ?.addToBackStack(null)
                            ?.commit()

                    }
                }
            })

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    if (session.fragmentRedirect == 0) {
                        fragmentTransaction?.replace(R.id.nav_host_fragment,ManageGroupFragment())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                    if (session.fragmentRedirect == 1) {
                        fragmentTransaction?.replace(R.id.fragmentTasting,ManageGroupFragment())
                            ?.addToBackStack(null)
                            ?.commit()

                    }
                }
            })
    }

}