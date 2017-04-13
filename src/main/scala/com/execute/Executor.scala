package com.execute;

import com.model.other.Request

class Executor {

  val hiveExecutor = new HiveExecutor()

  def execute(req:Request){
    val hiveCmd:HiveCmd = HiveConvertor.toCmd(req)
    hiveCmd.print()
    hiveExecutor.execute(hiveCmd)
    //val result:HiveResult = HiveExecutor.execute(hiveCmd)
  }

}
