package com.abdurashidov.codial.fragments

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
import com.abdurashidov.codial.adapters.CourseAdapter
import com.abdurashidov.codial.databinding.CourseDialogItemBinding
import com.abdurashidov.codial.databinding.FragmentCoursesBinding
import com.abdurashidov.codial.db.MyDbHelper
import com.abdurashidov.codial.models.Course


class CoursesFragment : Fragment(), CourseAdapter.RvAction {

    private lateinit var binding: FragmentCoursesBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list:ArrayList<Course>
    private lateinit var courseAdapter: CourseAdapter
    private val TAG = "CoursesFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentCoursesBinding.inflate(layoutInflater)

        binding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        myDbHelper= MyDbHelper(binding.root.context)
        list= ArrayList()
        list.addAll(myDbHelper.getAllCourses())
        courseAdapter= CourseAdapter(list, binding.root.context, this)
        binding.myRv.adapter=courseAdapter

        binding.add.setOnClickListener {
            val dialog=AlertDialog.Builder(binding.root.context).create()
            val courseDialogItemBinding=CourseDialogItemBinding.inflate(layoutInflater)
            dialog.setView(courseDialogItemBinding.root)
            dialog.show()

            courseDialogItemBinding.cancel.setOnClickListener {
                dialog.cancel()
            }

            courseDialogItemBinding.save.setOnClickListener {
                if (courseDialogItemBinding.name.text.isNotEmpty() && courseDialogItemBinding.about.text.isNotEmpty()){
                    val course=Course(
                        name = courseDialogItemBinding.name.text.toString(),
                        info = courseDialogItemBinding.about.text.toString()
                    )
                    myDbHelper.addCourse(course)
                    list.add(course)
                    courseAdapter.notifyItemInserted(list.size-1)
                    Toast.makeText(binding.root.context, "Saqlandi", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                }else{
                    Toast.makeText(binding.root.context, "Iltimos hamma maydonlarni toldiring!", Toast.LENGTH_SHORT).show()
                }
            }

        }
        return binding.root
    }


    override fun itemClick(course: Course, position: Int) {
        findNavController().navigate(R.id.aboutCourseFragment, bundleOf("key" to course))
    }
}