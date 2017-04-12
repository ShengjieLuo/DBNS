package com.execute;

import com.model.other.Request

class HiveCmd(cmd:List[String],req:Request){

    var command:List[String] = cmd
    var request:Request = req

    def setCommand(cmd:List[String]){ command = cmd }
    def getCommand():List[String] = { return command}

    def setRequest(req:Request){ request = req }
    def getRequest():Request = {return request}

    def print(){
      command.foreach( p => println(Console.RED + "  [Hive] " + p + Console.WHITE) )
    }
}
