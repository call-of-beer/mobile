package call.ofbeer.fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import call.ofbeer.R
import call.ofbeer.activities.MainActivity
import call.ofbeer.activities.TastingActivity
import call.ofbeer.adapters.UserInGroupAdapter
import call.ofbeer.api.GroupInfoResponse
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.SuccesfulResponse
import call.ofbeer.models.User
import kotlinx.android.synthetic.main.fragment_group_details.*
import kotlinx.android.synthetic.main.fragment_groups.*
import kotlinx.android.synthetic.main.fragment_manage_group.*
import retrofit2.Call
import retrofit2.Response

class ManageGroupFragment : Fragment() {

    lateinit var session: SessionManager
    private var user = listOf<User>()
    private lateinit var userAdapter: UserInGroupAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_manage_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())
        val fragmentTransaction = fragmentManager?.beginTransaction()


        (activity as? AppCompatActivity)?.supportActionBar?.title = session.groupName

        usersList.layoutManager = LinearLayoutManager(requireContext())

        fetchUsers()

        if(session.fragmentRedirect==1)
        {
            tastingHistory.visibility = View.GONE
            startTastingNow.visibility = View.VISIBLE
        }

        addUser.setOnClickListener{

            if(session.fragmentRedirect==0) {
                fragmentTransaction?.replace(R.id.nav_host_fragment, AddUserToGroupFragment())
                ?.addToBackStack(null)
                ?.commit()
            }
            if(session.fragmentRedirect==1){
                fragmentTransaction?.replace(R.id.fragmentTasting, AddUserToGroupFragment())
                ?.addToBackStack(null)
                ?.commit()
            }
        }

        tastingHistory.setOnClickListener{
            fragmentTransaction?.replace(R.id.nav_host_fragment, HistoryOfTastingFragment())
            ?.addToBackStack(null)
            ?.commit()
        }

        startTastingNow.setOnClickListener{
            val intent = Intent(activity, TastingActivity::class.java)
            startActivity(intent)
        }

        deleteGroup.setOnClickListener{
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage("Jesteś pewny, że chcesz usunąć grupę " + session.groupName +"?")
                .setCancelable(false)
                .setPositiveButton("Usuń") { dialog, id ->
                    RetrofitClient.instance.deleteGroup(session.TOKEN, session.goupID)
                        .enqueue(object : retrofit2.Callback<SuccesfulResponse> {
                            override fun onFailure(call: Call<SuccesfulResponse>, t: Throwable) {
                                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(
                                call: Call<SuccesfulResponse>,
                                response: Response<SuccesfulResponse>
                            ) {
                                Toast.makeText(
                                    context,
                                    "Grupa została usunięta",
                                    Toast.LENGTH_SHORT
                                ).show()
                                fragmentTransaction?.replace(R.id.nav_host_fragment,GroupFragment())
                                ?.addToBackStack(null)
                                ?.commit()
                            }

                        })
                }
                .setNegativeButton("Anuluj"){dialog, id->
                    dialog.dismiss()
                }
            val alert = dialogBuilder.create()
            alert.show()

            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)
        }

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    if(session.fragmentRedirect==0) {
                        fragmentTransaction?.replace(R.id.nav_host_fragment, GroupToManageFragment())
                        ?.addToBackStack(null)
                        ?.commit()
                    }
                    if(session.fragmentRedirect==1){
                        fragmentTransaction?.replace(R.id.fragmentTasting, TastingChooseGroupFragment())
                        ?.addToBackStack(null)
                        ?.commit()

                    }
                }
                })



    }

    private fun fetchUsers(){
        session = SessionManager(requireContext())
        RetrofitClient.instance.getGroupInfo(session.TOKEN, session.goupID)
            .enqueue(object : retrofit2.Callback<GroupInfoResponse>{
                override fun onFailure(call: Call<GroupInfoResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<GroupInfoResponse>, response: Response<GroupInfoResponse>
                ) {
                    if (response.code() == 200) {
                        user = response.body()?.result!!
                        userAdapter = UserInGroupAdapter(requireContext(), user)

                        usersList.adapter = userAdapter
                        userAdapter.notifyDataSetChanged()

                        userAdapter.setVisibility()


                    }
                }

            })
    }
}