package com.example.taskcalculator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskcalculator.databinding.FragmentMainScreenBinding


class MainScreen : Fragment() {
    private lateinit var actionListener: FragmentActionListener
    private lateinit var binding: FragmentMainScreenBinding
    lateinit var adapterData: Adapter
    var isResultPage = 0
    var result = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        val buttonData = arrayOf(getString(R.string.add), getString(R.string.subtract), getString(R.string.multiply), getString(R.string.divide))
        if(savedInstanceState ==null) {
            adapterData = Adapter(buttonData, actionListener)
        }
        else{
            actionListener = activity as MainActivity
            isResultPage = savedInstanceState.getInt(IS_RESULT_PAGE)
            result = savedInstanceState.getString(RESULT) ?: ""
            adapterData = Adapter(buttonData, actionListener)
            if (isResultPage == 1) {
                modifyScreenForResult()
            }
        }
        binding.buttonsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterData
            setHasFixedSize(true)
        }
        binding.toolbarTitle.text = getString(R.string.toolbar_header)

        setFragmentResultListener(REQUEST_KEY) { requestKey, bundle ->

             result = getString(
                R.string.result_text, bundle.getString(RESULT), bundle.getString(
                    OPERAND1
                ), bundle.getString(OPERAND2), bundle.getString(OPERATION)
            )
            modifyScreenForResult()
        }
        binding.reset.setOnClickListener {
            adapterData.data = buttonData
            adapterData.notifyDataSetChanged()
            binding.reset.visibility = View.GONE
            result=""
        }

        return binding.root
    }
    fun setFragmentActionListener(listener: FragmentActionListener){
        actionListener = listener
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(result != ""){
            outState.putInt(IS_RESULT_PAGE, 1)
            outState.putString(RESULT, result)
        }
    }
    private fun modifyScreenForResult(){
        adapterData.data = arrayOf(result)
        adapterData.notifyDataSetChanged()
        binding.buttonsRecyclerView.setHasFixedSize(true)
        binding.reset.visibility = View.VISIBLE
        actionListener.currentScreen(HOME_SCREEN)
        actionListener.selectedOperation(Operation.DEFAULT)
    }

}


