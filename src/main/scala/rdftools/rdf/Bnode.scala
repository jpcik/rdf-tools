package rdftools.rdf

trait Bnode extends RdfTerm {
  val id:String
  override def asBnode=this  
  override def toString=s"_:$id"
}

case class IdBnode(id:String) extends Bnode{
}
