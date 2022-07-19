package com.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var pressedDot: Boolean = false
    private var lastDot: Boolean = false
    private var lastNumeric: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit (view: View){
        tvInput?.append((view as Button).text)
        lastDot = false
        lastNumeric = true
    }

    fun onClear (view: View){
        tvInput?.text=""
        pressedDot = false
        lastNumeric = false
    }

    fun onDecimalPoint (view: View){
        if (lastNumeric && !lastDot && !pressedDot && !(tvInput?.text == "")){
            tvInput?.append(".")
            lastNumeric = false
            lastDot =  true
            pressedDot = true
        } else if (pressedDot && !(tvInput?.text == "") ){
            Toast.makeText(this, "Floating-point already added!", Toast.LENGTH_LONG).show()
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && (!isOperatorAdded(it.toString()) )){
                    tvInput?.append((view as Button).text)
                    lastNumeric = false
                    lastDot = false
                    pressedDot = false
            }
        }
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        } else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual (view : View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if (tvValue.contains("-")) {
                    if (tvValue.startsWith("-")) {
                        prefix = "-"
                        tvValue = tvValue.substring(1)
                    }

                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix == "-") {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }

                if (tvValue.contains("+")) {
                    if (tvValue.startsWith("+")) {
                        tvValue = tvValue.substring(1)
                    }

                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]


                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

                if (tvValue.contains("*")) {

                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]


                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

                if (tvValue.contains("/")) {

                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]


                    tvInput?.text =  removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }

    }

    private fun removeZeroAfterDot (result: String ) : String {
        var value = result
        var split : String
        var second : String?
        if(result.contains(".0")){
            split = result.split(".0").toString()
            second = split[1].toString()
            if(second != null){
                value = result.substring(0, result.length - 2)
            }
        }
        return value
    }




}


