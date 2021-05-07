/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf.jena

import org.apache.jena.riot.RDFDataMgr
import org.apache.jena.riot.Lang

object IOTools {
  import org.apache.jena.graph.NodeFactory._
  import org.apache.jena.datatypes.xsd.XSDDatatype._
  object ModelIO{
  def loadToModel(url:String,lang:Lang)={
    //val m=ModelFactory.createDefaultModel
    RDFDataMgr.loadModel(url,lang)
   
  }
  }
   

}