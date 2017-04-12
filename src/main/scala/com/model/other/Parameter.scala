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

   def equals(obj:CompareParameter):Boolean = {
     if ( objectAType == obj.objectAType && objectA == obj.objectA && objectBType == obj.objectBType && objectB == obj.objectB && method == obj.method && index == obj.index && problem == obj.problem) {
       return true
     } else {
       return false
     }     
   }
 
   def getObjectAType():String = {return objectAType}
   def getObjectA():String = {return objectA}
   def getObjectBType():String = {return objectBType}
   def getObjectB():String = {return objectB}
   def getMethod():String = {return method}
   def getIndex():String = {return index}
   def getProblem():String = {return problem}
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

   def equals(obj:AllParameter):Boolean = {
     if ( method == obj.method && content  == obj.content && kind == obj.kind) {
       return true
     } else {
       return false
     }
   }

   def getContent():String = {return content}
   def getKind():String = {return kind}
   def getMethod():String = {return method}
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

   def equals(sin:SingleParameter):Boolean = {
     if ( obj == sin.obj && content  == sin.content && kind == sin.kind) {
       return true
     } else {
       return false
     }
   }
 
   def getContent():String = {return content}
   def getKind():String = {return kind}
   def getObj():String = {return obj}
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
 
   def equals(tool:ToolParameter):Boolean = {
     if ( obj == tool.obj && content  == tool.content && kind == tool.kind) {
       return true
     } else {
       return false
     }
   }

   def getContent():String = {return content}
   def getKind():String = {return kind}
   def getObj():String = {return obj}
}
