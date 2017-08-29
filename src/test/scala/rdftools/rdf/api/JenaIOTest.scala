package rdftools.rdf.api

import rdftools.rdf.ParentTest
import org.apache.jena.riot.RDFDataMgr
import collection.JavaConversions._
import rdftools.rdf.Triple
import rdftools.rdf.RdfIO

class JenaIOTest extends ParentTest{
  /*
  "load model" should "work" in {
    val ds=RDFDataMgr.loadGraph("src/main/resources/eswc-2017-complete.ttl")
    time ("list triples") {
      ds.find(null, null, null).foreach { t =>
        val ti=(t:Triple)
        ti.s
        ti.p
        ti.o
      }
    }
    time ("list triples") {
      ds.find(null, null, null).foreach { t =>
        t.getSubject 
        t.getPredicate
        t.getObject
      }
    }
    time ("list triples") {
      ds.find(null, null, null).foreach { t =>
        val ti=(t:Triple)
        ti.s
        ti.p
        ti.o
      }
    }*/
  "paa" should "" in {
    RdfIO.read("src/main/resources/eswc-2017-complete.ttl").foreach { println }
  }
}