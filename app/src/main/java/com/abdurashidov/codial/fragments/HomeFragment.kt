package com.abdurashidov.codial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.abdurashidov.codial.R
import com.abdurashidov.codial.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentHomeBinding.inflate(layoutInflater)

        binding.apply {

            course.setOnClickListener {
                findNavController().navigate(R.id.coursesFragment)
            }

            mentor.setOnClickListener {
                findNavController().navigate(R.id.mentorsFragment)
            }

            group.setOnClickListener {
                findNavController().navigate(R.id.groupsFragment)
            }
        }

        return binding.root
    }
}