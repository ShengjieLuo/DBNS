object StringTest {

  case class NA(n: String, a: String)
  object NANA extends NA("", "")

  def main(args: Array[String]) {
    // number, month, day, year, time, hostname, module, severity, program, message
    val syslogPattern = ("<(\\d+)>([A-Za-z]{3})  (\\d{1,2}) (\\d{4}) " +
      "(\\d\\d:\\d\\d:\\d\\d) ([A-Za-z\\d\\-]+) (?:%%\\d+)?(\\w+)/(\\d)/" +
      "([A-Z]+(?:\\([a-z]\\))?)(?:\\[\\d+\\])?:(?:[A-Za-z\\d=,]+)?;?(.*)").r

    // number, month, day, time, src_ip, src_port, dst_ip, dst_port, protocol, nat_type, nat_ip, nat_port
    val natlogPattern = ("<(\\d+)>([A-Za-z]{3})  (\\d{1,2}) " +
      "(\\d\\d:\\d\\d:\\d\\d) .*?NAT: ((?:\\d{1,3}\\.){3}\\d{1,3}):" +
      "(\\d{1,5})->((?:\\d{1,3}\\.){3}\\d{1,3}):(\\d{1,5})\\((\\w+)\\)," +
      " ([sd]nat) to ((?:\\d{1,3}\\.){3}\\d{1,3}):(\\d{1,5}), .*").r

    println(util.parsing.json.JSON.parseFull("{\"a\":1}"))



//    val syslogPattern(number, month, day, year, time, hostname, module, severity, program, message) = "<186>Mar  6 2017 17:09:38 DXY-SHOU-HW-NE40E-01 %%01SRM/2/NODEFAULT(l)[840070]:Slot=1,Vcpu=0;PIC0 of LPU 1 is failed, perhaps RXPowLowAlarm of XFP0 ALARM is abnormal. (Reason=\"Card0 XFP RX power low alarm, Current Rxpower is -26.20dBm, threshold is -26.02dBm.\")"
//    println(number, month, day, year, time, hostname, module, severity, program, message)

    val natlogPattern(number, month, day, time, src_ip, src_port, dst_ip, dst_port, protocol, nat_type, nat_ip, nat_port) = "<190>Mar  6 11:04:19 1304415150001934(root) 46083623 Traffic@FLOW: NAT: 10.26.176.40:62338->180.153.105.157:80(TCP), snat to 218.78.247.169:55490, vr trust-vr, user -@UNKNOWN, host -, rule 4"
    println(number, month, day, time, src_ip, src_port, dst_ip, dst_port, protocol, nat_type, nat_ip, nat_port)


  }

}
