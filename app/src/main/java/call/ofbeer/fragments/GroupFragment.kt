package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


class GroupFragment : Fragment() {

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

        session = SessionManager(requireContext())
        val fragmentTransaction = fragmentManager?.beginTransaction()

        session.getfragmentRedirect(0)

        groupsList.layoutManager = LinearLayoutManager(requireContext())

        fetchGroups()

        addGroup.setOnClickListener{
            fragmentTransaction?.replace(R.id.nav_host_fragment, AddGroupFragment())
            ?.addToBackStack(null)
            ?.commit()

        }

        groupToManage.setOnClickListener{
            fragmentTransaction?.replace(R.id.nav_host_fragment, GroupToManageFragment())
            ?.addToBackStack(null)
            ?.commit()
        }



    }

    private fun fetchGroups(){
        session = SessionManager(requireContext())

        RetrofitClient.instance.getGroups(session.TOKEN)
            .enqueue(object : retrofit2.Callback<GroupResponse>{
                override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {

                    if (response.code() == 200) {


                        if (activity == null || !isAdded) return //when "fragment  not attached to a context."

                        group = response.body()?.result!!
                        groupAdapter = GroupAdapter(requireContext(), group)

                        groupsList.adapter = groupAdapter
                        groupAdapter.notifyDataSetChanged()


                    }

                }

            }
            )


    }



}

