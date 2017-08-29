package rdftools.rdf.api 

import org.apache.jena.graph.NodeFactory
import org.apache.jena.graph.{Triple=>JenaTriple}
import rdftools.rdf._
import rdftools.rdf.RdfTools._
import org.apache.jena.rdf.model.AnonId
import org.apache.jena.graph.Node
import org.apache.jena.datatypes.xsd.XSDDatatype
import org.apache.jena.rdf.model.ResourceFactory
import scala.language.implicitConversions
import org.apache.jena.graph.BlankNodeId
import org.apache.jena.graph.Node_URI
import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.RDFList
import org.apache.jena.rdf.model.Container
import org.apache.jena.rdf.model.{Literal=>JenaLiteral}
import org.apache.jena.rdf.model.{Property=>JenaProperty}
import org.apache.jena.rdf.model.RDFNode
import org.apache.jena.rdf.model.Resource
import org.apache.jena.riot.Lang
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import collection.JavaConversions._
import org.apache.jena.rdf.model.Statement

object JenaTools {
  import org.apache.jena.graph.NodeFactory._
  import org.apache.jena.datatypes.xsd.XSDDatatype._
  object ModelIO{
  def loadToModel(url:String,lang:Lang)={
    //val m=ModelFactory.createDefaultModel
    RDFDataMgr.loadModel(url,lang)
   
  }
  }
   
}