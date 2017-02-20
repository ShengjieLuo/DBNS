'''
'''

import os
import re
import time
import datetime
from tools import *
from table import *
from image import *

class DBNS:
	def __init__(self):
		self.version=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"version\"")[0][0]
		self.frame=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"frame\"")[0][0]
		self.master=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"master\"")[0][0]
		self.slaves=self._multiitem(exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"slaves\""))
		self.messageio=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"message\"")[0][0]
		self.stream=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"stream\"")[0][0]
		self.online=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"online\"")[0][0]
		self.offline=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"offline\"")[0][0]
		self.meta=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"meta\"")[0][0]
		self.temp=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"temp\"")[0][0]
		self.basic=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"basic\"")[0][0]
		self.streamcores=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"stream-cores\"")[0][0]
		self.onlinecores=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"online-cores\"")[0][0]
		self.offlinecores=exeSQLquery("SELECT value FROM DBNS.metadata WHERE name = \"offline-cores\"")[0][0]
	
	def _multiitem(self,items):
		string = ""
		for i in items:
			string = string + i[0] +" "
		return string

class Header:
	def __init__(self):
		self.template = os.environ.get('DBNS_HOME')+"/report/header.template.md"
		self._load()

	def setTemplate(self,name):
		self.template = name

	def getTemplate(self):
		return self.template

	def _load(self):
		fp = open(self.template)		
		self.md = fp.read()
		fp.close()		

	def _flash(self):
		time = datetime.datetime.now()
		timestamp = str(time.strftime("%Y-%m-%d %H:%M"))
		metadata = DBNS()
		self.md = sub(self.md,"header::time",timestamp)
		self.md = sub(self.md,"header::DBNS::version",metadata.version)
		self.md = sub(self.md,"header::DBNS::frame",metadata.frame)
		self.md = sub(self.md,"header::DBNS::master",metadata.master)
		self.md = sub(self.md,"header::DBNS::slaves",metadata.slaves)
		self.md = sub(self.md,"header::DBNS::stream",metadata.stream)
		self.md = sub(self.md,"header::DBNS::message",metadata.messageio)
		self.md = sub(self.md,"header::DBNS::online",metadata.online)
		self.md = sub(self.md,"header::DBNS::offline",metadata.offline)
		self.md = sub(self.md,"header::DBNS::meta",metadata.meta)
		self.md = sub(self.md,"header::DBNS::temp",metadata.temp)
		self.md = sub(self.md,"header::DBNS::basic",metadata.basic)
		self.md = sub(self.md,"header::DBNS::stream-cores",metadata.streamcores)
		self.md = sub(self.md,"header::DBNS::online-cores",metadata.onlinecores)
		self.md = sub(self.md,"header::DBNS::offline-cores",metadata.offlinecores)
			
	def getmd(self):
		self._flash()	
		#md = "Report of Intellegent Maintain System \n=================================\n"
		return self.md

class Conclusion:
	def __init__(self):
		pass

	def flash(self):
		pass

	def getmd(self):
		md = "Conclusion of Intellegent Maintain System \n=====================================\n"
		return md

class Monitor:
	def __init__(self):
		self.template = os.environ.get('DBNS_HOME')+"/report/monitor.template.md"
		self._load()

	def setTemplate(self,name):
		self.template = name

	def getTemplate(self):
		return self.template

	def _load(self):
		fp = open(self.template)		
		self.md = fp.read()
		self.mdhtml = self.md
		fp.close()		

	def _flash(self):
		table = draw_table("select * from DBNS.DRSipd;",None,"DRS IP destination","First Occurred Time","IPaddress","Frequency",10)[0]
		self.md = sub(self.md,"Monitor::DRS::DRSipd::table",table)
		table = draw_table("select * from DBNS.DRSips;",None,"DRS IP source","First Occurred Time","IPaddress","Frequency",10)[0]
		self.md = sub(self.md,"Monitor::DRS::DRSips::table",table)
		image = draw_image("select * from DBNS.DRSips;","Monitor::DRS::DRSips::image.png","DRS IP source","Frequency",10)
		self.md = sub(self.md,"Monitor::DRS::DRSips::image",image)
		image = draw_image("select * from DBNS.DRSipd;","Monitor::DRS::DRSips::image.png","DRS IP destination","Frequency",10)
		self.md = sub(self.md,"Monitor::DRS::DRSipd::image",image)
		#table = draw_table("select * from DBNS.DRSpd;",None,"DRS Port destination","First Occurred Time","Port Address","Frequency",10)[0]
                #self.md = sub(self.md,"monitor::DRS::DRSipd::table",table)
		#table = draw_table("select * from DBNS.DRSps;",None,"DRS Port source","First Occurred Time","Port Address","Frequency",10)[0]
                #self.md = sub(self.md,"monitor::DRS::DRSipd::table",table)	

	def _flashHtml(self):
		table = draw_table("select * from DBNS.DRSipd;",None,"DRS IP destination","First Occurred Time","IPaddress","Frequency",10)[1]
		self.mdhtml = sub(self.mdhtml,"Monitor::DRS::DRSipd::table",table)
		table = draw_table("select * from DBNS.DRSips;",None,"DRS IP source","First Occurred Time","IPaddress","Frequency",10)[1]
		self.mdhtml = sub(self.mdhtml,"Monitor::DRS::DRSips::table",table)
		image = draw_image("select * from DBNS.DRSips;","Monitor::DRS::DRSips::image.png","DRS IP source","Frequency",10)
		self.mdhtml = sub(self.mdhtml,"Monitor::DRS::DRSips::image",image)
		image = draw_image("select * from DBNS.DRSipd;","Monitor::DRS::DRSips::image.png","DRS IP destination","Frequency",10)
		self.mdhtml = sub(self.mdhtml,"Monitor::DRS::DRSipd::image",image)
		

	def getmd(self):
		self._flash()	
		return self.md

        def getmdHtml(self):
		self._flashHtml()
		return self.mdhtml

class Probe:
	def __init__(self):
		pass
	
	def flash(self):
		pass
	
	def getmd(self):
		md = "Probe Report \n===================================== \n"
		return md

class Report:
	def __init__(self):
		print "Begin print the network security report:\n"
		self.header = Header()
		print "Build Report Header -------------------- finish"
		self.conclusion = Conclusion()
		print "Build Report Conclusion ---------------- finish"
		self.monitor = Monitor()
		print "Build Report Monitor ------------------- finish"
		self.probe = Probe()
		print "Build Report Probe --------------------- finish"
	
	def build(self,buildtype,filename):
		if buildtype == "md" or buildtype == "MD" or buildtype == "markdown":
			self._buildMD(os.environ.get('DBNS_HOME')+'/report/'+filename)
		elif buildtype == "html" or buildtype == "HTML":
			self._buildHTML(os.environ.get('DBNS_HOME')+'/report/'+filename)
		elif buildtype == "pdf" or buildtype == "PDF":
			self._buildPDF(os.environ.get('DBNS_HOME')+'/report/'+filename)
		else:
			self._throwError("buildtype")
	
	def _throwError(errortype):
		if errortype == "buildtype":
			print "Error: Please use the correct build type (md/html/pdf)\n"

	def _buildMD(self,filename):
		head = self.header.getmd()
		conc = self.conclusion.getmd()
		moni = self.monitor.getmd()
		prob = self.probe.getmd()
		md = head + moni + prob + conc
		filename = filename+".md"
		fp = open(filename,'w')
		fp.write(md)
		fp.close()
		print "Build Markdown Report ------------------ finish"
	
	def _buildHTML(self,filename):	
		head = self.header.getmd()
		conc = self.conclusion.getmd()
		moni = self.monitor.getmdHtml()
		prob = self.probe.getmd()
		html = head+conc+moni+prob
		mdname = filename+".md.tmp"
                fp = open(mdname,'w')
                fp.write(html)
                fp.close()
		htmlname = filename + ".html"		
		os.system("markdown2 " + mdname +" > "+ htmlname)
		print "Build Html Report ---------------------- finish"	
	
	def _buildPDF(self,filename):
		self._buildHTML(filename)
		htmlname = filename + ".html"
		pdfname = filename + ".pdf"
		os.system("xvfb-run -a -s \"-screen 0 640x480x16\" wkhtmltopdf "+htmlname+" "+pdfname)
		print "Build Pdf Report ----------------------- finish"	

if __name__=="__main__":
	report = Report()
	report.build("pdf","sample") 
	report.build("md","sample") 
