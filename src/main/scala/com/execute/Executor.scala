package com.execute;

import org.apache.spark._
import org.apache.spark.rdd.RDD
import com.model.other.Request

class Executor {

  val sparkconf = new SparkConf().setAppName("TrojanTestBackendExecutor")
  val sc = new SparkContext(sparkconf)
  
  def execute(req:Request){
    

    req.print()
  }
}
