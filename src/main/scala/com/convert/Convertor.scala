package com.convert

import com.model.other.Request

class Convertor(serviceName:String,req:Request){

   var service:String = serviceName
   var request:Request = req

   def setService(servicename:String){service=servicename}
   def getService():String = {return service}

   def getInterface():List[Request]={
	var externalService:ExternalElement = new ExternalElement(service,request,-1)
        externalService.convertToInternal()
        var interface:List[Request] = externalService.getInterface()
        interface.foreach(item => item.print())
        interface
	}
}
