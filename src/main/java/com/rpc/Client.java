package com.rpc;

import org.zeromq.ZMQ;
import com.model.other.Request;
import com.model.other.*;
import com.rpc.Interface.IRequest;
import com.rpc.Interface.IRequest.*;

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
	  com.rpc.Interface.IRequest.AllParameter.Builder iall = com.rpc.Interface.IRequest.AllParameter.newBuilder();
          com.model.other.AllParameter all = req.getAllParameter();
          iall.setContent(all.getContent());
          iall.setKind(all.getKind());
          iall.setMethod(all.getMethod());
          iRequest.setAll(iall.build());
        } else if ( req.getRequestType().equals("SINGLE")){
          iRequest.setType(IRequest.RequestType.SINGLE);
	  com.rpc.Interface.IRequest.SingleParameter.Builder isin = com.rpc.Interface.IRequest.SingleParameter.newBuilder();
          com.model.other.SingleParameter sin = req.getSingleParameter();
          isin.setContent(sin.getContent());
          isin.setKind(sin.getKind());
          isin.setObject(sin.getObj());
          iRequest.setSingle(isin.build());
        } else if ( req.getRequestType().equals("COMPARE")){
          iRequest.setType(IRequest.RequestType.COMPARE);
	  com.rpc.Interface.IRequest.CompareParameter.Builder icom = com.rpc.Interface.IRequest.CompareParameter.newBuilder();
          com.model.other.CompareParameter com = req.getCompareParameter();
          icom.setObjectAType(com.getObjectAType());
          icom.setObjectBType(com.getObjectBType());
          icom.setObjectA(com.getObjectA());
          icom.setObjectB(com.getObjectB());
	  icom.setMethod(com.getMethod());
	  icom.setIndex(com.getMethod());
	  icom.setProblem(com.getProblem());
          iRequest.setCompare(icom.build());
	  
        } else if ( req.getRequestType().equals("TOOL")){
          iRequest.setType(IRequest.RequestType.TOOL);
	  com.rpc.Interface.IRequest.ToolParameter.Builder itool = com.rpc.Interface.IRequest.ToolParameter.newBuilder();
          com.model.other.ToolParameter tool = req.getToolParameter();
          itool.setContent(tool.getContent());
          itool.setKind(tool.getKind());
          itool.setObject(tool.getObj());
          iRequest.setTool(itool.build());
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
        System.out.println("  [RPC] Received Package: " + new String(reply));
      }
    }
}
