package com.model.other;

class CompareParameter(obj1:String,obj2:String,obj3:String,obj4:String,obj5:String,obj6:String,obj7:String){

   var objectAType = obj1
   var objectA = obj2
   var objectBType = obj3
   var objectB = obj4
   var method = obj5
   var index = obj6
   var problem = obj7

   def print(){
     println("  [Request] Compare Object A type: "+objectAType);     
     println("  [Request] Compare Object A : "+objectA);     
     println("  [Request] Compare Object B type: "+objectBType);     
     println("  [Request] Compare Object B : "+objectB);     
     println("  [Request] Compare method: "+method);     
     println("  [Request] Compare index: "+index);     
     println("  [Request] Compare problem: "+problem);     
   }
}

class AllParameter(obj1:String,obj2:String,obj3:String){

   var content = obj1
   var kind = obj2
   var method = obj3

   
   def print(){
     println("  [Request] All method: "+method);     
     println("  [Request] All content: "+content);     
     println("  [Request] All kind: "+kind);     
   }
}

class SingleParameter(obj1:String,obj2:String,obj3:String){

   var content = obj1
   var kind = obj2
   var obj = obj3
   
   def print(){
     println("  [Request] Single object: "+obj);     
     println("  [Request] Single content: "+content);     
     println("  [Request] Single kind: "+kind);     
   }
}


class ToolParameter(obj1:String,obj2:String,obj3:String){

   var content = obj1
   var kind = obj2
   var obj = obj3
   
   def print(){
     println("  [Request] Tool object: "+obj);     
     println("  [Request] Tool content: "+content);     
     println("  [Request] Tool kind: "+kind);     
   }
}
