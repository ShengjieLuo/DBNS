package com.convert

import scala.io.Source

object Relation{

  val sourceFile = sys.env("DBNS_HOME")+"/src/main/scala/com/convert/Relation.txt"
  var relationMap:Map[Tuple2[Int,String],Map[String,List[Int]]] = Map()
  // relationMap Explanation
  // Right Case: S1 => S2 + S3 (Convert To) (1,("External",[2,3]))
  // Right Case: S3 => I3 (Convert To) (1,("Internal",[3]))

  var section1:List[String] = List()
  var section2:List[String] = List()
  var section3:List[String] = List()
  var section4:List[String] = List()
  var externalServiceList:List[String] = List()
  var internalServiceList:List[String] = List()
  var status:Int = initRelationFromFile()

  def initRelationFromFile():Int={
      System.out.println ("  [Service] Initial Relation From File!")
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
        } else if (line.startsWith("Section4")){
          flag = 4
        } else {
          if (flag == 1) {
              section1 = section1:+ line
          } else if (flag == 2) { 
              section2 = section2:+ line
          } else if (flag == 3) { 
              section3 = section3:+ line
          } else if (flag == 4) {
              section4 = section4:+ line
          }
       }
      }
      _dealSection1()
      _dealSection2()
      _dealSection3()
      _dealSection4()
      return 0
  }

  //TODO : Use a Gracefule method here
  def _dealSection1(){
    var result="NULL"
    for (line<-section1){
      if (line.trim().split(" ")(1).trim !=""){
        result = line.trim().split(" ")(1).trim
      } else if (line.trim().split(" ")(2).trim !=""){
        result = line.trim().split(" ")(2).trim
      }
      //System.out.println("  [Service] Section1 Service: "+result)
      externalServiceList = externalServiceList :+ result
    }
    System.out.println("  [Service] External Service List: "+externalServiceList)
  }

  def _dealSection2(){
    var result = "NULL"
    for (line<-section2){
      if (line.trim().split(" ")(1).trim !=""){
        result = line.trim().split(" ")(1).trim
      } else if (line.trim().split(" ")(2).trim !=""){
        result = line.trim().split(" ")(2).trim
      }
      internalServiceList = internalServiceList :+ result
    }
    System.out.println("  [Service] Internal Service List: "+internalServiceList)
  }

  def _dealSection3() = {
    for (line<-section3){
       val number:Int = line.trim.split("=>")(0).trim.replace("S","0").toInt
       val ls:Tuple2[Int,String] = new Tuple2(number,"")
       var stype:String = ""
       var elelist:List[Int] = List()
       for (item <- line.trim.split("=>")(1).split("\\+")) {
           if (item.trim.startsWith("I")){ elelist = elelist :+ item.trim.replace("I","0").toInt ; stype = "Internal" ;}
           if (item.trim.startsWith("S")){ elelist = elelist :+ item.trim.replace("S","0").toInt ; stype = "External" ;}
       }
       val rs:Map[String,List[Int]] = Map(stype -> elelist)
       relationMap = relationMap ++ Map(ls -> rs)
    }
  }

  def _dealSection4() = {
    for (line<-section4){
       val number:Int = line.trim.split("=>")(0).trim.split(" ")(0).replace("S","0").toInt
       val para:String = line.trim.split("=>")(0).trim.split(" ")(1).trim
       val ls:Tuple2[Int,String] = new Tuple2(number,para)
       var stype:String = ""
       var elelist:List[Int] = List()
       for (item <- line.trim.split("=>")(1).split("\\+")) {
           if (item.trim.startsWith("I")){ elelist = elelist :+ item.trim.replace("I","0").toInt ; stype = "Internal" ;}
           if (item.trim.startsWith("S")){ elelist = elelist :+ item.trim.replace("S","0").toInt ; stype = "External" ;}
       }
       val rs:Map[String,List[Int]] = Map(stype -> elelist)
       relationMap = relationMap ++ Map(ls -> rs)
    }
  }

  def query_atom(ele:ExternalElement):Boolean={
    val elenum = externalServiceList.indexOf(ele.name,0)
    //System.out.println("  [Service] External Element Name: "+ele.name+ " Element Number: "+elenum)
    if (ele.request.binding!="") {return false}
    if (relationMap.contains(Tuple2(elenum,""))){
       if (relationMap.apply(Tuple2(elenum,"")).contains("Internal") == true) {return true}
    }
    return false
  }

  def query_per(ele:ExternalElement):List[ExternalElement]={
    var returnList:List[ExternalElement] = List() 
    val elenum = externalServiceList.indexOf(ele.name,0)
    if ( relationMap.contains(Tuple2(elenum,ele.request.binding))){
      if ( relationMap.apply(Tuple2(elenum,ele.request.binding)).contains("External")) {
      var order = 1
      for (item <- relationMap.apply(Tuple2(elenum,ele.request.binding)).apply("External")){
        val elename = externalServiceList(item)
        val elerequest = ele.request.copyTo()
        val elepercendent = ele.num
        if (ele.request.requestType == "COMPARE") {elerequest.setOrder(order);order = order + 1;}
        elerequest.setBinding("")
        elerequest.setRequestType("")
        val externalEle = new ExternalElement(elename,elerequest,elepercendent)
        returnList = returnList :+ externalEle
      }}
    } /*else{
      if (relationMap.apply(Tuple2(elenum,"")).contains("External")){
        for (item <- relationMap.apply(Tuple2(elenum,"")).apply("External")){
          val elename = externalServiceList(item)
          //val elepara = ele.para
          val elepercendent = ele.num
          val externalEle = new ExternalElement(elename,ele.request,elepercendent)
          returnList = returnList :+ externalEle
        }
      }
    }*/
    return returnList
  }

  def query_internal(ele:ExternalElement):InternalElement = {
    val exelenum = externalServiceList.indexOf(ele.name,0)
    val inelenum = relationMap.apply(Tuple2(exelenum,"")).apply("Internal")(0)
    val inelename = internalServiceList(inelenum)
    System.out.println("  [Service] Internal Element Name: "+inelename+ " Element Number: "+inelenum)
    //val inelepara = ele.percendent.toString()::ele.para
    val internalEle = new InternalElement(inelename,ele.request)
    return internalEle
  }
}

