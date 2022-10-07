package com.abdurashidov.codial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdurashidov.codial.R
import com.abdurashidov.codial.databinding.FragmentAboutCourseBinding
import com.abdurashidov.codial.db.MyDbHelper
import com.abdurashidov.codial.models.Course


class AboutCourseFragment : Fragment() {

    private lateinit var binding: FragmentAboutCourseBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list:ArrayList<Course>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentAboutCourseBinding.inflate(layoutInflater)
        val index=arguments?.getSerializable("key") as Course
        myDbHelper= MyDbHelper(binding.root.context)
        list=ArrayList()
        list.addAll(myDbHelper.getAllCourses())
        binding.info.text=index.name
        binding.about.text=index.info

        binding.add.visibility=View.GONE
        binding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        return binding.root
    }

}