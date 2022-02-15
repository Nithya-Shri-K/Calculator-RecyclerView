package com.example.taskcalculator


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(var data : Array<String>, var actionListener: FragmentActionListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     inner class ButtonsViewHolder(view : View) : RecyclerView.ViewHolder(view){
         private val button= view.findViewById<Button>(R.id.operation_button)
         fun bind(bindingData : String){
             button.text = bindingData
             button.setOnClickListener {
                 actionListener.selectedOperation(Operation.valueOf(button.text.toString()))
             }
         }
     }
    inner class ResultViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val resultText= view.findViewById<TextView>(R.id.result)
        fun bind(bindingData: String){
            resultText.text = bindingData
        }
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
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == OPERATION_SCREEN){
            val viewHolder = holder as ButtonsViewHolder
            viewHolder.bind(data[position])
        }
        else{
            val viewHolder = holder as ResultViewHolder
            viewHolder.bind(data[position])
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if(data.size == 1) RESULT_SCREEN else OPERATION_SCREEN
    }
    override fun getItemCount(): Int {
        return data.size
    }
}