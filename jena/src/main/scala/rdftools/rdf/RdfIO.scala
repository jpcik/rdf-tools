/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

import org.apache.jena.riot.Lang
import org.apache.jena.riot.RDFParser
import org.apache.jena.riot.system.StreamRDF
import org.apache.jena.graph.{Triple=>JenaTriple}
import org.apache.jena.sparql.core.{Quad=>JenaQuad}
import rdftools.rdf.jena._
import org.apache.jena.riot.lang.PipedRDFIterator
import org.apache.jena.riot.lang.PipedTriplesStream
import collection.JavaConverters.asScalaIteratorConverter
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import java.io.InputStream

object RdfIO {
  private implicit val globalEc=scala.concurrent.ExecutionContext.Implicits.global
  
  private def pipeAndStream={
    val pipe=new PipedRDFIterator[JenaTriple]
    val str=new PipedTriplesStream(pipe) 
    (pipe,str)
  }
  
  def read(path:String)={
    val (pipe,str)=pipeAndStream
    Future(RDFParser.source(path).parse(str))
    pipe.asScala.map{t=>(t:Triple)}
  }
  
  def read(inputStream:InputStream)={
    val (pipe,str)=pipeAndStream
    Future(RDFParser.source(inputStream).parse(str))
    pipe.asScala.map{t=>(t:Triple)}
  }
 
}



