package call.ofbeer.fragments

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
import call.ofbeer.adapters.GroupAdapter
import call.ofbeer.api.GroupResponse
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.models.Group
import kotlinx.android.synthetic.main.fragment_groups.*
import retrofit2.Call
import retrofit2.Response


class GroupToManageFragment : Fragment() {

    lateinit var session: SessionManager
    private var group = listOf<Group>()
    private lateinit var groupAdapter: GroupAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Zarządzaj swoimi grupami"

        addGroup.visibility = View.GONE
        groupToManage.visibility = View.GONE


        groupsList.layoutManager = LinearLayoutManager(requireContext())

        fetchGroups()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.nav_host_fragment, GroupFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                }
            })




    }

    private fun fetchGroups(){
        session = SessionManager(requireContext())

        RetrofitClient.instance.getGroupWhereModerator(session.TOKEN)
            .enqueue(object : retrofit2.Callback<GroupResponse>{
                override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {

                    if (response.code() == 200) {


                        group = response.body()?.result!!
                        groupAdapter = GroupAdapter(requireContext(), group)

                        groupsList.adapter = groupAdapter
                        groupAdapter.notifyDataSetChanged()

                        groupAdapter.setVisibility()

                    }

                }

            }
            )


    }

    }