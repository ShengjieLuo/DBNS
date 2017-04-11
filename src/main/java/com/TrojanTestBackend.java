package com;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.model.other.Request;
import com.rpc.Interface.IRequest;
import com.execute.Executor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;


public class TrojanTestBackend
{
  public static void main(String[] args) throws Exception {
    Context context = ZMQ.context(1);

    //  Socket to talk to server
    Socket responder = context.socket(ZMQ.REP);
    responder.connect("tcp://localhost:5557");
    Executor executor = new Executor();

    while (!Thread.currentThread().isInterrupted()) {
    //  Wait for next request from client
      byte[] reply = responder.recv(0);

      IRequest ireq = IRequest.parseFrom(reply);
      Request req = new Request();
      req.setName(ireq.getName());
      req.setBeginTime(ireq.getBeginTime());
      req.setEndTime(ireq.getEndTime());
      req.setNum(ireq.getNum());
      req.setParent(ireq.getParent());

      switch (ireq.getType()){
	case ALL:
	  {
	    req.setRequestType("ALL");
            IRequest.AllParameter allobj = ireq.getAll();
	    String obj1 = allobj.getContent();
	    String obj2 = allobj.getKind();
	    String obj3 = allobj.getMethod();
            req.setAllParameter(obj1,obj2,obj3);
          }
	  break;
        case SINGLE:
          {
            req.setRequestType("SINGLE");
            IRequest.SingleParameter sinobj = ireq.getSingle();
	    String obj1 = sinobj.getContent();
	    String obj2 = sinobj.getKind();
	    String obj3 = sinobj.getObject();
	    req.setSingleParameter(obj1,obj2,obj3);
          }
	  break;
        case TOOL:
          {
            req.setRequestType("TOOL");
            IRequest.ToolParameter toolobj = ireq.getTool();
	    String obj1 = toolobj.getContent();
	    String obj2 = toolobj.getKind();
	    String obj3 = toolobj.getObject();
	    req.setToolParameter(obj1,obj2,obj3);
          }
          break;
        case COMPARE:
          {
            req.setRequestType("COMPARE");
            IRequest.CompareParameter comobj = ireq.getCompare();
	    String obj1 = comobj.getObjectAType();
	    String obj2 = comobj.getObjectA();
	    String obj3 = comobj.getObjectBType();
	    String obj4 = comobj.getObjectB();
	    String obj5 = comobj.getMethod();
	    String obj6 = comobj.getIndex();
	    String obj7 = comobj.getProblem();
	    req.setCompareParameter(obj1,obj2,obj3,obj4,obj5,obj6,obj7);
          }
	  break;
      }

      switch (ireq.getMode()){
        case DEFAULT:
          req.setRequestMode("DEFAULT");
          break;
        case OPTIMIZED:
          req.setRequestType("OPTIMIZED");
          break;
        case SIMPLE:
          req.setRequestType("SIMPLE");
          break;
      }

      //req.print();
      executor.execute(req);
      responder.send("Request "+ ireq.getNum());
    }

    responder.close();
    context.term();
  }
}
