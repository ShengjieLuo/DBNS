package com.execute

import com.model.other.Request
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext

class HiveExecutor{

  val sparkconf = new SparkConf().setAppName("TrojanHiveExecutor")
  val sc = new SparkContext(sparkconf)
  val sqlContext = new SQLContext(sc)
  val hiveCtx = new HiveContext(sc)
  
  def execute(hiveCmd:HiveCmd){ 
    hiveCmd.command.foreach(cmd => {
      hiveCtx.sql(cmd)
      HiveRecorder.addRecord(cmd)
    })
    println("Execute")
  }

}
