package com.model.other;

class Request(){

  var requestType:String = ""
  var name:String = ""
  var parent:Int = -1
  var beginTime:Int = 0
  var endTime:Int = 0
  var requestMode:String = ""
  var num:Int = -1
  var binding:String = ""
  var order:Int = 0

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
  def setBinding(obj:String){binding = obj}
  def setOrder(obj:Int) {order=obj}

  def getNum():Int = {num}
  def getName():String = {name}
  def getParent():Int = {parent}
  def getRequestType():String = {requestType}
  def getRequestMode():String = {requestMode}
  def getBeginTime():Int = {beginTime}
  def getEndTime():Int = {endTime}
  def getBinding():String = {binding}
  def getOrder():Int = {order}

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

  def copyTo():Request = {
    var obj = new Request()
    obj.setName(name)
    obj.setNum(num)
    obj.setParent(parent)
    obj.setRequestType(requestType)
    obj.setRequestMode(requestMode)
    obj.setBeginTime(beginTime)
    obj.setEndTime(endTime)
    obj.setBinding(binding)
    obj.setOrder(order)
    if (all.length>0) {obj.all = this.all}
    if (tool.length>0) {obj.tool = this.tool}
    if (single.length>0) {obj.single = this.single}
    if (compare.length>0) {obj.compare = this.compare}
    return obj
  }

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

  def equals(obj:Request):Boolean = {
    if ( requestType == obj.requestType && name == obj.name && beginTime == obj.beginTime && endTime == obj.endTime && requestMode == obj.requestMode){
      if (requestType == "ALL"){
        return all.last.equals(obj.all.last)
      } else if (requestType == "SINGLE") {
        return single.last.equals(obj.single.last)
      } else if (requestType == "COMPARE") {
        return compare.last.equals(obj.compare.last)
      } else if (requestType == "TOOL"){
        return tool.last.equals(obj.tool.last)
      } else {
        return false
      }
    } else {
      return false
    }
  }
}
