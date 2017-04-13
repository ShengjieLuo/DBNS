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
    } else if (req.name == "_SA_TCP_UPLOAD_COUNT_GROUPBY_SRCIP"){
       sql = Convert_SA_TCP_UPLOAD_COUNT_GROUPBY_SRCIP(req)
    } else if (req.name == "_SA_TCP_DOWNLOAD_COUNT_GROUPBY_DSTIP"){
       sql = Convert_SA_TCP_DOWNLOAD_COUNT_GROUPBY_DSTIP(req)
    } else if (req.name == "_SA_TCP_DOWNLOAD_SIZE_GROUPBY_DSTIP"){
       sql = Convert_SA_TCP_DOWNLOAD_SIZE_GROUPBY_DSTIP(req)
    } else if (req.name == "_SA_TCP_UPLOAD_SIZE_AVERAGE_SRCIP"){
       sql = Convert_SA_TCP_UPLOAD_SIZE_AVERAGE_SRCIP(req)
    } else if (req.name == "_SA_TCP_UPLOAD_COUNT_AVERAGE_SRCIP"){
       sql = Convert_SA_TCP_UPLOAD_COUNT_AVERAGE_SRCIP(req)
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
      var sql1:String = "CREATE TABLE " + table + "(ips varchar(20),size bigInt)"
      var sql2:String = " INSERT INTO " + table + " SELECT ips,count(*) as size FROM hrq.original WHERE time >  " + req.beginTime + " and time< " + req.endTime + " GROUP BY ips having size > 1000";
      sql = sql :+ sql1 :+ sql2
      InternalTrigger.insertEvent(req)
    } else {
      InternalTrigger.updateStatus(req)
    }
    return sql
  }

  def Convert_SA_TCP_UPLOAD_COUNT_GROUPBY_SRCIP(req:Request): List[String] = {
    var sql:List[String] = List()
    var status:Int = InternalTrigger.queryStatus(req)
    if (status == 0) {
      var table:String = "RuEn.table" + req.num;
      var sql1:String = "CREATE TABLE " + table + "(ips varchar(20),count bigInt)"
      var sql2:String = " INSERT INTO " + table + " SELECT ips,count(*) as count FROM hrq.original WHERE time >  " + req.beginTime + " and time< " + req.endTime + " GROUP BY ips having count > 100";
      sql = sql :+ sql1 :+ sql2
      InternalTrigger.insertEvent(req)
    } else {
      InternalTrigger.updateStatus(req)
    }
    return sql
  }

  def Convert_SA_TCP_DOWNLOAD_SIZE_GROUPBY_DSTIP(req:Request): List[String] = {
    var sql:List[String] = List()
    var status:Int = InternalTrigger.queryStatus(req)
    // status = 0: A new task
    // status = 1: An old task
    if (status == 0) {
      var table:String = "RuEn.table" + req.num;
      var sql1:String = "CREATE TABLE " + table + "(ipd varchar(20),size bigInt)"
      var sql2:String = " INSERT INTO " + table + " SELECT ipd,count(*) as size FROM hrq.original WHERE time >  " + req.beginTime + " and time< " + req.endTime + " GROUP BY ipd having size > 1000";
      sql = sql :+ sql1 :+ sql2
      InternalTrigger.insertEvent(req)
    } else {
      InternalTrigger.updateStatus(req)
    }
    return sql
  }

  def Convert_SA_TCP_DOWNLOAD_COUNT_GROUPBY_DSTIP(req:Request): List[String] = {
    var sql:List[String] = List()
    var status:Int = InternalTrigger.queryStatus(req)
    if (status == 0) {
      var table:String = "RuEn.table" + req.num;
      var sql1:String = "CREATE TABLE " + table + "(ipd varchar(20),count bigInt)"
      var sql2:String = " INSERT INTO " + table + " SELECT ipd,count(*) as count FROM hrq.original WHERE time >  " + req.beginTime + " and time< " + req.endTime + " GROUP BY ipd having count > 100";
      sql = sql :+ sql1 :+ sql2
      InternalTrigger.insertEvent(req)
    } else {
      InternalTrigger.updateStatus(req)
    }
    return sql
  }

  def Convert_SA_TCP_UPLOAD_SIZE_AVERAGE_SRCIP(req:Request): List[String] = {
    var sql:List[String] = List()
    var status:Int = InternalTrigger.queryStatus(req)
    if (status == 0) {
      var table:String = "RuEn.table" + req.num;
      var sql1:String = "CREATE TABLE temp (ips varchar(20),size bigInt)"
      var sql2:String = "CREATE TABLE "+table+"(size bigInt)"
      var sql3:String = " INSERT INTO temp SELECT ips,count(*) as size FROM hrq.original WHERE time >  " + req.beginTime + " and time< " + req.endTime + " GROUP BY ips";
      var sql4:String = " INSERT INTO "+table+" SELECT avg(size) as size FROM temp";
      var sql5:String = " DROP TABLE temp"
      sql = sql :+ sql1 :+ sql2 :+ sql3 :+ sql4 :+ sql5
      InternalTrigger.insertEvent(req)
    } else {
      InternalTrigger.updateStatus(req)
    }
    return sql
  }  

  def Convert_SA_TCP_UPLOAD_COUNT_AVERAGE_SRCIP(req:Request): List[String] = {
    var sql:List[String] = List()
    var status:Int = InternalTrigger.queryStatus(req)
    if (status == 0) {
      var table:String = "RuEn.table" + req.num;
      var sql1:String = "CREATE TABLE temp (ips varchar(20),count bigInt)"
      var sql2:String = "CREATE TABLE "+table+"(count bigInt)"
      var sql3:String = " INSERT INTO temp SELECT ips,count(*) as count FROM hrq.original WHERE time >  " + req.beginTime + " and time< " + req.endTime + " GROUP BY ips";
      var sql4:String = " INSERT INTO "+table+" SELECT avg(count) as count FROM temp";
      var sql5:String = " DROP TABLE temp"
      sql = sql :+ sql1 :+ sql2 :+ sql3 :+ sql4 :+ sql5
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
      var sql2:String = null
      if (req.tool.last.content=="SRCIP_SIZE"){
        var tablenum = InternalTrigger.querySourceTable(req)
        var resultTable:String = "RuEn.table" + req.num
        sql1 = "CREATE TABLE " + resultTable + "(ips varchar(20),size bigInt)"
        sql2 = "INSERT INTO " + resultTable + " SELECT * FROM RuEn.table" + tablenum + " WHERE ips = \"" + req.tool.last.obj+"\""
      } else if (req.tool.last.content=="SRCIP_COUNT"){
        var tablenum = InternalTrigger.querySourceTable(req)
        var resultTable:String = "RuEn.table" + req.num
        sql1 = "CREATE TABLE " + resultTable + "(ips varchar(20),count bigInt)"
        sql2 = "INSERT INTO " + resultTable + " SELECT * FROM RuEn.table" + tablenum + " WHERE ips = \"" + req.tool.last.obj+"\""
      } else if (req.tool.last.content=="DSTIP_SIZE"){
        var tablenum = InternalTrigger.querySourceTable(req)
        var resultTable:String = "RuEn.table" + req.num
        sql1 = "CREATE TABLE " + resultTable + "(ips varchar(20),size bigInt)"
        sql2 = "INSERT INTO " + resultTable + " SELECT * FROM RuEn.table" + tablenum + " WHERE ipd = \"" + req.tool.last.obj+"\""
      } else if (req.tool.last.content=="DSTIP_COUNT"){
        var tablenum = InternalTrigger.querySourceTable(req)
        var resultTable:String = "RuEn.table" + req.num
        sql1 = "CREATE TABLE " + resultTable + "(ips varchar(20),count bigInt)"
        sql2 = "INSERT INTO " + resultTable + " SELECT * FROM RuEn.table" + tablenum + " WHERE ipd = \"" + req.tool.last.obj+"\""
      }
      
      sql = sql :+ sql1 :+ sql2
      InternalTrigger.insertEvent(req)
    } else {
      InternalTrigger.updateStatus(req)
    }
    return sql
  }

}
