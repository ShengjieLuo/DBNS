package com.convert

class Convertor(serviceName:String,para:List[String]){

   var service:String = serviceName
   var parameter:List[String] = para

   def setService(servicename:String){service=servicename}
   def getService():String = {return service}

   def setparameter(para:List[String]){parameter=para}
   def getparameter():List[String] = {return parameter}

   //TODO
   def getInterface():List[String]={
	var externalService:ExternalElement = new ExternalElement(service,parameter)
        externalService.convertToInternal()
        var interface:List[String] = externalService.getInterface()
        interface
	}
}
