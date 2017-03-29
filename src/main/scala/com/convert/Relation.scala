package com.convert

import com.convert.ExternalElement

object Relation{

  val RelationList:Map[String]

  //TODO
  def query_atom(ele:ExternalElement):Boolean{
    return true
  }

  //TODO
  def query_per(ele:ExternalElement):List[ExternalElement]{
    return List(ele)
  }

}

