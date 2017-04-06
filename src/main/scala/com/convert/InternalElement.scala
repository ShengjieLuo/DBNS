package com.convert

//import com.convert.Relation
//import com.convert.ExternalElement

import com.model.other.Request

object InternalNumber{
  var value = 0
  def getValue():Int={value=value+1;return value;}
}

class InternalElement (elementName:String,req:Request){

  var name:String = elementName
  var num:Int = InternalNumber.getValue()
  //var para:List[String] = parameter
  //var request:Request = req
  var interface:Request = buildInterface(req)

  def buildInterface(request:Request):Request = {
    if (name=="_SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP"){
          request.setRequestType("ALL")
          request.setAllParameter("SRCIP_SIZE","TCP","GroupBy")
          request.setNum(num)
    }
    request
  }

  def getInterface():Request = {return interface}

  def show(){
    println("Internal Service Number:" + num)
    println("\n Internal Service Name:" + name)
    println("\n Internal Service Show:\n"+interface)
  }
}
