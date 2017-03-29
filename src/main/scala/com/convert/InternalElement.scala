package com.convert

import com.convert.Relation
import com.convert.ExternalElement

object InternalNumber{
  var value = 0
  def getValue():Int={value=value+1;return value;}
}

class InternalElement (elementName:String,parameter:List[String]){

  var name:String = elementname
  var num:Int = InternalNumber.getValue()
  var para:List[String] = parameter
  var interface:String = buildInterface()

  def buildInterface(){
    var str = num.toString + " " + name + " "
    para.foreach(pa => {str = str + p + " ";p})
    str = str + "\n"
  }

  def getInterface():String = {return interface}

  def show(){
    println("Internal Service Number:" + num)
    println("\n Internal Service Name:" + name)
    println("\n Internal Service Show:\n"+interface)
  }
}
