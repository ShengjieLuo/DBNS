package com.registra

import com.registra.Element

//***************************************************************
// Service Table contents the overall service avaliable in the system
// The table includes the element within the type of service name and description
// The peresuisite is not included in the service table
// The table is initialed from the source file
//***************************************************************


class ServiceTable(){

  var table:List[Element] = List()

  def init_dictionary_from_file(file:String):Boolean={
    //READ the service name from txt and push it into table
    var leng = 0
    for(line<-Source.fromFile(file).getLines){
      line = line.trim.split(" ").filter(_!="")
      if (line.length != 2) {return false}
      var service = new Element(name=line(0),description=line(1))
      table.insert_element(service)
      leng = leng + 1
    }
    if (leng==table.length){return true}
    return false
  }

  def insert_element(E:Element):Boolean={
    var previous_length = table.length
    table =  E::table
    var revise_length = table.length
    if (revise_length == previous_length +1){return true}
    return false	 
  }

  def get_length():Int={
    return table.length
  }

  def get_elements():Boolean={
    table.foreach(e => e.show())
    return true
  }

  def get_first():Boolean={
    table(0).show()
    return true
  }

  def get_elementN(n:Int): Boolean={
    table(n-1).show()
    return true
  }

} 


