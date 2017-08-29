package rdftools.rdf

trait Variable extends RdfTerm{
  val varName:String
  override def toString=s"?$varName"
}

case class NodeVariable(varName:String) extends Variable

object nodeVar {
  def apply(varName:String)=NodeVariable(varName)
}


