package com.abdurashidov.codial.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdurashidov.codial.R
import com.abdurashidov.codial.databinding.FragmentStudentsBinding
import com.abdurashidov.codial.db.MyDbHelper
import com.abdurashidov.codial.models.Group


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class StudentsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding:FragmentStudentsBinding
    private lateinit var myDbHelper: MyDbHelper
    private val TAG = "StudentsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentStudentsBinding.inflate(layoutInflater)
        myDbHelper= MyDbHelper(binding.root.context)
        val group=arguments?.getSerializable("index") as Group
        Log.d(TAG, "onCreateView: $group")
        binding.startLessons.setOnClickListener {
            myDbHelper.getAllGroups().forEach {
                if (it==group){
                    it.open=true
                    myDbHelper.editGroupOpen(group)
                    Log.d(TAG, "onCreateView: ${myDbHelper.getAllGroups()}")
                    fragmentManager?.popBackStack()
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}