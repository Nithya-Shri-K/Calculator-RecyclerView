package com.example.taskcalculator

interface FragmentActionListener {
    fun selectedOperation(operationSelected: Operation)
    fun currentScreen(screen:Int)
}