package com.convert

import com.model.other.Request

//import com.convert.Relation
//import com.convert.InternalElement

object ExternalNumber{
  var value = 0
  def getValue():Int={value=value+1;return value;}
}

class ExternalElement (elementName:String,req:Request,percendentName:Int){

  var name:String = elementName
  var atom:Boolean = Relation.query_atom(this)
  var num:Int = ExternalNumber.getValue()
  var percendent:Int = percendentName
  var request:Request = req
  var atomTable:List[InternalElement] = List()
  var interface:List[Request] = List()

  def getname():String = {return name}

  def _convertToInternal(tempTable:List[ExternalElement]){
      tempTable.foreach( element => {
        if (element.atom==true){
	  val internalElement:InternalElement = Relation.query_internal(element) 
          atomTable = atomTable :+ internalElement
	} else{
          val newTable:List[ExternalElement] = Relation.query_per(element)
          _convertToInternal(newTable)
        }  
      })
  }

  def convertToInternal(){
    System.out.println("  [Service] "+name+" ConvertToInternal ... begin!")
    if (atom == false){
      var tempTable:List[ExternalElement] = Relation.query_per(this)
      _convertToInternal(tempTable)
    } else {
      val internalElement:InternalElement = Relation.query_internal(this)
      atomTable = atomTable :+ internalElement
    }
    buildInterface()  
    System.out.println("  [Service] "+name+" ConvertToInternal ... done!")
  }

  def buildInterface(){
    atomTable.foreach( element =>
      interface = interface :+ element.getInterface()
    )
  }

  def getInterface():List[Request]={return interface}

  def show(){
    println("External Service Number:" + num)
    println("\n External Service Name:" + name)
    println("\n External Service Atomic:" + atom)
    println("\n External Service Prerequisite:\n"+interface)
  }

}

