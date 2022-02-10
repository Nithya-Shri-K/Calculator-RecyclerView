package com.example.taskcalculator


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(var data : Array<String>, var actionListener: FragmentActionListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     class ButtonsViewHolder(view : View) : RecyclerView.ViewHolder(view){
         val button: Button = view.findViewById<Button>(R.id.operation_button)
     }
    class ResultViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val resultText: TextView = view.findViewById<TextView>(R.id.result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == OPERATION_SCREEN) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.buttons_view, parent, false)
            ButtonsViewHolder(view)
        } else{
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.result_view, parent, false)
            ResultViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == OPERATION_SCREEN){
            val viewHolder = holder as ButtonsViewHolder
            viewHolder.button.text = data[position]
            viewHolder.button.setOnClickListener {
                actionListener.selectedOperation(Operation.valueOf(viewHolder.button.text.toString()))
            }
        }
        else{
            val viewHolder = holder as ResultViewHolder
            viewHolder.resultText.text = data[position]
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(data.size == 1) RESULT_SCREEN else OPERATION_SCREEN
    }
}