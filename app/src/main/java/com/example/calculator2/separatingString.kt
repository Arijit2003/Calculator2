package com.example.calculator2

import kotlinx.coroutines.delay
import kotlin.math.pow

var separatedExp:ArrayList<String> = arrayListOf()
var A="6.5+2.3×3×(-356^56)"

var actualSeparatedExpression:MutableList<String> = mutableListOf()

var postfixExp:ArrayList<String> = arrayListOf()
var stack:MutableList<Char> = mutableListOf()
var top:Int=-1
fun pushInStack(input:Char){
    top++
    stack.add(top,input)

}
fun popInStack():Char{

    if(top!=-1) {
        val pop:Char= stack[top]
        stack.removeAt(top)
        top--
        return pop

    }
    return '0'

}
fun main(){
    if(A.startsWith("-")){
        separatedExp.add("-")
        A=A.substring(1)
    }
    splitMethod(A)
    println(separatedExp)
/////////////////////////////////////////****////
    var hold:String
    for(i in separatedExp.indices){
        hold= separatedExp[i]
        while (hold.startsWith('(')){
            actualSeparatedExpression.add("(")
            hold=hold.substring(1)
        }
        var arrayList:ArrayList<String> = arrayListOf()
        while (hold.endsWith(')')){
            arrayList.add(")")
            hold=hold.substring(0,hold.length-1)
        }
        if(hold!="") {
            actualSeparatedExpression.add(hold)
        }
        for (i in arrayList.indices){
            actualSeparatedExpression.add(arrayList[i])
        }
    }
    val minusStore:ArrayList<Int> = arrayListOf()
    for(i in actualSeparatedExpression.indices){
        if(actualSeparatedExpression[i]=="-"){
            if(i-1>-1){
                if(actualSeparatedExpression[i-1]=="("){

                    minusStore.add(i)
                    actualSeparatedExpression[i+1]= "-"+actualSeparatedExpression[i+1]

                }
            }
            else if(i-1==-1){
                minusStore.add(i)
                actualSeparatedExpression[i+1]= ("-".plus(actualSeparatedExpression[i+1])).toString()

            }
        }
    }

    for(i in minusStore.sortedDescending()){
        actualSeparatedExpression.removeAt(i)
    }
    println(actualSeparatedExpression)
    findPostfix(actualSeparatedExpression)
    println(postfixExp)

    // there is a problem with postfix evaluation
    evaluatePostfix(postfixExp)
    println(resultStack)

}
fun precedence(operator:String):Int{
    when(operator){
        "^"->{
            return 3
        }
        "×"->{
            return 2
        }
        "÷"->{
            return 2
        }
        "+"->{
            return 1
        }
        "-"->{
            return 1
        }
        else->{
            return 0
        }

    }
}

fun findPostfix(infix:MutableList<String>){
    for(i in (infix.indices)){
        when(infix[i]){
            "("->{
                pushInStack('(')
            }
            "+"->{

                if(top>-1) {
                    var holdChar:Char='2'
                    while (precedence(infix[i]) <= precedence(stack[top].toString())) {
                        holdChar = popInStack()
                        if (holdChar != '0' && holdChar != '2') {
                            postfixExp.add(holdChar.toString())
                        }
                        if (top  == -1) {
                            break
                        }
                    }
                }
                pushInStack('+')
            }
            "-"->{
                var holdChar:Char='2'
                if(top>-1) {
                    while (precedence(infix[i]) <= precedence(stack[top].toString())) {
                        holdChar = popInStack()
                        if (holdChar != '0' && holdChar != '2') {
                            postfixExp.add(holdChar.toString())
                        }
                        if (top  == -1) {
                            break
                        }
                    }
                }
                pushInStack('-')
            }
            "×"->{
                var holdChar:Char='2'
                if(top>-1) {
                    while (precedence(infix[i]) <= precedence(stack[top].toString())) {
                        holdChar = popInStack()
                        if (holdChar != '0' && holdChar != '2') {
                            postfixExp.add(holdChar.toString())
                        }
                        if (top  == -1) {
                            break
                        }
                    }
                    pushInStack('×')
                }
            }
            "÷"->{
                var holdChar:Char='2'
                if (top>-1) {
                    while (precedence(infix[i]) <= precedence(stack[top].toString())) {
                        holdChar = popInStack()
                        if (holdChar != '0' && holdChar != '2') {
                            postfixExp.add(holdChar.toString())
                        }
                        if (top  == -1) {
                            break
                        }
                    }
                }
                pushInStack('÷')
            }
            "^"->{
                var holdChar:Char='2'
                if(top>-1) {
                    while (precedence(infix[i]) <= precedence(stack[top].toString())) {
                        holdChar = popInStack()
                        if (holdChar != '0' && holdChar != '2') {
                            postfixExp.add(holdChar.toString())
                        }
                        if (top  == -1) {
                            break
                        }
                    }
                }
                pushInStack('^')
            }
            ")"->{
                var newString:String
                while (stack[top]!='('){
                    newString= popInStack().toString()
                    postfixExp.add(newString)
                }
                popInStack()
            }
            else->{
                postfixExp.add(infix[i])
            }
        }
    }
    // start from here

    var newPop:String
    while(top!=-1){
        newPop= popInStack().toString()
        postfixExp.add(newPop)
    }
}

fun splitMethod(value:String){
    if(value.contains('+')||value.contains('-')||value.contains('×')||value.contains('÷')||value.contains('^')) {
        val firstOperator = whoIsFirst(value)

        val split = newSplitAlgo(value,firstOperator)
        val first = split[0]
        val second = split[1]
        splitMethod(first)
        separatedExp.add(firstOperator.toString())
        splitMethod(second)
    }

    else{
        separatedExp.add(value)

    }
}

fun whoIsFirst(str:String):Char{
    for(i in str.indices){
        if(str[i]=='+'){
            return '+'
        }
        else if(str[i]=='-'){
            return '-'
        }
        else if(str[i]=='÷'){
            return '÷'
        }
        else if(str[i]=='×'){
            return '×'
        }
        else if(str[i]=='^'){
            return '^'
        }

    }
    return '0'
}

fun newSplitAlgo(str:String,operator:Char):List<String>{
    for(i in (0..(str.length-1))){
        if(str[i]==operator){
            return listOf(str.substring(0,i),str.substring(i+1,(str.length)))

        }
    }
    return listOf("0")
}

var resultStack:MutableList<Double> = mutableListOf()
var resultTop:Int=-1
fun pushInResultStack(input: Double){
    resultTop++
    resultStack.add(resultTop,input)
}
fun popFromResultStack():Double{
    if(resultTop!=-1){
        val popped:Double= resultStack.removeAt(resultTop)
        resultTop--
        return popped
    }
    return 0.0
}
fun evaluatePostfix(postfix:ArrayList<String>){
    var operand1:Double=0.0
    var operand2:Double=0.0
    for(i in postfix.indices){
        when(postfix[i]){
            "+"->{
                operand1= popFromResultStack()
                operand2= popFromResultStack()
                pushInResultStack(operand1+operand2)
            }
            "-"->{
                operand1= popFromResultStack()
                operand2= popFromResultStack()
                pushInResultStack(operand2-operand1)
            }
            "×"->{
                operand1= popFromResultStack()
                operand2= popFromResultStack()
                pushInResultStack(operand1*operand2)
            }
            "÷"->{
                operand1= popFromResultStack()
                operand2= popFromResultStack()
                pushInResultStack(operand2/operand1)
            }
            "^"->{
                operand1= popFromResultStack()
                operand2= popFromResultStack()
                pushInResultStack(operand2.pow(operand1))
            }
            else->{
                pushInResultStack(postfix[i].toDouble())
            }

        }
    }
}