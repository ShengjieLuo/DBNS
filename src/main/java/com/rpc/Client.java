package com.rpc;

import org.zeromq.ZMQ;
import com.model.other.Request;
import com.rpc.Interface.IRequest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class Client {

    ZMQ.Context context = null;
    ZMQ.Socket requester = null;

    public Client(){
      context = ZMQ.context(1);
      System.out.println("  [RPC] Connect to server spark-master:5556 ");
      requester = context.socket(ZMQ.REQ);
      requester.connect("tcp://spark-master:5556");
    }

    protected void finalize(){
      requester.close();
      context.term();      
    }
   
    public void send(List<Request> reqs){

      for (int i=0;i<reqs.size();i++){
        Request req = reqs.get(i);
        IRequest.Builder iRequest = IRequest.newBuilder();
        
        iRequest.setName(req.getName());
        iRequest.setNum(req.getNum());
        iRequest.setParent(req.getParent());
        iRequest.setBeginTime(req.getBeginTime());
        iRequest.setEndTime(req.getEndTime());
        
        if ( req.getRequestType().equals("ALL")){
	  iRequest.setType(IRequest.RequestType.ALL);
        } else if ( req.getRequestType().equals("SINGLE")){
          iRequest.setType(IRequest.RequestType.SINGLE);
        } else if ( req.getRequestType().equals("COMPARE")){
          iRequest.setType(IRequest.RequestType.COMPARE);
        } else if ( req.getRequestType().equals("TOOL")){
          iRequest.setType(IRequest.RequestType.TOOL);
        }
 
        if ( req.getRequestMode().equals("DEFAULT")){
 	   iRequest.setMode(IRequest.RequestMode.DEFAULT);
        } else if ( req.getRequestMode().equals("OPTIMIZED")){
           iRequest.setMode(IRequest.RequestMode.OPTIMIZED);
        } else if ( req.getRequestMode().equals("SIMPLE")){
           iRequest.setMode(IRequest.RequestMode.SIMPLE);
        }
	System.out.println("  [RPC] Send Package: "+req.getNum());
        requester.send(iRequest.build().toByteArray(), 0);
        byte[] reply = requester.recv(0);
        System.out.println("Received " + new String(reply));
      }
    }
}
