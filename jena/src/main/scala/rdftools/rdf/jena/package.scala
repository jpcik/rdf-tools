package rdftools.rdf

import org.apache.jena.rdf.model.Resource
import org.apache.jena.rdf.model.{Property=>JenaProperty}
import org.apache.jena.rdf.model.{Literal=>JenaLiteral}
import org.apache.jena.rdf.model.ResourceFactory
import org.apache.jena.graph.{Triple=>JenaTriple}
import org.apache.jena.graph.{Graph=>JenaGraph}
import org.apache.jena.graph.NodeFactory
import org.apache.jena.graph.Node
import language.implicitConversions
import org.apache.jena.graph.BlankNodeId
import org.apache.jena.datatypes.xsd.XSDDatatype
import collection.JavaConverters._
import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.RDFList
import org.apache.jena.rdf.model.Container
import org.apache.jena.rdf.model.Statement
import org.apache.jena.rdf.model.RDFNode
import org.apache.jena.rdf.model.StmtIterator

package object jena {
  //type TripleList = java.util.List[Triple]
  
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
  
  //implicit def prop2Iri(p:JenaProperty):Iri=p.asResource
 
  def iri(n:Node)=Iri(n.getURI)
  
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
  def +=(s:Iri,p:Iri,lit:Literal)(implicit m:Model)={
    m.add(s,p,lit.getString)
  } 
  
  import NodeFactory._
  import XSDDatatype._
  import RdfTools._
  
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
  
   implicit class JenaNodePlus(val jenaNode:Node) extends RdfTerm {
    override def asIri:Iri=jenaNode.getURI
    override def asBnode=bnode(jenaNode.getBlankNodeLabel)
    override def asLiteral=lit(jenaNode.getLiteralValue)
    def asRdfTerm:RdfTerm={
      if (jenaNode.isURI) jenaNode.asIri
      else if (jenaNode.isLiteral) jenaNode.asLiteral
      else if (jenaNode.isBlank) jenaNode.asBnode
      else throw new ClassCastException(s"Cannot cast $this as RdfTerm")
    }
  }
  
  implicit class JenaTriplePlus(jt:JenaTriple) extends Triple {
    lazy val subject=jt.getSubject.asRdfTerm
    lazy val predicate=jt.getPredicate.asIri
    lazy val _object=jt.getObject.asRdfTerm
  }
  
  implicit class JenaGraphPlus(jg:JenaGraph) extends Graph {
    val name=None
    lazy val triples=jg.find(null,null,null).asScala.map(t=>t:Triple).toSet
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
      r.asResource.listProperties(prop).asScala.map{st=>
        st.o
      }
    }
     /*
     override def equals(other:Any)=other match{
       case i:Iri
     }*/

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
    override def equals(other:Any)=other match{
      case i:Iri=>r.isURIResource && i.equals(r.asIri)
      case l:Literal=>r.isLiteral && l.equals(r.asLiteral)
      case b:Bnode=>r.isAnon() && b.equals(r.asBnode)
    }
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
      r.asResource.listProperties(prop).asScala.map{st=>
        st.o
      }
      }
      else Iterator.empty
    }
  }
  
  implicit class JenaStatementPlus(stm:Statement) extends Triple {
    lazy val subject:RdfTerm=stm.getSubject
    lazy val predicate:Iri=stm.getPredicate.getURI
    lazy val _object:RdfTerm=stm.getObject
    
    

  }

  implicit class ModelPlus(m:Model){
    
    private def asTripleItr(it:StmtIterator)=it.asScala.map(stmt=>stmt:Triple)
    def byPredicate(predicate:Iri)={
      asTripleItr(m.listStatements(null,predicate,null))
    }
    def bySubject(iri:Iri)={
      asTripleItr(m.listStatements(iri,null,null))
    }
    
  }
}