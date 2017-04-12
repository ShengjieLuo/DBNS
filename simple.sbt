name := "Simple Project"
version := "1.0"
scalaVersion := "2.10.5"
libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.2"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.2"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.2"
//libraryDependencies += "org.apache.hbase" % "hbase-client" % "1.1.2"
//libraryDependencies += "org.apache.hbase" % "hbase-common" % "1.1.2"
//libraryDependencies += "org.apache.hbase" % "hbase-server" % "1.1.2"
//libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.2"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "1.2.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.2" 
libraryDependencies += "org.drools" % "drools-compiler" % "6.5.0.Final" 
libraryDependencies += "org.drools" % "drools-core" % "6.5.0.Final" 
libraryDependencies += "org.drools" % "drools-decisiontables" % "6.5.0.Final" 
libraryDependencies += "com.google.protobuf" % "protobuf-java" % "3.2.0"
libraryDependencies += "org.zeromq" % "jzmq" % "3.1.0"

resolvers += "jboss-releases" at "https://repository.jboss.org/nexus/content/repositories/releases"
