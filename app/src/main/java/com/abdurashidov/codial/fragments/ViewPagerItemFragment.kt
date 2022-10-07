package com.abdurashidov.codial.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdurashidov.codial.adapters.GroupAdapter
import com.abdurashidov.codial.databinding.FragmentViewPagerItemBinding
import com.abdurashidov.codial.db.MyDbHelper
import com.abdurashidov.codial.models.Group
import com.abdurashidov.codial.models.MyMentorObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewPagerItemFragment : Fragment(), GroupAdapter.GroupRvEvent {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding:FragmentViewPagerItemBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var groupAdapter:GroupAdapter
    private lateinit var list:ArrayList<Group>
    private val TAG = "ViewPagerItemFragment"

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding= FragmentViewPagerItemBinding.inflate(layoutInflater)

        myDbHelper= MyDbHelper(binding.root.context)
        list= ArrayList()


        if (MyMentorObject.onRegisterCallBack==0){
            myDbHelper.getAllGroups().forEach {
                if (it.open==false && it.course!!.id==MyMentorObject.courseId){
                    list.add(it)
                }
            }
            groupAdapter= GroupAdapter(list, this)
            binding.myRv.adapter=groupAdapter
            groupAdapter.notifyDataSetChanged()
        }
        if (MyMentorObject.onRegisterCallBack==1){
            myDbHelper.getAllGroups().forEach {
                if (it.open==true && it.course!!.id==MyMentorObject.courseId){
                    list.add(it)
                }
            }
            Log.d(TAG, "onCreateView: $list")
            groupAdapter=GroupAdapter(list, this)
            binding.myRv.adapter=groupAdapter
            groupAdapter.notifyDataSetChanged()
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewPagerItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun viewClick(group: Group, position: Int) {

    }

    override fun editClick(group: Group, position: Int) {

    }

    override fun trashClick(group: Group, position: Int) {

    }
}