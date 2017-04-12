package com.execute;

import com.model.other.Request

class Executor {

  def execute(req:Request){
    val hiveCmd:HiveCmd = HiveConvertor.toCmd(req)
    hiveCmd.print()
    val hiveExecutor = new HiveExecutor()
    hiveExecutor.execute(hiveCmd)
    //val result:HiveResult = HiveExecutor.execute(hiveCmd)
  }

}
