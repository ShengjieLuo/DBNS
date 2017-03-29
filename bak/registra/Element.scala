package com.registra

import com.registra.Element
import com.topo.Topo
import com.topo.Prerequisite

object Number{
  var value = 0
  def getValue():Int={value=value+1;return value;}
}

class Element(name:String,description:String){

  var serviceName:String = name
  var serviceDesc:String = description
  var serviceAtom:Boolean = Topo.query_atom(name)
  var serviceNum:Int = Number.getValue()
  var servicePer:Prerequisite = Topo.query_per(name)

  def this(name:String){this(name,"")}

  def show(){
    println("Service Number:"+serviceNum.toString)
    println("Service Name:"+serviceName)
    println("Service Description:"+serviceDescription)
    println("Service Atomic:"+service)
    println("Service Prerequisite:"+servicePer.show())
  }

}

