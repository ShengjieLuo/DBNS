package com.internal;

import com.model.other.Request;

object InternalTrigger{

  var registra:List[Tuple3[Request,Int,Int]] = List()
  var total:List[Tuple3[Request,Int,Int]] = List()
  var reflection:Map[Int,Int] = Map()

  def queryStatus(req:Request):Int = {
    var status = 0;
    registra.foreach( p => if (p._1.equals(req)) {status = 1; reflection = reflection ++ Map(req.num->p._2)})
    if (status == 0) {reflection = reflection ++ Map(req.num->req.num)}
    return status
  }  

  def updateStatus(req:Request){
    var rootNum:Int = req.num
    var rootParent:Int = req.parent
    total = total :+ Tuple3(req,rootNum,rootParent)
  }

  def insertEvent(req:Request){
    
    var rootNum:Int = req.num
    var rootParent:Int = req.parent

    registra = registra :+ Tuple3(req,rootNum,rootParent)
    total = total :+ Tuple3(req,rootNum,rootParent)
    println("  [Internal] The registra table size: "+registra.size)
  }

  def querySourceTable(req:Request):Int = {
   var result:Int = -1
   total.foreach( p => if ( p._3 == req.parent) { result = p._2 } )
   result = reflection.apply(result)
   return result
  }
} 
