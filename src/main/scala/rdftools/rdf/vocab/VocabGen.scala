
package rdftools.rdf.vocab

import org.apache.jena.riot.RDFDataMgr
import collection.JavaConverters._
import collection.JavaConversions._
import org.apache.jena.rdf.model.Statement
import com.typesafe.config.ConfigFactory
import scala.io.Source
import java.io.File
import java.io.FileWriter
import org.apache.jena.rdf.model.Resource
import org.apache.jena.rdf.model.Property
import com.typesafe.config.Config

object VocabGen {  
  
  import treehugger.forest._
  import definitions._
  import treehuggerDSL._
  
  val IriType=TYPE_REF("Iri")
  
  case class VocabConf(name:String,uri:String,pack:String)
  case class VocabMeta(name:String,uri:String,
      classNames:Iterator[String],propertyNames:Iterator[String])
  
  def genTree(conf:VocabConf,meta:VocabMeta)={
    BLOCK(
      IMPORT("rdftools.rdf.Vocab") ,
      IMPORT("rdftools.rdf._") ,
      IMPORT("rdftools.rdf.RdfTools._"),
      OBJECTDEF(conf.name) withParents("Vocab") := BLOCK(
        Seq(VAL("iri",IriType) withFlags(Flags.OVERRIDE) := LIT(conf.uri))++
        meta.classNames.map{r=>
          VAL(r) := REF("clazz") APPLY (LIT(r))
        }   ++
        meta.propertyNames.map{r=>
          VAL(r) := REF("prop") APPLY (LIT(r))
        }    
      ) 
    ) inPackage(conf.pack)
  }
  
  def readVocab(url:String)={
    val m=RDFDataMgr.loadModel(url)
    val shortName=url.split("/").last.toUpperCase.replace("#", "")
    val defPrefix={
      val empty=m.getNsPrefixURI("")
      if (empty==null) url
      else empty
    }
    
    def isLocal(s:Statement)={
      s.getSubject.isURIResource && s.getSubject.getURI.startsWith(defPrefix)
    }
    def toLocalName(stms:Iterator[Statement])=
      stms.filter(isLocal).map(_.getSubject.getLocalName)
    
    import rdftools.rdf.jena._

    val localnames=toLocalName(
      m.listStatements(null,RDF.`type`,RDF.Property).asScala++  
      m.listStatements(null,RDF.`type`,OWL.ObjectProperty).asScala++
      m.listStatements(null,RDF.`type`,OWL.DatatypeProperty).asScala)
    val classnames=toLocalName(
      m.listStatements(null, RDF.`type`, OWL.Class).asScala++
      m.listStatements(null, RDF.`type`,RDFS.Class).asScala)
    
    VocabMeta(shortName,defPrefix,classnames,localnames)
  }
  
  def generateVocabs={  
    val conf=ConfigFactory.load.getConfig("vocabGen")    
    conf.getConfigList("vocabs").asScala.foreach { voc => 
      val meta=readVocab(voc.getString("url"))
      println(s"write vocab: $meta")
      val vocabId=voc.getString("id")
      val pack=voc.getString("package")
      val vconf=VocabConf(vocabId,meta.uri,pack)
      val dir=new File(conf.getString("outputDir"))
      dir.mkdir
      val file=new File(dir+"/"+vocabId+".scala")
      val fw=new FileWriter(file)
      fw.write(treeToString(genTree(vconf,meta)))
      fw.close
      println(s"written vocab: $meta")
  
    }
  
  }
  
  def main(args:Array[String]):Unit={
    generateVocabs  
  }
}

