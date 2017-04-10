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
    var req:Request = new Request()
    //TODO:Need a graceful implement
    req.name = request.name
    req.requestMode = request.requestMode
    req.requestType = request.requestType
    req.beginTime = request.beginTime
    req.endTime = request.endTime
    req.num = request.num
    req.parent = request.parent    

    if (name=="_SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP"){
      req.setRequestType("ALL")
      req.setAllParameter("SRCIP_SIZE","TCP","GroupBy")
      req.setParent(request.num)
      req.setNum(num)
      println("  [Request] Build Internal Interface: "+"_SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP")
    } else if (name=="_ST_CHOOSE_FROM_TABLE"){
      req.setRequestType("TOOL")
      req.setToolParameter("SRCIP_SIZE","TCP",request.single.last.obj)
      req.setParent(request.num)
      req.setNum(num)
      println("  [Request] Build Internal Interface: "+"_ST_CHOOSE_FROM_TABLE")
    }

    req
  }

  def getInterface():Request = {return interface}

  def show(){
    println("Internal Service Number:" + num)
    println("\n Internal Service Name:" + name)
    println("\n Internal Service Show:\n"+interface)
  }
}
