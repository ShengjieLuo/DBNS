package com.model.other;

class Request(){

  var requestType:String = ""
  var name:String = ""
  var parent:Int = -1
  var beginTime:Int = 0
  var endTime:Int = 0
  var requestMode:String = ""
  var num:Int = -1

  var compare:List[CompareParameter] = List()
  var all:List[AllParameter] = List()
  var single:List[SingleParameter] = List()
  var tool:List[ToolParameter] = List()

  def setTime(time:Time){
    beginTime = time.getbeginTime();
    endTime = time.getendTime();
  }
  def setNum(obj:Int) { num = obj;}
  def setRequestType(obj:String) { requestType = obj;}
  def setName(obj:String) { name = obj;}
  def setParent(obj:Int) { parent = obj;}
  def setRequestMode(obj:String) { requestMode = obj;}
  def setBeginTime(obj:Int) {beginTime = obj}
  def setEndTime(obj:Int) {endTime = obj}

  def getNum():Int = {num}
  def getName():String = {name}
  def getParent():Int = {parent}
  def getRequestType():String = {requestType}
  def getRequestMode():String = {requestMode}
  def getBeginTime():Int = {beginTime}
  def getEndTime():Int = {endTime}

  def setAllParameter(obj1:String,obj2:String,obj3:String){
      var obj:AllParameter = new AllParameter(obj1,obj2,obj3)
      all = all :+ obj
  }
 
  def setSingleParameter(obj1:String,obj2:String,obj3:String){
      var obj:SingleParameter = new SingleParameter(obj1,obj2,obj3)
      single = single :+ obj
  }
  
  def setCompareParameter(obj1:String,obj2:String,obj3:String,obj4:String,obj5:String,obj6:String,obj7:String){
      var obj:CompareParameter = new CompareParameter(obj1,obj2,obj3,obj4,obj5,obj6,obj7)
      compare = compare :+ obj
  }
  
  def setToolParameter(obj1:String,obj2:String,obj3:String){
      var obj:ToolParameter = new ToolParameter(obj1,obj2,obj3)
      tool = tool :+ obj
  }

  def getAllParameter():AllParameter = {return all.last}
  def getToolParameter():ToolParameter = {return tool.last}
  def getSingleParameter():SingleParameter = { return single.last}
  def getCompareParameter():CompareParameter = { return compare.last}  

  def print(){
      println("============Service Request============")
      println("  [Request] Request Number: "+num);
      println("  [Request] Request name: "+name);
      println("  [Request] Request Begin Time:"+beginTime)
      println("  [Request] Request End Time:"+endTime)
      println("  [Request] Request Mode:"+requestMode)
      println("  [Request] Request Type:"+requestType)
      println("  [Request] Request Parent:"+parent)
      if (requestType == "ALL"){
        all.last.print()
      } else if (requestType == "SINGLE"){
        single.last.print()
      } else if (requestType == "COMPARE"){
        compare.last.print()
      } else if (requestType == "TOOL"){
        tool.last.print()
      }
      println("=======================================")
  }
}
