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
  val TTL="TTL"
  import org.apache.jena.graph.NodeFactory._
  import org.apache.jena.datatypes.xsd.XSDDatatype._
  
  def toJenaRes(s:String):Resource=
    ResourceFactory.createResource(s)  

  def toJenaRes(iri:Iri)=
    ResourceFactory.createResource(iri.path)  

  implicit def toJenaNode(clazz:Class)=
    ResourceFactory.createResource(clazz.iri.path)  

  implicit def toIriNode(s:String)=
    NodeFactory.createURI(s)

  implicit def toJenaProperty(iri:Iri):JenaProperty=
    ResourceFactory.createProperty(iri.path)

  implicit def toJenaProperty(prop:Property)=
    ResourceFactory.createProperty(prop.iri.path)

  implicit def toJenaLit(lit:AnyLiteral)=
    ResourceFactory.createPlainLiteral(lit.value.toString)
    
  implicit def toJenaTriple(t:Triple):JenaTriple={
    new JenaTriple(t.subject ,t.predicate ,t._object )
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
    m.add(toJenaRes(s),p:JenaProperty,toJenaRes(o))
  }
  /*
  def +=(s:Iri,p:Iri,o:String)(implicit m:Model)={
    m.add(toJenaRes(s),p,o)
  }*/
  def +=(s:Iri,p:Iri,o:JenaLiteral)(implicit m:Model)={
    m.add(s,p,o)
  }
  def +=(s:Iri,p:Iri,list:RDFList)(implicit m:Model)={
    m.add(s,p,list)
  } 
  def +=(s:Iri,p:Iri,cont:Container)(implicit m:Model)={
    m.add(s,p,cont)
  } 
  
  import JenaGraphs._
  
  def obj(subj:Iri,pred:Iri)(implicit m:Model)={
    m.listObjectsOfProperty(subj, pred).map { node => node.asNode.asRdfTerm }
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
    case jn:JenaNodePlus=>jn.jenaNode 
  }
  object ModelIO{
  def loadToModel(url:String,lang:Lang)={
    //val m=ModelFactory.createDefaultModel
    RDFDataMgr.loadModel(url,lang)
   
  }
  }
  
  implicit class ModelPlus(m:Model) {
    def byPredicate(predIri:Iri)={
      m.listStatements(null,predIri,null).map(stm=>stm:Triple)
    }
    
    
  }
  
  implicit class JenaResourcePlus(r:Resource) extends Iri {
    val path=r.getURI
    override def triple(prop:Iri):Option[Triple]={
      val st=r.getProperty(prop)
      if (st==null) None else Some(st)
    }
       override def obj(prop:Iri):Option[RdfTerm]={
        println("doing obj "+r.getURI)
        println(prop)
        val o=r.asResource.getProperty(prop)
        if (o==null) None else Some(o.getObject)
    }
    override def objs(prop:Iri)={
      r.asResource.listProperties(prop).map{st=>
        st.o
      }
    }

  }
  
  implicit class JenaLiteralPlus(l:JenaLiteral) extends Literal {
    val value=l.getValue
    val datatype:Iri=l.getDatatypeURI
    val langTag:Option[String]=Option(l.getLanguage)
    override def getString={
      println("tombo")
      value.toString
    }
  }

  
  implicit class JenaRdfNodePlus(r:RDFNode) extends RdfTerm {
    override def asIri:Iri=r.asResource
    override def asBnode=bnode(r.asResource.getId.getLabelString)
    override def asLiteral=r.asLiteral
    override def toString=
      if (r.isURIResource) asIri.toString
      else if (r.isLiteral) asLiteral.toString
      else asBnode.toString
    override def obj(prop:Iri):Option[RdfTerm]={
        println("doing obj "+r.asResource.getURI)
        println(prop)
      if (r.isResource) {
        val o=r.asResource.getProperty(prop)
        if (o==null) None else Some(o.getObject)
      }
      else None
    }
    override def objs(prop:Iri)={
      if (r.isResource) {
      r.asResource.listProperties(prop).map{st=>
        st.o
      }
      }
      else Iterator.empty
    }
  }
  
  implicit class JenaStatementPlus(stm:Statement) extends Triple {
    import JenaGraphs._
    lazy val subject:RdfTerm=stm.getSubject
    lazy val predicate:Iri=stm.getPredicate.getURI
    lazy val _object:RdfTerm=stm.getObject
  

  }
  
}