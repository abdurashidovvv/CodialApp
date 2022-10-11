package com.abdurashidov.codial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abdurashidov.codial.R
import com.abdurashidov.codial.databinding.FragmentMentorAddBinding
import com.abdurashidov.codial.db.MyDbHelper
import com.abdurashidov.codial.models.Course
import com.abdurashidov.codial.models.Mentor
import com.abdurashidov.codial.models.MyMentorObject

class MentorAddFragment : Fragment() {

    private lateinit var binding:FragmentMentorAddBinding
    private lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentMentorAddBinding.inflate(layoutInflater)
        val index=arguments?.getSerializable("course") as Course
        myDbHelper= MyDbHelper(binding.root.context)

        binding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        binding.btnSave.setOnClickListener {
            if (binding.name.text!!.isNotEmpty() && binding.surname.text!!.isNotEmpty() && binding.phone.text!!.isNotEmpty()){
                val mentor=Mentor(
                    name = binding.name.text.toString(),
                    surname = binding.surname.text.toString(),
                    phone = binding.phone.text.toString(),
                    course = MyMentorObject.courseId
                )
                myDbHelper.addMentor(mentor)
                Toast.makeText(binding.root.context, "Saqlandi", Toast.LENGTH_SHORT).show()
                fragmentManager?.popBackStack()
            }else{
                Toast.makeText(binding.root.context, "Iltimos hamma maydonlarni toldiring!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

}