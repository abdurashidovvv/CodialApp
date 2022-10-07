package com.abdurashidov.codial.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdurashidov.codial.databinding.MentorAdapterItemBinding
import com.abdurashidov.codial.models.Mentor

class MentorAdapter(val list:ArrayList<Mentor>, val mentorItemEvent: MentorItemEvent): RecyclerView.Adapter<MentorAdapter.Vh>() {

    inner class Vh(val rvItem:MentorAdapterItemBinding): RecyclerView.ViewHolder(rvItem.root){
        fun onBind(mentor:Mentor, position: Int){
            rvItem.name.text="${mentor.name} ${mentor.surname}"
            rvItem.edit.setOnClickListener {
                mentorItemEvent.editClick(mentor, position)
            }
            rvItem.trash.setOnClickListener {
                mentorItemEvent.trashClick(mentor, position)
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
    interface MentorItemEvent{
        fun editClick(mentor: Mentor, position: Int)
        fun trashClick(mentor: Mentor, position: Int)
    }
}