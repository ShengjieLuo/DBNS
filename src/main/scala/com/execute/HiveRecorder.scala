package com.execute

class HiveRecord(recordName:String){
  val name = recordName
  def getName():String={return name}
}

object HiveRecorder{

  var recordSum:Int = 0
  var records:List[HiveRecord] = List()

  def addRecord(rec:HiveRecord){
    recordSum = recordSum + 1
    records = records :+ rec
  }

  def addRecord(recname:String){
    recordSum = recordSum + 1
    records = records :+ new HiveRecord(recname)
  }
}
