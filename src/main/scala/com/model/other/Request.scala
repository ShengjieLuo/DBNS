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

  def setTime(time:Time){
    beginTime = time.getbeginTime();
    endTime = time.getendTime();
  }
  def setNum(obj:Int) { num = obj;}
  def setRequestType(obj:String) { requestType = obj;}
  def setName(obj:String) { name = obj;}
  def setParent(obj:Int) { parent = obj;}
  def setRequestMode(obj:String) { requestMode = obj;}

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
}
