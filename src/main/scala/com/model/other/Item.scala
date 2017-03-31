package com.model.other

import com.service.TCPservice

class Item(itemname:String,itemobj:String) extends TCPservice(itemobj:String) {
  
  var name:String = itemname
  var problems:List[String] = List()
  override val obj:String = itemobj

  def getname():String = {return name}
  //def setname(itemname:String){name=itemname}

  def getobj():String = {return obj}
  //def setobj(objname:String){obj = objname}

  def insertProblem(problem:String){problems = problem::problems}

  def insertProblems(problemList:List[String]){problemList.foreach(p => this.insertProblem(p))}

  def getProblems():List[String]={return problems;}

  def printProblems():String = {
    var str = ""
    problems.foreach(p => str=str+p)
    return str
  }

  //def SS_TCP_UPLOAD_SIZE_SRCIP(time:Time,other:String){}
  //def SS_TCP_DOWNLOAD_SIZE_DSTIP(time:Time,other:String){}  
  //def SS_TCP_UPLOAD_COUNT_SRCIP(time:Time,other:String){}
  //def SS_TCP_DOWNLOAD_COUNT_DSTIP(time:Time,other:String){}
}
