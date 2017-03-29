package com.model.problem

import com.model.other.Time
import com.model.other.Item

class Trojan(detectItem:Item,detectTime:Time){
  
  var item:Item = detectItem
  var time:Time = detectTime
  var problems:List[String] = List()
  var hazard:String = "Trojan"
  var method:String = "/n  For User: Check the host machine program and delete the Trojan program"
  method = method + "/n  For Administor: Filter the network access from problem IP host machine" 

  def insertProblem(problem:String){
    problems = problem::problems
  }

  def insertProblems(problemList:List[String]){
    problemList.foreach(p => this.insertProblem(p))
  }

  def getProblems():List[String]={return problems;}

  def printProblems():String = {
    var str = ""
    problems.foreach(p => str=str+p)
    return str
  }

  def reportProblem():String={
     var str =  "BeingTime :	      "   + time.getbeginTime.toString() +
                "\nEndTime :		" + time.getendTime.toString() +
                "\nDetection Hazard :	" + hazard.toString() +
                "\nDetection Object : 	" + item.getname().toString() +
                "\nDetection Target : 	" + item.getobj().toString() +
                "\nDetection Reasons : 	" + item.printProblems().toString +
                "\nSuggestted Method :	" + method.toString() + "\n"
     str
  }
}
