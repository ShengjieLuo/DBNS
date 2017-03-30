package com

import org.drools.KnowledgeBase
import org.drools.KnowledgeBaseFactory
import org.drools.builder.KnowledgeBuilder
import org.drools.builder.KnowledgeBuilderError
import org.drools.builder.KnowledgeBuilderErrors
import org.drools.builder.KnowledgeBuilderFactory
import org.drools.builder.ResourceType
import org.drools.logger.KnowledgeRuntimeLogger
import org.drools.logger.KnowledgeRuntimeLoggerFactory
import org.drools.io.ResourceFactory
import org.drools.runtime.StatefulKnowledgeSession
import org.drools.event.KnowledgeRuntimeEventManager

import org.apache.spark._
import org.apache.spark.rdd.RDD

import org.drools.event._
import org.drools.runtime._
import org.drools.builder._

import com.model.other.Time
import com.model.other.Item
import com.model.other.Type
import com.model.problem.Trojan

object TrojanTest {

  def main(args : Array[String]):Unit ={
    
    println("Run Trojan Test by Distributed Rule Engine")
    val sparkconf = new SparkConf().setAppName("TrojanDetectionTest")
    val sc = new SparkContext(sparkconf)

    //Input the IP of the host machine you would like to detect
    val data = List("202.120.37.78","8.8.8.8")
    println("  [Debug] Begin Execution!")

    val pairRDD = sc.parallelize(data.map(p=>p.trim),1)
		    .mapPartitions{ partition => {
				      println("  [Debug] Load the knowledge session");
				      val ksession:StatefulKnowledgeSession = GetKnowledgeSession()
				      val time = new Time();
				      ksession.insert(time);
				      val ty   = new Type();
				      ksession.insert(ty);
				      
                                      val newPartition = partition.map(p => {
						println("  [Debug] Begin Dealing the element: "+p);
				                val item = new Item("ip-hostmachine",p.toString());
						ksession.insert(item);
                                      		ksession.fireAllRules();
						//println("  [Debug] Finish Dealing the element: "+p);
						(p,1)
						})						
    				      //println("  [Debug] Finish the data partition!");
				      newPartition
                                    }
                                  }
                    .reduceByKey(_+_)
                    .foreach{p => println("  [Debug] Detection Object: " + p._1.toString + ": Number :"+ p._2.toString)}
  }

  def GetKnowledgeSession() : StatefulKnowledgeSession = {
    val config:KnowledgeBuilderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration()
    config.setProperty("drools.dialect.mvel.strict", "false")
    var kbuilder : KnowledgeBuilder  = KnowledgeBuilderFactory.newKnowledgeBuilder(config)
    //kbuilder.add(ResourceFactory.newFileResource("/usr/local/DBNS/src/main/scala/com/rules/trojan/RuleV1.drl"), ResourceType.DRL)
    kbuilder.add(ResourceFactory.newFileResource("/usr/local/DBNS/test.drl"), ResourceType.DRL)
    println("  [Debug]  Rule Error:"+kbuilder.getErrors().toString())
    var kbase : KnowledgeBase = KnowledgeBaseFactory.newKnowledgeBase()
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages())
    var ksession : StatefulKnowledgeSession = kbase.newStatefulKnowledgeSession()
    var logger : KnowledgeRuntimeLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,"/root/DroolsSpark/drools.log")
    ksession
  }
}

