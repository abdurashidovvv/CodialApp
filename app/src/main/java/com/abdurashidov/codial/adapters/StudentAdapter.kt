package com.abdurashidov.codial.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdurashidov.codial.databinding.MentorAdapterItemBinding
import com.abdurashidov.codial.models.Student

class StudentAdapter(val list:ArrayList<Student>, val studentItemEvent: StudentItemEvent): RecyclerView.Adapter<StudentAdapter.Vh>() {

    inner class Vh(val rvItem: MentorAdapterItemBinding): RecyclerView.ViewHolder(rvItem.root){
        fun onBind(student: Student, position: Int){
            rvItem.name.text="${student.name} ${student.surname}"
            rvItem.edit.setOnClickListener {
                studentItemEvent.editClick(student, position)
            }
            rvItem.trash.setOnClickListener {
                studentItemEvent.trashClick(student, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(MentorAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface StudentItemEvent{
        fun editClick(student: Student, position: Int)
        fun trashClick(student: Student, position: Int)
    }
}