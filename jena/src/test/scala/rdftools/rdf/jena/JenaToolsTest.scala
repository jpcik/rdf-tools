/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf.jena

import rdftools.rdf.ParentTest
import org.apache.jena.riot.RDFDataMgr
import rdftools.rdf.RdfIO
import org.apache.jena.rdf.model.ModelFactory
import java.io.StringReader
import org.apache.jena.riot.Lang
import rdftools.rdf.jena._
import rdftools.rdf.RdfTools._
import collection.JavaConverters._
import org.apache.jena.rdf.model.ResourceFactory
import rdftools.rdf._


class JenaToolsTest extends ParentTest{
    
  
  
  val turtle="""
    @prefix : <:>.
    :a :p1 :b .
    :a :p2 :c .
    :c :p3 :d .
    :c :p2 :e .
    """
  
  "statement triple" should "be navigable" in {
    val m=ModelFactory.createDefaultModel
    val sr=new StringReader(turtle)
    RDFDataMgr.read(m,sr,"",Lang.TTL)

    m.listStatements().asScala.foreach{st=>
      println(st.getObject.getClass)
    }
    
    val p2s=m.byPredicate(":p2").toSeq   
    p2s.size shouldBe (2)
    p2s should contain (Triple(":a",":p2",":c")) 
    //p2s should contain (Triple(":c",":p2",":e")) 
   
    //m.byPredicate(":p1").foreach { t => 
    //  println(t.s.triple(":p2")) }
    //println(m.getResource(":c").triple(":p3"))
  }
}