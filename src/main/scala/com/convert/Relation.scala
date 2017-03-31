package com.convert

import scala.io.Source

object Relation{

  val sourceFile = sys.env("DBNS_HOME")+"/src/main/scala/com/convert/relation.txt"
  var relationMap:Map[Int,Map[String,List[Int]]] = Map()
  // relationMap Explanation
  // Right Case: S1 => S2 + S3 (Convert To) (1,("External",[2,3]))
  // Right Case: S3 => I3 (Convert To) (1,("Internal",[3]))

  var section1:List[String] = List()
  var section2:List[String] = List()
  var section3:List[String] = List()
  var externalServiceList:List[String] = List()
  var internalServiceList:List[String] = List()

  def initRelationFromFile{
      val lines = Source.fromFile(sourceFile).getLines
      var flag:Int = 0
      var count = 0
      for (line<-lines){
        if ( line.startsWith("//") || line == ""){
          flag = flag + 0
        } else if (line.startsWith("Section1")){
 	  flag = 1
        } else if (line.startsWith("Section2")){
          flag = 2
	} else if (line.startsWith("Section3")){
          flag = 3
        } else {
          if (flag == 1) {
              section1:+ line
          } else if (flag == 2) { 
              section2:+ line
          } else if (flag == 3) { 
              section3:+ line
          }
       }
      }
      _dealSection1()
      _dealSection2()
      _dealSection3()
  }

  def _dealSection1(){
    for (line<-section1){
      var result = line.trim().split(" ")(1)
      externalServiceList = externalServiceList :+ result
    }
  }

  def _dealSection2(){
    for (line<-section2){
      var result = line.trim().split(" ")(0)
      internalServiceList = internalServiceList :+ result
    }
  }

  def _dealSection3() = {
    for (line<-section3){
       val ls:Int = line.trim.split("=>")(0).trim.replace("S","0").toInt
       var stype:String = ""
       var elelist:List[Int] = List()
       for (item <- line.trim.split("=>")(1).split("\\+")) {
           if (item.trim.startsWith("I")){ elelist = item.trim.replace("I","0").toInt :: elelist; stype = "Internal" ;}
           if (item.trim.startsWith("S")){ elelist = item.trim.replace("S","0").toInt :: elelist; stype = "External" ;}
       }
       val rs:Map[String,List[Int]] = Map(stype -> elelist)
       relationMap = relationMap ++ Map(ls -> rs)
    }
  }

  def query_atom(ele:ExternalElement):Boolean={
    val elenum = externalServiceList.indexOf(ele.name,0)
    if (relationMap.apply(elenum).contains("Internal") == true) {return true}
    return false
  }

  def query_per(ele:ExternalElement):List[ExternalElement]={
    var returnList:List[ExternalElement] = List() 
    val elenum = externalServiceList.indexOf(ele.name,0)
    for (item <- relationMap.apply(elenum).apply("External")){
      val elename = externalServiceList(item)
      val elepara = ele.para
      val externalEle = new ExternalElement(elename,elepara)
      returnList = returnList :+ externalEle
    }
    return returnList
  }

  def query_internal(ele:ExternalElement):InternalElement = {
    val exelenum = externalServiceList.indexOf(ele.name,0)
    val inelenum = relationMap.apply(exelenum).apply("Internal")(0)
    val inelename = internalServiceList(inelenum)
    val internalEle = new InternalElement(inelename,ele.para)
    return internalEle
  }
}

