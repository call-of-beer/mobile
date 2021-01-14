package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.api.GroupAddResponse
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.requests.GroupRequest
import kotlinx.android.synthetic.main.fragment_add_group.*
import retrofit2.Call
import retrofit2.Response

class AddGroupFragment : Fragment() {

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Stwórz nową grupę"

        session = SessionManager(requireContext())
        val fragmentTransaction = fragmentManager?.beginTransaction()

        btn_create.setOnClickListener {
            val name = input_name.text.toString().trim()

            if (name.isEmpty()) {
                input_name.error = "Name of group required"
                input_name.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.newGroup(GroupRequest(name), session.TOKEN)
                .enqueue(object : retrofit2.Callback<GroupAddResponse> {
                    override fun onFailure(call: Call<GroupAddResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<GroupAddResponse>, response: Response<GroupAddResponse>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(
                                requireContext(),
                                "Grupa została utworzona!",
                                Toast.LENGTH_SHORT
                            ).show()
                            session.getIdOfGroup(response.body()?.result!!.id)
                            session.getNameOfGroup(response.body()?.result!!.name)

                            if (session.fragmentRedirect == 0) {
                                fragmentTransaction?.replace(
                                    R.id.nav_host_fragment,
                                    ManageGroupFragment()
                                )
                                    ?.addToBackStack(null)
                                    ?.commit()
                            }
                            if (session.fragmentRedirect == 1) {
                                fragmentTransaction?.replace(
                                    R.id.fragmentTasting,
                                    ManageGroupFragment()
                                )
                                    ?.addToBackStack(null)
                                    ?.commit()

                            }

                        }
                    }

                })

        }
    }


}
