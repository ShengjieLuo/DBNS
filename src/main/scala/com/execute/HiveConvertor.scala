package com.execute

import com.model.other.Request
import com.internal.InternalTrigger

object HiveConvertor{

  def toCmd(req:Request) : HiveCmd = {
    var sql:List[String] = List()
    if (req.name == "_SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP") {
       sql = Convert_SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP(req)
    } else if (req.name == "_ST_CHOOSE_FROM_TABLE"){
       sql = Convert_ST_CHOOSE_FROM_TABLE(req)
    }
    var cmd:HiveCmd =  new HiveCmd(sql,req)
    return cmd
  }

  def Convert_SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP(req:Request): List[String] = {
    var sql:List[String] = List()
    var status:Int = InternalTrigger.queryStatus(req)
    // status = 0: A new task
    // status = 1: An old task
    if (status == 0) {
      var table:String = "RuEn.table" + req.num;
      var sql1:String = "CREATE TABLE " + table + "(ips varchar(20),count bigInt)"
      var sql2:String = "SELECT ips,count(*) as count FROM hrq.original WHERE time >  " + req.beginTime + " and time< " + req.endTime + " GROUP BY ips having count > 100 insert into " + table;
      sql = sql :+ sql1 :+ sql2
      InternalTrigger.insertEvent(req)
    } else {
      InternalTrigger.updateStatus(req)
    }
    return sql
  }

  def Convert_ST_CHOOSE_FROM_TABLE(req:Request): List[String] = {
    var sql:List[String] = List()
    var status:Int = InternalTrigger.queryStatus(req)
    if (status == 0){
      var sql1:String = null
      if (req.tool.last.content=="SRCIP_SIZE"){
        var tablenum = InternalTrigger.querySourceTable(req)
        sql1 = "SELECT * FROM RuEn.table" + tablenum + " WHERE ips = " + req.tool.last.obj
      }
      sql = sql :+ sql1
      InternalTrigger.insertEvent(req)
    } else {
      InternalTrigger.updateStatus(req)
    }
    return sql
  }
}
