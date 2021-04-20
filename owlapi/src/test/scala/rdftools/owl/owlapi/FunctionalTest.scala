package rdftools.owl.owlapi

import org.scalatest.flatspec._
import org.scalatest.matchers._
import rdftools.owl.owlapi.OwlApiTools._
import rdftools.owl.owlapi.Functional._
import rdftools.rdf.RdfTools._
import rdftools.rdf.vocab.RDFS
import rdftools.rdf.vocab.OWL
import collection.JavaConverters._
import rdftools.rdf.XsdInteger
import rdftools.rdf.XsdDouble
import rdftools.rdf.XsdFloat
import org.semanticweb.owlapi.vocab.OWLFacet
import rdftools.rdf.XsdInt
import rdftools.rdf.XsdString
import rdftools.rdf.XsdNonNegativeInteger
import rdftools.rdf.XsdNonPositiveInteger
import rdftools.rdf.XsdPositiveInteger

class FunctionalTest extends AnyFlatSpec with should.Matchers{
  
  /** example of ontology from OWL 2 Spec:
  Prefix(:=<http://www.example.com/ontology1#>)
  Ontology( <http://www.example.com/ontology1>
    Import( <http://www.example.com/ontology2> )
    Annotation( rdfs:label "An example" )

    SubClassOf( :Child owl:Thing )
  ) 
  */
  implicit val pref=createPrefixManager
  

  Prefix("a:","http://example.org/")

  val hasAge=dataProperty("a:hasAge")
  val meg=i"a:Meg"

  "ontology" should "be loaded" in {
    
    Prefix(":","http://www.example.com/ontology1#")
    val onto=
      Ontology("http://www.example.com/ontology1",
        Imports ("http://www.example.com/ontology2"),
        Annotation(RDFS.label, lit("An example")),
        SubClassOf (Class(":Child"),OWL.Thing)
      )
    
    onto.getImportsDeclarations.size shouldBe (1)
    onto.getAnnotations.size should be (1)
    onto.getAxiomCount should be (1)
    val subclass=onto.getSubClassAxiomsForSuperClass(OWL.Thing).asScala.head.getSubClass
    subclass.asOWLClass.getIRI.getNamespace shouldBe ("http://www.example.com/ontology1#")
  }
  
  /** example
   *  DataPropertyRange( a:hasAge xsd:integer ) 	The range of the a:hasAge property is xsd:integer.
      DataPropertyAssertion( a:hasAge a:Meg "17"^^xsd:double ) 	Meg is seventeen years old. 
   */
  "data ranges" should "be defined" in {
    DataPropertyRange(hasAge, XsdInteger)
    DataPropertyAssertion(hasAge, meg, "17"^^XsdDouble)
  }
  
  /** example
   *  DataPropertyAssertion( a:numberOfChildren a:Meg "+0"^^xsd:float ) 	The value of a:numberOfChildren for a:Meg is +0.
      DataPropertyAssertion( a:numberOfChildren a:Meg "-0"^^xsd:float ) 	The value of a:numberOfChildren for a:Meg is -0.
      FunctionalDataProperty( a:numberOfChildren ) 	An individual can have at most one value for a:numberOfChildren. 
   */
  "functional data porperty" should "be defined" in {
     val numberOfChildren=dataProperty("a:numberOfChildren")
     DataPropertyAssertion(numberOfChildren, ind"a:Meg", "+0"^^XsdFloat)
     DataPropertyAssertion(numberOfChildren, ind"a:Meg", "-0"^^XsdFloat)
     FunctionalDataProperty(numberOfChildren)
     
     println (numberOfChildren)
  }
  
  "a class" should "be defined" in {
    //SubClassOf( a:Child a:Person ) 	Each child is a person. 
    SubClassOf( c"a:Child", c"a:Person" )
    
  }
  
  "a datatype" should "be defined" in {
    //DataPropertyRange( a:hasAge xsd:integer ) 	The range of the a:hasAge data property is xsd:integer. 
    DataPropertyRange(hasAge, XsdInteger)
  }
  
  "an object property" should "be defined" in {
    //ObjectPropertyAssertion( a:parentOf a:Peter a:Chris ) 	Peter is a parent of Chris. 
    ObjectPropertyAssertion(op"a:parentOf", ind"a:Peter", ind"a:Chris")
    
    val lois=i"a:Lois"
    val peter=ind"a:Peter"
    val hasName=dp"a:hasName"
    // DataPropertyAssertion( a:hasName a:Peter "Peter Griffin" ) 
    DataPropertyAssertion(hasName, peter, lit("Peter Griffin"))
    
    //AnnotationAssertion( rdfs:comment a:Peter "The father of the Griffin family from Quahog." )
    AnnotationAssertion(peter.getIRI, Annotation(RDFS.comment, lit("The father of the Griffin family from Quahog.")))
    
    //ClassAssertion( a:Person a:Peter ) 	Peter is a person. 
    ClassAssertion(c"a:Person", peter)
    
    //ObjectPropertyAssertion( a:livesAt a:Peter _:a1 ) 	Peter lives at some (unknown) address. 
    //ObjectPropertyAssertion( a:city _:a1 a:Quahog ) 	This unknown address is in the city of Quahog and...
    //ObjectPropertyAssertion( a:state _:a1 a:RI ) 	...in the state of Rhode Island. 
    ObjectPropertyAssertion(op"a:livesAt", peter,bnode("a1"))
    ObjectPropertyAssertion(op"a:city", bnode("a1"), ind"a:Quahog")
    ObjectPropertyAssertion(op"a:state", bnode("a1"), ind"a:RI")
    
    //Declaration( Class( a:Person ) )
    //Declaration( NamedIndividual( a:Peter ) ) 
    Declaration(Class("a:Person"))
    Declaration(NamedIndividual("a:Peter"))
    
    //Ontology( <http://www.my.example.com/example>
    //    Declaration( Class( a:Person ) )
    //    Declaration( NamedIndividual( a:Peter ) )
    //    ClassAssertion( a:Person a:Peter )
    //) 
    val Person=Class("a:Person")
    val o=Ontology("http://www.my.example.com/example",
        Declaration(Person) ::
        Declaration(peter) ::
        ClassAssertion(Person, peter)
    )
   
    o.getIndividualsInSignature().size shouldBe (1)
    
    //ClassAssertion( a:Dog a:Brian ) 	Brian is a dog.
    //ClassAssertion( a:Species a:Dog ) 	Dog is a species.  
    ClassAssertion(c"a:Dog", i"a:Brian")
    ClassAssertion(c"a:Species",i"a:Dog")
   
    
    //ClassAssertion( a:Dog a:Brian ) 	Brian is a dog.
    //ClassAssertion( a:PetAnimals a:Dog ) 	Dogs are pet animals.
    //AnnotationAssertion( a:addedBy a:Dog "Seth MacFarlane" ) 	The IRI a:Dog has been added to the ontology by Seth MacFarlane. 
    ClassAssertion(c"a:Dog",i"a:Brian")
    ClassAssertion(c"a:PetAnimals", i"a:Dog")
    
    AnnotationAssertion(ap"a:addedBy", c"a:Dog".getIRI, lit("Seth McFarlane"))
    
     //ObjectInverseOf( a:fatherOf ) 
    val fatherOf=op"a:fatherOf"
    ObjectInverseOf(fatherOf)
    
    // DataIntersectionOf( xsd:nonNegativeInteger xsd:nonPositiveInteger ) 
    DataIntersectionOf(XsdNonNegativeInteger,XsdNonPositiveInteger)
    
   //  DataUnionOf( xsd:string xsd:integer ) 
    DataUnionOf(XsdString,XsdInteger)
    
    // DataComplementOf( xsd:positiveInteger ) 
    DataComplementOf(XsdPositiveInteger)
    
    // DataOneOf( "Peter" "1"^^xsd:integer ) 
    DataOneOf(lit("Peter"),"1"^^XsdInteger)
    import Facets._
    //DatatypeRestriction( xsd:integer xsd:minInclusive "5"^^xsd:integer xsd:maxExclusive "10"^^xsd:integer ) 
    DatatypeRestriction(XsdInteger,  Set(Facets.<=("5"^^XsdInteger),Facets.>=("10"^^XsdInteger)))
    
    
    //ClassAssertion( a:Dog a:Brian ) 	Brian is a dog.
    //ClassAssertion( a:CanTalk a:Brian ) 	Brian can talk.
    //ObjectIntersectionOf( a:Dog a:CanTalk ) 
    val Dog=c"a:Dog"
    val CanTalk=c"a:CanTalk"
    val brian=i"a:Brian"
    ClassAssertion(Dog, brian)
    ClassAssertion(CanTalk,brian)
    ObjectIntersectionOf(Dog,CanTalk)
    
    //ClassAssertion( a:Man a:Peter ) 	Peter is a man.
    //ClassAssertion( a:Woman a:Lois ) 	Lois is a woman.
    //ObjectUnionOf( a:Man a:Woman )
    val Man=c"a:Man"
    val Woman=c"a:Woman"
    ClassAssertion(Man, peter)
    ClassAssertion(Woman, lois)
    ObjectUnionOf(Man,Woman)
    
    //DisjointClasses( a:Man a:Woman ) 	Nothing can be both a man and a woman.
    //ClassAssertion( a:Woman a:Lois ) 	Lois is a woman.
    //ObjectComplementOf( a:Man )
    DisjointClasses(Man,Woman)
    
    
    //ClassAssertion( a:Dog a:Brian ) 	Brian is a dog.
    //ObjectComplementOf( a:Bird ) 
    ClassAssertion(Dog,brian)
    ObjectComplementOf(c"a:Bird")
    
    val stewie=i"a:Stewie"
    val chris=i"a:Chris"
    EquivalentClasses( c"a:GriffinFamilyMember",
      ObjectOneOf( peter,lois,stewie,meg,chris,brian)) 	

    //DifferentIndividuals( a:Quagmire a:Peter a:Lois a:Stewie a:Meg a:Chris a:Brian ) 	Quagmire, Peter, Lois, Stewie, Meg, Chris, and Brian are all different from each other. 
    DifferentIndividuals(peter,lois,stewie,meg,chris,brian)
    
    
   
    //ObjectPropertyAssertion( a:fatherOf a:Peter a:Stewie ) 	Peter is Stewie's father.
    //ClassAssertion( a:Man a:Stewie ) 	Stewie is a man.
    //ObjectSomeValuesFrom( a:fatherOf a:Man )
    ObjectPropertyAssertion(fatherOf, peter, stewie)
    ClassAssertion(Man, stewie)
    ObjectSomeValuesFrom(fatherOf, Man)
    
    val hasPet=op"a:hasPet"
    //ObjectPropertyAssertion( a:hasPet a:Peter a:Brian ) 	Brian is a pet of Peter.
    //ClassAssertion( a:Dog a:Brian ) 	Brian is a dog.
    //ClassAssertion( ObjectMaxCardinality( 1 a:hasPet ) a:Peter ) 	Peter has at most one pet.
    
    //ObjectAllValuesFrom( a:hasPet a:Dog ) 
    ObjectPropertyAssertion(hasPet, peter, brian)
    ClassAssertion(Dog, brian)
    ClassAssertion(ObjectMaxCardinality(1,hasPet),peter)
    ObjectAllValuesFrom(hasPet, Dog)
    
    //ObjectPropertyAssertion( a:fatherOf a:Peter a:Stewie ) 	Peter is Stewie's father.
    //ObjectHasValue( a:fatherOf a:Stewie )
    ObjectPropertyAssertion(fatherOf, peter,stewie)
    ObjectHasValue(fatherOf, stewie)

    val likes=op"a:likes"
    //ObjectPropertyAssertion( a:likes a:Peter a:Peter ) 	Peter likes Peter.
    //ObjectHasSelf( a:likes ) 
    ObjectPropertyAssertion(likes,peter, peter)
    ObjectHasSelf(likes)

    //ObjectPropertyAssertion( a:fatherOf a:Peter a:Stewie ) 	Peter is Stewie's father.
    //ClassAssertion( a:Man a:Stewie ) 	Stewie is a man.
    //ObjectPropertyAssertion( a:fatherOf a:Peter a:Chris ) 	Peter is Chris's father.
    //ClassAssertion( a:Man a:Chris ) 	Chris is a man.
    //DifferentIndividuals( a:Chris a:Stewie ) 	Chris and Stewie are different from each other.
    //ObjectMinCardinality( 2 a:fatherOf a:Man )
    ObjectPropertyAssertion(fatherOf, peter,stewie)
    ClassAssertion(Man, stewie)
    ObjectPropertyAssertion(fatherOf, peter,chris)
    ClassAssertion(Man,chris)
    DifferentIndividuals(chris,stewie)
    ObjectMinCardinality(2,fatherOf)

    //ObjectPropertyAssertion( a:hasPet a:Peter a:Brian ) 	Brian is a pet of Peter.
    //ClassAssertion( ObjectMaxCardinality( 1 a:hasPet ) a:Peter ) 	Peter has at most one pet.
    //ObjectMaxCardinality( 2 a:hasPet )
    ObjectPropertyAssertion(hasPet, peter,brian)
    ClassAssertion(ObjectMaxCardinality(1,hasPet),peter)
    ObjectMaxCardinality(2,hasPet)

    val hasDaughter=op"a:hasDaughter"
    val megan=i"a:Megan"
    //ObjectPropertyAssertion( a:hasDaughter a:Peter a:Meg ) 	Meg is a daughter of Peter.
    //ObjectPropertyAssertion( a:hasDaughter a:Peter a:Megan ) 	Megan is a daughter of Peter.
    //ClassAssertion( ObjectMaxCardinality( 1 a:hasDaughter ) a:Peter ) 	Peter has at most one daughter.
 
    //SameIndividual( a:Meg a:Megan )

    //DifferentIndividuals( a:Peter a:Meg a:Megan ) 	Peter, Meg, and Megan are all different from each other.
    ObjectPropertyAssertion(hasDaughter, peter,meg)
    ObjectPropertyAssertion(hasDaughter, peter,megan)
    ClassAssertion(ObjectMaxCardinality(1, hasDaughter), peter)
    SameIndividual(meg,megan)
    DifferentIndividuals(peter,meg,megan)

    //ObjectPropertyAssertion( a:hasPet a:Peter a:Brian ) 	Brian is a pet of Peter.
    //ClassAssertion( a:Dog a:Brian ) 	Brian is a dog.
    //ClassAssertion(
    //  ObjectAllValuesFrom( a:hasPet
    //   ObjectUnionOf(
      //    ObjectOneOf( a:Brian )
      //    ObjectComplementOf( a:Dog )
      //  )
    //)
    //a:Peter
    // ) 	Each pet of Peter is either Brian or it is not a dog.

    //ObjectExactCardinality( 1 a:hasPet a:Dog )
    ObjectPropertyAssertion(hasPet, peter,brian)
    ClassAssertion(Dog,brian)
    ClassAssertion(
      ObjectAllValuesFrom(hasPet, 
        ObjectUnionOf(
          ObjectOneOf(brian),
          ObjectComplementOf(Dog)
        )
      ),
      peter
    )
    ObjectExactCardinality(1, hasPet)
     
    //DataPropertyAssertion( a:hasAge a:Meg "17"^^xsd:integer ) 	Meg is seventeen years old.
    //DataSomeValuesFrom( a:hasAge DatatypeRestriction( xsd:integer xsd:maxExclusive "20"^^xsd:integer ) )
    DataPropertyAssertion(hasAge, meg, "17"^^XsdInteger)
    DataSomeValuesFrom(hasAge, DatatypeRestriction(XsdInteger, OWLFacet.MAX_EXCLUSIVE, "20"^^XsdInteger))
    
    //DataPropertyAssertion( a:hasZIP _:a1 "02903"^^xsd:integer ) 	The ZIP code of _:a1 is the integer 02903.
    //FunctionalDataProperty( a:hasZIP ) 	Each object can have at most one ZIP code.
    //DataAllValuesFrom( a:hasZIP xsd:integer )
    val hasZip=dp"a:hasZIP"
    DataPropertyAssertion(hasZip, bnode("a1"), "02903"^^XsdInteger)   
    FunctionalDataProperty(hasZip)
    DataAllValuesFrom(hasZip, XsdInteger)
    
    //DataPropertyAssertion( a:hasAge a:Meg "17"^^xsd:integer ) 	Meg is seventeen years old.
    //DataHasValue( a:hasAge "17"^^xsd:integer )
    DataPropertyAssertion(hasAge, meg, "17"^^XsdInteger)
    DataHasValue(hasAge, "17"^^XsdInteger)
    
    //DataPropertyAssertion( a:hasName a:Meg "Meg Griffin" ) 	Meg's name is "Meg Griffin".
    //DataPropertyAssertion( a:hasName a:Meg "Megan Griffin" ) 	Meg's name is "Megan Griffin".
    //DataMinCardinality( 2 a:hasName )
    DataPropertyAssertion(hasName, meg, lit("Meg Griffin"))
    DataPropertyAssertion(hasName, meg, lit("Megan Griffin"))
    DataMinCardinality(2, hasName)
    
    //FunctionalDataProperty( a:hasName ) 	Each object can have at most one name.
    //DataMaxCardinality( 2 a:hasName )
    FunctionalDataProperty(hasName)
    DataMaxCardinality(2, hasName)
    
    //DataPropertyAssertion( a:hasName a:Brian "Brian Griffin" ) 	Brian's name is "Brian Griffin".
    //FunctionalDataProperty( a:hasName ) 	Each object can have at most one name.
    //DataExactCardinality( 1 a:hasName ) 
    DataPropertyAssertion(hasName, brian, lit("Brian Griffin"))
    FunctionalDataProperty(hasName)
    DataExactCardinality(1, hasName)
    
    //SubClassOf( Annotation( rdfs:comment "Male people are people." ) a:Man a:Person )
    //SubClassOf( a:Man a:Person ) 
    SubClassOf(Man, Person, Set(Annotation(RDFS.comment.iri, lit("Male people are people"))))
    SubClassOf(Man,Person)
    
    val Child=c"a:Child"
    val Baby=c"a:Baby"
    //SubClassOf( a:Baby a:Child ) 	Each baby is a child.
    //SubClassOf( a:Child a:Person ) 	Each child is a person.
    //ClassAssertion( a:Baby a:Stewie ) 	Stewie is a baby.
    //SubClassOf( a:Baby a:Person ) 
    SubClassOf(Baby, Child)
    SubClassOf(Child,Person)
    ClassAssertion(Baby, stewie)
    SubClassOf(Baby, Person)
    
    val PersonWithChild=c"a:PersonWithchild"
    val Parent=c"a:Parent"
    val Boy=c"a:Boy"
    val Girl=c"a:Girl"
    val hasChild=op"a:hasChild"
    //SubClassOf( a:PersonWithChild
    //    ObjectSomeValuesFrom( a:hasChild ObjectUnionOf( a:Boy a:Girl ) )
    //) 	A person that has a child has either at least one boy or a girl.
    //SubClassOf( a:Boy a:Child ) 	Each boy is a child.
    //SubClassOf( a:Girl a:Child ) 	Each girl is a child.
    //SubClassOf( ObjectSomeValuesFrom( a:hasChild a:Child ) a:Parent ) 	If some object has a child, then this object is a parent.
    //SubClassOf( a:PersonWithChild a:Parent ) 
    SubClassOf(PersonWithChild, 
        ObjectSomeValuesFrom(hasChild, ObjectUnionOf(Boy,Girl)))
    SubClassOf(Boy, Child)
    SubClassOf(Girl,Child)
    SubClassOf(ObjectSomeValuesFrom(hasChild, Child), Parent)
    SubClassOf(PersonWithChild, Parent)
  
    //EquivalentClasses( a:Boy ObjectIntersectionOf( a:Child a:Man ) ) 	A boy is a male child.
    //ClassAssertion( a:Child a:Chris ) 	Chris is a child.
    //ClassAssertion( a:Man a:Chris ) 	Chris is a man.
    //ClassAssertion( a:Boy a:Stewie ) 	Stewie is a boy.
    EquivalentClasses(Boy, ObjectIntersectionOf(Child,Man))
    ClassAssertion(Child,chris)
    ClassAssertion(Man,chris)
    ClassAssertion(Boy, stewie)

    //EquivalentClasses( a:MongrelOwner ObjectSomeValuesFrom( a:hasPet a:Mongrel ) ) 	A mongrel owner has a pet that is a mongrel.
    //EquivalentClasses( a:DogOwner ObjectSomeValuesFrom( a:hasPet a:Dog ) ) 	A dog owner has a pet that is a dog.
    //SubClassOf( a:Mongrel a:Dog ) 	Each mongrel is a dog.
    //ClassAssertion( a:MongrelOwner a:Peter ) 	Peter is a mongrel owner.
    //SubClassOf( a:MongrelOwner a:DogOwner )
    


    //DisjointClasses( a:Boy a:Girl ) 	Nothing can be both a boy and a girl.
    //ClassAssertion( a:Boy a:Stewie ) 	Stewie is a boy.
    //ObjectComplementOf( a:Girl )
    //ClassAssertion( a:Girl a:Stewie )




    //DisjointUnion( a:Child a:Boy a:Girl ) 	Each child is either a boy or a girl, each boy is a child, each girl is a child, and nothing can be both a boy and a girl.
    //ClassAssertion( a:Child a:Stewie ) 	Stewie is a child.
    //ClassAssertion( ObjectComplementOf( a:Girl ) a:Stewie ) 	Stewie is not a girl. 



    
  }
  
}