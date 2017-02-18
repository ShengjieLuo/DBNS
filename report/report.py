'''
'''

import os


class Header:
	def __init__(self):
		pass

	def flash(self):
		pass
	
	def getmd(self):
		md = "Report of Intellegent Maintain System \n=================================\n"
		return md

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
		pass

	def flash(self):
		pass
	
	def getmd(self):
		md = "Monitor Report \n===================================== \n"
		return md
		
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
			self._buildMD(filename)
		elif buildtype == "html" or buildtype == "HTML":
			self._buildHTML(filename)
		elif buildtype == "pdf" or buildtype == "PDF":
			self._buildPDF(filename)
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
		self._buildMD(filename)
		mdname = filename + ".md"
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
