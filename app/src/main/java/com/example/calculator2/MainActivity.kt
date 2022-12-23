package com.example.calculator2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    lateinit var result:TextView
    var lastNumeric:Boolean=false
    var lastDot:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result=findViewById(R.id.result)
    }
    fun onDigit(view:View){
        if(result.text.toString().lowercase()=="infinity"){
            result.text=""
        }
        result.append((view as Button).text)
        lastNumeric=true
        lastDot=false
        when(view.id){
            R.id.CLR->{
                result.text=""
            }

        }
    }
    fun onDecimalPoint(view:View){
        if(result.text.toString().lowercase().endsWith("infinity")){
            result.text=""
            lastDot=false
            lastNumeric=false
        }
        if(lastNumeric && !lastDot){
            result.append((view as Button).text)
            lastDot=true
            lastNumeric=false
        }
    }
    fun onOperatorAdd(view:View){

        if(lastNumeric && !operatorAtEnd(result.text.toString())){
            result.append((view as Button).text)
            lastNumeric=false
            lastDot=false
        }
    }
    private fun operatorAtEnd(str:String):Boolean{
         if(str.endsWith("+")||str.endsWith("-")||
                str.endsWith("÷")||str.endsWith("×")){
            return true
        }
        return false
    }
    fun onEquals(view:View){
        var holdMinus:String?=null
        var newResult:String=result.text.toString()
        if(newResult.startsWith("-")){
            holdMinus="-"
            newResult=newResult.substring(1)
        }
        try {
            //subtract
            if(newResult.contains("-")&&lastNumeric){
                val array=newResult.split("-")
                var operand1:String=array[0]
                val operand2:String=array[1]
                if(holdMinus=="-"){
                    operand1=holdMinus+operand1
                }
                val value=(operand1.toDouble()-operand2.toDouble()).toString()
                result.text=removeZero(value)
                holdMinus=null
            }
            // add
            else if(newResult.contains("+")&&lastNumeric){
                val array=newResult.split("+")
                var operand1:String=array[0]
                val operand2:String=array[1]
                if(holdMinus=="-"){
                    operand1=holdMinus+operand1
                }
                val value=(operand1.toDouble()+operand2.toDouble()).toString()
                result.text=removeZero(value)
                holdMinus=null
            }
            //divide
            else if(newResult.contains("÷")&&lastNumeric){
                val array=newResult.split("÷")
                var operand1:String=array[0]
                val operand2:String=array[1]
                if(holdMinus=="-"){
                    operand1=holdMinus+operand1
                }
                val value=(operand1.toDouble()/operand2.toDouble()).toString()
                result.text=removeZero(value)
                holdMinus=null
            }
            //multiply
            else if(newResult.contains("×")&&lastNumeric){
                val array=newResult.split("×")
                var operand1:String=array[0]
                val operand2:String=array[1]
                if(holdMinus=="-"){
                    operand1=holdMinus+operand1
                }
                val value=(operand1.toDouble()*operand2.toDouble()).toString()
                result.text=removeZero(value)
                holdMinus=null
            }
        }catch (e:ArithmeticException){
            e.printStackTrace()
        }
    }
    private fun removeZero(value:String):String{
        if(value.endsWith(".0")){
            return value.substring(0,(value.length-2))
        }
        return value
    }
    fun backspace(view:View){
        if(result.text.toString()!="") {
            result.text = (result.text.toString()).substring(0, (result.text.toString()).length - 1)
        }
    }
}