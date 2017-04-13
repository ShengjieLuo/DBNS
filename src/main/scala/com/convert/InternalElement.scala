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

    if (name == "_SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP"){
      req.setRequestType("ALL")
      req.setAllParameter("SRCIP_SIZE","TCP","GroupBy")
      req.setParent(request.num)
      req.setNum(num)
      req.setName(name)
      println("  [Request] Build Internal Interface: "+"_SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP")
    } else if (name == "_SA_TCP_UPLOAD_COUNT_GROUPBY_SRCIP"){
      req.setRequestType("ALL")
      req.setAllParameter("SRCIP_COUNT","TCP","GroupBy")
      req.setParent(request.num)
      req.setNum(num)
      req.setName(name)
      println("  [Request] Build Internal Interface: "+"_SA_TCP_UPLOAD_COUNT_GROUPBY_SRCIP")
    } else if (name == "_SA_TCP_DOWNLOAD_SIZE_GROUPBY_DSTIP"){
      req.setRequestType("ALL")
      req.setAllParameter("DSTIP_SIZE","TCP","GroupBy")
      req.setParent(request.num)
      req.setNum(num)
      req.setName(name)
      println("  [Request] Build Internal Interface: "+"_SA_TCP_DOWNLOAD_SIZE_GROUPBY_DSTIP")
    } else if (name == "_SA_TCP_DOWNLOAD_COUNT_GROUPBY_DSTIP"){
      req.setRequestType("ALL")
      req.setAllParameter("DSTIP_COUNT","TCP","GroupBy")
      req.setParent(request.num)
      req.setNum(num)
      req.setName(name)
      println("  [Request] Build Internal Interface: "+"_SA_TCP_DOWNLOAD_COUNT_GROUPBY_DSTIP")
    } else if (name == "_ST_CHOOSE_FROM_TABLE"){
      req.setRequestType("TOOL")
      if (request.single.length > 0) {
        req.setToolParameter(request.single.last.content,"TCP",request.single.last.obj)
      } else if (request.compare.length > 0 && request.order==1){
        req.setToolParameter(request.compare.last.objectAType,"TCP",request.compare.last.objectA)
      } else if (request.compare.length > 0 && request.order==2){
        req.setToolParameter(request.compare.last.objectBType,"TCP",request.compare.last.objectB)
      }
      req.setParent(request.num)
      req.setNum(num)
      req.setName(name)
      println("  [Request] Build Internal Interface: "+"_ST_CHOOSE_FROM_TABLE")
    } else if (name == "_SA_TCP_UPLOAD_SIZE_AVERAGE_SRCIP"){
      req.setRequestType("ALL")
      req.setAllParameter("SRCIP_SIZE","TCP","Average")
      req.setParent(request.num)
      req.setNum(num)
      req.setName(name)
      println("  [Request] Build Internal Interface: "+"_SA_TCP_UPLOAD_SIZE_AVERAGE_SRCIP")
    } else if (name == "_SA_TCP_UPLOAD_COUNT_AVERAGE_SRCIP"){
      req.setRequestType("ALL")
      req.setAllParameter("SRCIP_COUNT","TCP","Average")
      req.setParent(request.num)
      req.setNum(num)
      req.setName(name)
      println("  [Request] Build Internal Interface: "+"_SA_TCP_UPLOAD_COUNT_AVERAGE_SRCIP")
    } else if (name == "_SC_TCP_DOWLOAD_UPLOAD_RATIO") {
      req.setRequestType("COMPARE")
      req.compare = request.compare
      req.setParent(request.num)
      req.setNum(num)
      req.setName(name)
      println("  [Request] Build Internal Interface: "+"_SC_TCP_DOWLOAD_UPLOAD_RATIO")
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


