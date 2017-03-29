package com.model.other

class Item(){
  var name:String = ""
  var problems:List[String] = List()
  var obj:String = ""

  def getname():String = {return name}
  def setname(itemname:String){name=itemname}

  def getobj():String = {return obj}
  def setobj(objname:String){obj = objname}

  def insertProblem(problem:String){problems = problem::problems}

  def insertProblems(problemList:List[String]){problemList.foreach(p => this.insertProblem(p))}

  def getProblems():List[String]={return problems;}

  def printProblems():String = {
    var str = ""
    problems.foreach(p => str=str+p)
    return str
  }

}
