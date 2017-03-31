package com.convert

import scala.io.Source

object Relation{

  val sourceFile = sys.env("DBNS_HOME")+"/src/main/scala/com/convert/relation.txt"
  val RelationList:Map[String,List[String]] = initRelationFromFile()

  def initRelationFromFile{
      val lines = Source.fromFile(sourceFile).getLines
      var count = 0
      for (line<-lines){
        if (line.startsWith("//")){
          count += 1
        } else if (line.startsWith("Section")){
 	  val line
        }
	

      }
  }

  //TODO
  def query_atom(ele:ExternalElement):Boolean={
    return true
  }

  //TODO
  def query_per(ele:ExternalElement):List[ExternalElement]={
    return List(ele)
  }

}

