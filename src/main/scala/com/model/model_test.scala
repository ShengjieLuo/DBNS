package com.model

import com.model.problem.Trojan
import com.model.other.Item
import com.model.other.Time

object ModelTest{
  def main()
  {
    var time = new Time()
    time.setbeginTime(1490630400)
    time.setendTime(1490634000)
    println("Time.BeginTime: \n" + time.getbeginTime())
    println("Time.EndTime: \n" + time.getendTime())

    val item = new Item()
    item.setname("IP-hostmachine")
    item.insertProblem("TCP upload more network flow than download")
    item.setobj("202.120.37.78")
    println("Item.printProblems: \n" + item.printProblems())
  
    var trojan = new Trojan(item,time)
    println("Trojan.printProblems: \n" + trojan.reportProblem())
 }
}
