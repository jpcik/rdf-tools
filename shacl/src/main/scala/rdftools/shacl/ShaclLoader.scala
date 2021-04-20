package rdftools.shacl

import rdftools.rdf.jena.IOTools.ModelIO
import org.apache.jena.riot.RDFDataMgr
import rdftools.rdf.vocab.RDF
import rdftools.rdf.vocab.SHACL
import rdftools.rdf.jena._
import collection.JavaConverters._
import rdftools.rdf.Iri

case class NodeShape(iri:Iri)

object ShaclLoader {

  def load(uri:String)={
    val d=RDFDataMgr.loadDataset(uri)
    val m=d.getDefaultModel()

    val shapes=m.listSubjectsWithProperty(RDF.`type`,SHACL.NodeShape)
    shapes.toList.asScala.map(r=>r.asIri)
  }

}
