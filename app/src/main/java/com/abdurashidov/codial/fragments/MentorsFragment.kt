package com.abdurashidov.codial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.abdurashidov.codial.R
import com.abdurashidov.codial.adapters.CourseAdapter
import com.abdurashidov.codial.databinding.FragmentMentorsBinding
import com.abdurashidov.codial.db.MyDbHelper
import com.abdurashidov.codial.models.Course
import com.abdurashidov.codial.models.MyMentorObject


class MentorsFragment : Fragment(), CourseAdapter.RvAction {
    private lateinit var binding: FragmentMentorsBinding
    private lateinit var list:ArrayList<Course>
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var courseAdapter: CourseAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentMentorsBinding.inflate(layoutInflater)
        binding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        myDbHelper= MyDbHelper(binding.root.context)
        list= ArrayList()
        list.addAll(myDbHelper.getAllCourses())
        courseAdapter= CourseAdapter(list, binding.root.context, this)
        binding.myRv.adapter=courseAdapter
        return binding.root
    }

    override fun itemClick(course: Course, position: Int) {
        MyMentorObject.courseId=position
        findNavController().navigate(R.id.mentorsListFragment, bundleOf("key" to course))
    }
}