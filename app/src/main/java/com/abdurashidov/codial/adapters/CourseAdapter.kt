package com.abdurashidov.codial.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdurashidov.codial.databinding.CourseAdapterItemBinding
import com.abdurashidov.codial.models.Course

class CourseAdapter(val list:ArrayList<Course>, val context: Context, val rvAction: RvAction): RecyclerView.Adapter<CourseAdapter.Vh>() {

    inner class Vh(val rvItem: CourseAdapterItemBinding): RecyclerView.ViewHolder(rvItem.root){
        fun onBind(course: Course, position: Int){
            rvItem.name.text=course.name
            rvItem.card.setOnClickListener {
                rvAction.itemClick(course, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CourseAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RvAction{
        fun itemClick(course: Course, position: Int)
    }
}