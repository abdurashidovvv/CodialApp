package com.abdurashidov.codial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.abdurashidov.codial.R
import com.abdurashidov.codial.adapters.MentorAdapter
import com.abdurashidov.codial.databinding.FragmentMentorsListBinding
import com.abdurashidov.codial.db.MyDbHelper
import com.abdurashidov.codial.models.Course
import com.abdurashidov.codial.models.Mentor
import com.abdurashidov.codial.models.MyMentorObject


class MentorsListFragment : Fragment(), MentorAdapter.MentorItemEvent {

    private lateinit var binding:FragmentMentorsListBinding
    private lateinit var list:ArrayList<Mentor>
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var mentorAdapter: MentorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentMentorsListBinding.inflate(layoutInflater)
        binding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        val index=arguments?.getSerializable("key") as Course
        binding.info.text=index.name

        myDbHelper= MyDbHelper(binding.root.context)
        list= ArrayList()
        for (i in myDbHelper.getAllMentors()){
            if (i.course == MyMentorObject.courseId) list.add(i)
        }
        mentorAdapter= MentorAdapter(list, this)
        binding.myRv.adapter=mentorAdapter

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.mentorAddFragment, bundleOf("course" to index))
        }

        return binding.root
    }

    override fun editClick(mentor: Mentor, position: Int) {

    }

    override fun trashClick(mentor: Mentor, position: Int) {

    }


}