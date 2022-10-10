package com.abdurashidov.codial.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.abdurashidov.codial.R
import com.abdurashidov.codial.adapters.GroupAdapter
import com.abdurashidov.codial.adapters.SpinnerAdapter
import com.abdurashidov.codial.databinding.FragmentViewPagerItemBinding
import com.abdurashidov.codial.databinding.GroupEditDialogBinding
import com.abdurashidov.codial.databinding.MentorEditDialogBinding
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
    private lateinit var spinnerAdapter: SpinnerAdapter
    private val TAG = "ViewPagerItemFragment"

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding= FragmentViewPagerItemBinding.inflate(layoutInflater)

        myDbHelper= MyDbHelper(binding.root.context)
        list= ArrayList()


        if (param1.toString()=="Ochilgan guruhlar"){
            myDbHelper.getAllGroups().forEach {
                if (it.open==true && it.course!!.id==MyMentorObject.courseId){
                    list.add(it)
                    Log.d(TAG, "onCreateView: ${it.open}")
                }
            }
            Log.d(TAG, "onCreateView: $list")
            groupAdapter= GroupAdapter(list, this)
            binding.myRv.adapter=groupAdapter
            groupAdapter.notifyDataSetChanged()
        }
        if (param1.toString()=="Ochilayotgan guruhlar"){
            myDbHelper.getAllGroups().forEach {
                if (it.open==false && it.course!!.id==MyMentorObject.courseId){
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
        findNavController().navigate(R.id.studentsFragment, bundleOf("index" to group))
    }

    override fun editClick(group: Group, position: Int) {
        val dialog=AlertDialog.Builder(binding.root.context).create()
        val groupEditDialogBinding=GroupEditDialogBinding.inflate(layoutInflater)
        groupEditDialogBinding.name.setText(group.name)

        val mentorName=ArrayList<String>()
        myDbHelper.getAllMentors().forEach {
            mentorName.add(it.name.toString())
        }
        groupEditDialogBinding.spinnerMentor.adapter=SpinnerAdapter(mentorName)

        val listTime=ArrayList<String>()
        listTime.add("16:00-18:00")
        listTime.add("18:00-20:00")
        groupEditDialogBinding.spinnerTime.adapter=SpinnerAdapter(listTime)

        val listDays=ArrayList<String>()
        listDays.add("Dushanba/Chorshanba/Juma")
        listDays.add("Seshanba/Payshanba/Shanba")
        groupEditDialogBinding.spinnerDays.adapter=SpinnerAdapter(listDays)
        dialog.setView(groupEditDialogBinding.root)
        dialog.show()

        groupEditDialogBinding.cancel.setOnClickListener {
            dialog.cancel()
        }

        groupEditDialogBinding.save.setOnClickListener {
            group.name=groupEditDialogBinding.name.text.toString()
            group.mentor=myDbHelper.getAllMentors()[groupEditDialogBinding.spinnerMentor.selectedItemPosition]
            group.time=listTime[groupEditDialogBinding.spinnerTime.selectedItemPosition]
            group.daysOfWeek=listDays[groupEditDialogBinding.spinnerDays.selectedItemPosition]
            myDbHelper.editGroup(group)
            groupAdapter.notifyDataSetChanged()
            Toast.makeText(binding.root.context, "Edit", Toast.LENGTH_SHORT).show()
            dialog.cancel()
        }

    }

    override fun trashClick(group: Group, position: Int) {
        myDbHelper.deleteGroup(group)
        list.remove(group)
        groupAdapter.notifyDataSetChanged()
    }
}