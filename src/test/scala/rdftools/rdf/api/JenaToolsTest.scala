package rdftools.rdf.api

import rdftools.rdf.ParentTest
import org.apache.jena.riot.RDFDataMgr
import rdftools.rdf.RdfIO
import org.apache.jena.rdf.model.ModelFactory
import java.io.StringReader
import org.apache.jena.riot.Lang
import rdftools.rdf.api.JenaTools._
import rdftools.rdf.RdfTools._
import collection.JavaConversions._
import org.apache.jena.rdf.model.ResourceFactory

class JenaToolsTest extends ParentTest{
  
  val turtle="""
    @prefix : <:>.
    :a :p1 :b .
    :a :p2 :c .
    """
  
  "statement triple" should "be navigable" in {
    val m=ModelFactory.createDefaultModel
    val sr=new StringReader(turtle)
    RDFDataMgr.read(m,sr,"",Lang.TTL)
    val p=ResourceFactory.createProperty(":p2")
    //m.listStatements().foreach(println)
    m.listStatements().foreach{
     s=>println(s.getSubject.getProperty(p) )
    }
    m.byPredicate(":p1").foreach { t => 
      println(t.s.triple(":p2")) }
    
  }
}