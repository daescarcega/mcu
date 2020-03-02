package edu.daec.mcu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.mcu_dude_layout.view.*

class MUCDudeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private  var dudes: List<MUCDude> = ArrayList()

    /*
    Crea el layout
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MUCDudeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.mcu_dude_layout, parent, false)
            )
    }

    override fun getItemCount(): Int {
        return dudes.size
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MUCDudeViewHolder -> {
                holder.bind(dudes.get(position))
            }
        }

    }

    fun setData(listDudes: List<MUCDude>){
        dudes = listDudes
    }

    class MUCDudeViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.name_alias
        val notes = itemView.notes

        fun bind(mucDude: MUCDude){
            name.text = mucDude.name
            notes.text = mucDude.hnotes
        }

    }








}