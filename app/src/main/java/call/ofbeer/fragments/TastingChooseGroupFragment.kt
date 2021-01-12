package call.ofbeer.fragments

import android.R.attr.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import call.ofbeer.R
import call.ofbeer.api.GroupResponse
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.createGroup
import kotlinx.android.synthetic.main.fragment_tasting_choose_group.*
import retrofit2.Call
import retrofit2.Response


class TastingChooseGroupFragment : Fragment() {

    lateinit var session: SessionManager
    private var inputGroup: Spinner? = null
    private var _idsGroup: ArrayList<Int> = ArrayList<Int>()
    private var groupPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasting_choose_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())
        val fragmentTransaction = fragmentManager?.beginTransaction()

        session.getfragmentRedirect(1)

        inputGroup = getView()?.findViewById(R.id.choose_group) as Spinner
        fetchGroup()
        inputGroup?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val id = _idsGroup.get(position)
                session.getIdOfGroup(id)
                groupPosition = parent?.getItemIdAtPosition(position)!!.toInt()

            }
        }

        createGroup.setOnClickListener{
            fragmentTransaction?.replace(R.id.fragmentTasting, AddGroupFragment())
            ?.addToBackStack(null)
            ?.commit()
        }

        chooseBeers.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentTasting, TastingChooseBeersFragment())
            ?.addToBackStack(null)
            ?.commit()
        }

    }

    private fun fetchGroup(){

        session = SessionManager(requireContext())

        RetrofitClient.instance.getGroupWhereModerator(session.TOKEN)
            .enqueue(object : retrofit2.Callback<GroupResponse>{
                override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {

                    if (response.code() == 200) {

                        if(response.body()!=null){

                            val responseList = response.body()?.result!!
                            val item = arrayOfNulls<String>(responseList!!.size)
                            for(i in responseList.indices)
                            {
                                item[i] = responseList[i].name
                                var id = responseList[i].id
                                _idsGroup.add(id)
                            }
                            val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                            inputGroup?.adapter = arrayAdapter
                        }



                    }

                }

            }
            )
    }

}