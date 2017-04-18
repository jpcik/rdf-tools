package ch.hevs.rdftools.rdf

import org.apache.jena.rdf.model.ResourceFactory


object TestRdf {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val s="dfsdfs"                                  //> s  : String = dfsdfs
  s.split('|')                                    //> res0: Array[String] = Array(dfsdfs)
  val prop=ResourceFactory.createProperty("http://example.org/1Birth+Mother+")
                                                  //> SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
                                                  //| SLF4J: Defaulting to no-operation (NOP) logger implementation
                                                  //| SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further de
                                                  //| tails.
                                                  //| org.apache.jena.shared.InvalidPropertyURIException: http://example.org/1Birt
                                                  //| h+Mother+
                                                  //| 	at org.apache.jena.rdf.model.impl.PropertyImpl.checkLocalName(PropertyIm
                                                  //| pl.java:66)
                                                  //| 	at org.apache.jena.rdf.model.impl.PropertyImpl.<init>(PropertyImpl.java:
                                                  //| 55)
                                                  //| 	at org.apache.jena.rdf.model.ResourceFactory$Impl.createProperty(Resourc
                                                  //| eFactory.java:330)
                                                  //| 	at org.apache.jena.rdf.model.ResourceFactory.createProperty(ResourceFact
                                                  //| ory.java:168)
                                                  //| 	at ch.hevs.rdftools.rdf.TestRdf$$anonfun$main$1.apply$mcV$sp(ch.hevs.rdf
                                                  //| tools.rdf.TestRdf.scala:10)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSuppor
                                                  //| Output exceeds cutoff limit.
  
  
}