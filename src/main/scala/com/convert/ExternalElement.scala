package com.convert

import com.convert.Relation
import com.convert.InternalElement

object ExternalNumber{
  var value = 0
  def getValue():Int={value=value+1;return value;}
}

class ExternalElement (elementName:String,parameter:List[String]){

  var name:String = elementname
  var atom:Boolean = Relation.query_atom(name)
  var num:Int = ExternalNumber.getValue()
  var para:List[String] = parameter
  var atomTable:List[InternalElement] = List()
  var interface:String =""

  def _convertToInternal(tempTable:List[ExternalElement]){
      tempTable.foreach( element => {
        if (element.atom==True){
	  val internalElement = new InternalElement(element.name,element.para) 
          atomTable = internalElement::atomTable
	} else{
          val newTable = Relation.query_per(this)
          newTable._convertToInternal
        }  
      })
  }

  def convertToInternal(){
    var tempTable:List[ExternalElement] = Relation.query_per(this)
    _convertToInternal(tempTable)
    buildInterface()
  }

  def buildInterface(){
    atomTable.foreach( element =>
      interface = interface + element.getInterface()
    )
  }

  def getInterface():String={return interface}

  def show(){
    println("External Service Number:" + num)
    println("\n External Service Name:" + name)
    println("\n External Service Atomic:" + atom)
    println("\n External Service Prerequisite:\n"+interface)
  }

}

