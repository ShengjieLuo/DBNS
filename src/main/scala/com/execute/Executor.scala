package com.execute;

import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SQLContext, Row}
import com.model.other.Request

class Executor {

  val sparkconf = new SparkConf().setAppName("TrojanTestBackendExecutor")
  val sc = new SparkContext(sparkconf)  
  val sqlContext = new SQLContext(sc)
  val hiveCtx = new HiveContext(sc)

  def execute(req:Request){
    //req.print()
    val hiveCmd:HiveCmd = HiveConvertor.toCmd(req)
    hiveCmd.print()
    //val result:HiveResult = HiveExecutor.execute(hiveCmd)
  }

}
