package com.abdurashidov.codial.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdurashidov.codial.databinding.GroupAdapterItemBinding
import com.abdurashidov.codial.models.Group


class GroupAdapter(val list:ArrayList<Group>, val groupRvEvent: GroupRvEvent): RecyclerView.Adapter<GroupAdapter.Vh>() {

    inner class Vh(val rvItem:GroupAdapterItemBinding): RecyclerView.ViewHolder(rvItem.root){
        fun onBind(group: Group, position: Int){
            rvItem.name.text=group.name

            rvItem.view.setOnClickListener {
                groupRvEvent.viewClick(group, position)
            }
            rvItem.edit.setOnClickListener {
                groupRvEvent.editClick(group, position)
            }
            rvItem.trash.setOnClickListener {
                groupRvEvent.trashClick(group, position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(GroupAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface GroupRvEvent{
        fun viewClick(group: Group, position: Int)
        fun editClick(group: Group, position: Int)
        fun trashClick(group: Group, position: Int)
    }

}