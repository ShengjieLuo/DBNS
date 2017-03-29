package com.model.data

// Now the data model only supports the hive mode
// Further, we would include variable types of distributed database

import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SQLContext, Row}
import org.apache.spark.sql.types._
import org.apache.spark.sql.hive.HiveContext

class tcp(begin:String,end:String,hiveSource:String,hiveDest:String){

  var beginTime:Int = begin.toInt
  var endTime:String = end.toInt
  var source:String = hiveSource
  var destination:String = hiveDest
  var status:Int = 0
  // Status = 0 : Data have not be loaded from source data table
  // Status = 1 : Data is being loaded to destination data table now
  // Status = 2 : Data have been loaded to destination table
  // Status = -1 : Something error occurred when loading
  

  def load_from_source(){
    //Env relatred to spark-Hive   
    val sc = new SparkConf().setAppName("Model::Data::TCP::load_from_source").setMaster("spark://spark-master:7077")
    val sssc = new SparkContext(sc)
    val sqlContext = new SQLContext(sssc)
    val hiveCtx = new HiveContext(sssc)

  }
}

