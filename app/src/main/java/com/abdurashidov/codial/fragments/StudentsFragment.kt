package com.abdurashidov.codial.fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.abdurashidov.codial.R
import com.abdurashidov.codial.adapters.StudentAdapter
import com.abdurashidov.codial.databinding.FragmentStudentsBinding
import com.abdurashidov.codial.databinding.MentorEditDialogBinding
import com.abdurashidov.codial.databinding.StudentEditDialogBinding
import com.abdurashidov.codial.db.MyDbHelper
import com.abdurashidov.codial.models.Group
import com.abdurashidov.codial.models.Student


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class StudentsFragment : Fragment(), StudentAdapter.StudentItemEvent {
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
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var list:ArrayList<Student>
    private val TAG = "StudentsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentStudentsBinding.inflate(layoutInflater)
        myDbHelper= MyDbHelper(binding.root.context)
        val group=arguments?.getSerializable("index") as Group


        list=ArrayList()
        myDbHelper.getAllStudents().forEach {
            if (it.group?.id==group.id){
                list.add(it)
            }
        }
        studentAdapter=StudentAdapter(list, this)
        Log.d(TAG, "onCreateView: $list")
        binding.myRv.adapter=studentAdapter

        binding.result.text="${group.name} \nO'quvchilar soni: ${list.size}"

        binding.add.setOnClickListener {
            val dialog=AlertDialog.Builder(binding.root.context).create()
            val mentorEditDialogBinding=MentorEditDialogBinding.inflate(layoutInflater)
            mentorEditDialogBinding.name.hint="Familiyasi"
            mentorEditDialogBinding.about.hint="Ismi"
            mentorEditDialogBinding.about2.visibility=View.VISIBLE
            mentorEditDialogBinding.about2.hint="Phone"
            mentorEditDialogBinding.about2.setInputType(InputType.TYPE_CLASS_PHONE)
            dialog.setView(mentorEditDialogBinding.root)
            dialog.show()

            mentorEditDialogBinding.save.setOnClickListener {
                if (mentorEditDialogBinding.about.text.toString().isNotEmpty() && mentorEditDialogBinding.name.text.toString().isNotEmpty() && mentorEditDialogBinding.about2.text.toString().isNotEmpty()){
                    val student=Student(
                        name = mentorEditDialogBinding.about.text.toString(),
                        surname = mentorEditDialogBinding.name.text.toString(),
                        phone = mentorEditDialogBinding.about2.text.toString(),
                        day = group.daysOfWeek,
                        group = group
                    )
                    myDbHelper.addStudent(student)
                    list.add(student)
                    studentAdapter.notifyItemInserted(list.size-1)
                    dialog.dismiss()
                    Toast.makeText(binding.root.context, "Saqlandi", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(binding.root.context, "Iltimos hamma maydonlarni toldiring!", Toast.LENGTH_SHORT).show()
                }
            }
            mentorEditDialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
        }
        binding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        binding.info.text=group.name

        binding.startLesson.setOnClickListener {
            group.open=true
            myDbHelper.editGroup(group)
            Log.d(TAG, "onCreateView: ${group.open}")
            Toast.makeText(binding.root.context, "Guruhda dars boshlandi!", Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack()
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

    override fun editClick(student: Student, position: Int) {
        val dialog=AlertDialog.Builder(binding.root.context).create()
        val studentEditDialogBinding=StudentEditDialogBinding.inflate(layoutInflater)
        studentEditDialogBinding.surname.setText(student.surname)
        studentEditDialogBinding.name.setText(student.name)
        studentEditDialogBinding.phone.setText(student.phone)
        dialog.setView(studentEditDialogBinding.root)
        dialog.show()

        studentEditDialogBinding.cancel.setOnClickListener {
            dialog.cancel()
        }
        studentEditDialogBinding.save.setOnClickListener {
            if (studentEditDialogBinding.name.text.toString().isNotEmpty() && studentEditDialogBinding.surname.text.toString().isNotEmpty() && studentEditDialogBinding.phone.text.toString().isNotEmpty()){
                student.surname=studentEditDialogBinding.surname.text.toString()
                student.name=studentEditDialogBinding.name.text.toString()
                student.phone=studentEditDialogBinding.phone.text.toString()
                myDbHelper.editStudent(student)
                studentAdapter.notifyDataSetChanged()
                dialog.cancel()
            }
        }
    }

    override fun trashClick(student: Student, position: Int) {
        myDbHelper.deleteStudent(student)
        list.remove(student)
        Toast.makeText(binding.root.context, "O'chirildi", Toast.LENGTH_SHORT).show()
        studentAdapter.notifyDataSetChanged()
    }
}