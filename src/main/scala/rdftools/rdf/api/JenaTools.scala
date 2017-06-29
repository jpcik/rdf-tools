package rdftools.rdf.api 

import org.apache.jena.graph.NodeFactory
import org.apache.jena.graph.Triple
import rdftools.rdf.{Triple=>RdfTriple}
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
import org.apache.jena.rdf.model.RDFNode
import org.apache.jena.rdf.model.Resource

package object JenaTypes {
  type TripleList = java.util.List[Triple]
}

object JenaTools {
  val TTL="TTL"
  import org.apache.jena.graph.NodeFactory._
  import org.apache.jena.datatypes.xsd.XSDDatatype._
  
  def toJenaRes(s:String)=
    ResourceFactory.createResource(s)  

  def toJenaRes(iri:Iri)=
    ResourceFactory.createResource(iri.path)  

  implicit def toJenaNode(clazz:Class)=
    ResourceFactory.createResource(clazz.iri.path)  

  implicit def toIriNode(s:String)=
    NodeFactory.createURI(s)

  implicit def toJenaProperty(iri:Iri)=
    ResourceFactory.createProperty(iri.path)

  implicit def toJenaProperty(prop:Property)=
    ResourceFactory.createProperty(prop.iri.path)

  implicit def toJenaLit(lit:AnyLiteral)=
    ResourceFactory.createPlainLiteral(lit.value.toString)
    
  implicit def toJenaTriple(t:RdfTriple):Triple={
    new Triple(t.subject ,t.predicate ,t._object )
  }
 
  def iri(n:Node)=Iri(n.getURI)
  /*
  implicit def fromNode(n:Node)=n match {
    case i:Node_URI=> iri(i.getURI)
    case l: org.apache.jena.rdf.model.Literal =>lit(l.getString) 
  }
  */
  /*
  implicit def fromJenaTriple(t:Triple):RdfTriple={
    RdfTriple(iri(t.getSubject),iri(t.getPredicate),t.getObject)
  }
  */
  
  def +=(s:Iri,p:Iri,o:Iri)(implicit m:Model)={
    m.add(s:Resource,p,o:Resource)
  }
  def +=(s:Iri,p:Iri,o:String)(implicit m:Model)={
    m.add(toJenaRes(s),p,o)
  }
  def +=(s:Iri,p:Iri,o:JenaLiteral)(implicit m:Model)={
    m.add(s,p,o)
  }
  def +=(s:Iri,p:Iri,list:RDFList)(implicit m:Model)={
    m.add(s,p,list)
  } 
  def +=(s:Iri,p:Iri,cont:Container)(implicit m:Model)={
    m.add(s,p,cont)
  } 
  
  
  implicit def toJenaNode(t:RdfTerm):Node=t match {
    case i:Iri=>createURI(i.toString)
    case bn:Bnode=>createBlankNode(new BlankNodeId(bn.id))
    case l:Literal=>l.value match {
      case i:Integer=>createLiteral(l.value.toString,XSDinteger)
      case i:Double=>createLiteral(l.value.toString,XSDdouble)
      case i:Boolean=>createLiteral(l.value.toString,XSDboolean)
      case ln:Long=>createLiteral(ln.toString,XSDlong)
      case _=>createLiteral(l.value.toString,XSDstring)
    }
      
  }

}