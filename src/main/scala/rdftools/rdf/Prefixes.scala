package rdftools.rdf

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap


case class Prefix(prefix:String,uri:String)

class Prefixes {

    protected val prefixToURI =new HashMap[String, String] ;
    protected  val URItoPrefix= new HashMap[String, String] ;
    protected var locked=false;
    
    
    protected def set( prefix:String,  uri:String):Unit= {
        prefixToURI.put(prefix, uri) ;
        URItoPrefix.put(uri, prefix) ;
    }

    protected def get( prefix:String):String= {
        return prefixToURI(prefix) ;
    }

    protected def remove( prefix:String):Unit= {
        prefixToURI.remove(prefix) ;
    }

   // @Override
    def  lock()=
        { 
        locked = true; 
         this;
        }
        
 //   @Override
    def setNsPrefix(  prefix:String,  uri:String ) 
        {
        checkUnlocked();
        checkLegal( prefix );
        if (!prefix.equals( "" )) checkProper( uri );
        if (uri == null) throw new NullPointerException( "null URIs are prohibited as arguments to setNsPrefix" );
        set( prefix, uri );
         this;
        }
    
 //   @Override
    def removeNsPrefix(  prefix:String )
        {
        checkUnlocked();
        remove(prefix);
        regenerateReverseMapping() ;
         this;
        }
    
   // @Override
    def clearNsPrefixMap() {
        checkUnlocked();
        // Do by calling down via the interception point remove(prefix).
        // Copy prefixes to avoid possible concurrent modification exceptions
        val prefixes = (prefixToURI.keySet) ;
        prefixes.foreach(p=>remove(p));
        regenerateReverseMapping() ;
         this ;
    }
    
    protected def regenerateReverseMapping():Unit=
        {
        URItoPrefix.clear();
        prefixToURI.foreach(kv=>URItoPrefix.put(kv._2,kv._1))

        }
        
    protected def checkUnlocked()=
        { if (locked) throw new Exception( "locked" ); }
        
    private def checkProper( uri:String )=
        { 
        // suppressed by popular demand. TODO consider optionality
        // if (!isNiceURI( uri )) throw new NamespaceEndsWithNameCharException( uri );
        }
        /*
    public static boolean isNiceURI( String uri )
        {
        if (uri.equals( "" )) return false;
        char last = uri.charAt( uri.length() - 1 );
        return Util.notNameChar( last ); 
        }
        */
    /**
        Add the bindings of other to our own. We defer to the general case 
        because we have to ensure the URIs are checked.
        
        @param other the PrefixMapping whose bindings we are to add to this.
    */
   /* @Override
    public PrefixMapping setNsPrefixes( PrefixMapping other )
        { return setNsPrefixes( other.getNsPrefixMap() ); }
    */
    /**
         Answer this PrefixMapping after updating it with the <code>(p, u)</code> 
         mappings in <code>other</code> where neither <code>p</code> nor
         <code>u</code> appear in this mapping.
    */
   /* @Override
    public PrefixMapping withDefaultMappings( PrefixMapping other )
        {
        checkUnlocked();
        for (Entry<String, String> e: other.getNsPrefixMap().entrySet())
            {
            String prefix = e.getKey(), uri = e.getValue();
            if (getNsPrefixURI( prefix ) == null && getNsURIPrefix( uri ) == null)
                setNsPrefix( prefix, uri );
            }
        return this;
        }
     */   
    /**
        Add the bindings in the prefixToURI to our own. This will fail with a ClassCastException
        if any key or value is not a String; we make no guarantees about order or
        completeness if this happens. It will fail with an IllegalPrefixException if
        any prefix is illegal; similar provisos apply.
        
         @param other the Map whose bindings we are to add to this.
    */
 /*   @Override
    public PrefixMapping setNsPrefixes( Map<String, String> other )
        {
        checkUnlocked();
        for (Entry<String, String> e: other.entrySet())
            setNsPrefix( e.getKey(), e.getValue() );
        return this;
        }
   */      
    /**
        Checks that a prefix is "legal" - it must be a valid XML NCName.
    */
    private def checkLegal(  prefix:String )
        {
      //  if (prefix.length() > 0 && !XMLChar.isValidNCName( prefix ))
      //      throw new PrefixMapping.IllegalPrefixException( prefix ); 
        }
       
   // @Override
   def getNsPrefixURI(  prefix:String ) =
        {  get( prefix ); }
        
  //  @Override
     def getNsPrefixMap()=
        {   prefixToURI ; }
        
   // @Override
    def getNsURIPrefix(  uri :String)=
        {  URItoPrefix( uri ); }
       
    /**
        Expand a prefixed URI. There's an assumption that any URI of the form
        Head:Tail is subject to mapping if Head is in the prefix mapping. So, if
        someone takes it into their heads to define eg "http" or "ftp" we have problems.
    */
//    @Override
    def expandPrefix(  prefixed:String )=
        {
        val colon = prefixed.indexOf( ':' );
        if (colon < 0) 
             prefixed;
        else
            {
            val uri = prefixToURI.get( prefixed.substring( 0, colon ) );
            if (uri.isEmpty) prefixed
            else 
             uri + prefixed.substring( colon + 1 );
            } 
        }
       
    /**
        Answer a readable (we hope) representation of this prefix mapping.
    */
 //   @Override
    override def toString()=
        {  "pm:" + prefixToURI; }
        
    /**
        Answer the qname for <code>uri</code> which uses a prefix from this
        mapping, or null if there isn't one.
    <p>
        Relies on <code>splitNamespace</code> to carve uri into namespace and
        localname components; this ensures that the localname is legal and we just
        have to (reverse-)lookup the namespace in the prefix table.
        
     	@see org.apache.jena.shared.PrefixMapping#qnameFor(java.lang.String)
    */
  /*  @Override
    public String qnameFor( String uri )
        { 
        int split = Util.splitNamespaceXML( uri );
        String ns = uri.substring( 0, split ), local = uri.substring( split );
        if (local.equals( "" )) return null;
        String prefix = URItoPrefix.get( ns );
        return prefix == null ? null : prefix + ":" + local;
        }
    */
    /**
        Compress the URI using the prefix mapping. This version of the code looks
        through all the maplets and checks each candidate prefix URI for being a
        leading substring of the argument URI. There's probably a much more
        efficient algorithm available, preprocessing the prefix strings into some
        kind of search table, but for the moment we don't need it.
    */
  //  @Override
    def  shortForm(  uri:String )=
        {
      
        val e = findMapping( uri, true );
        if (e.isDefined) e.get._1+":" + uri.substring( (e.get._2).length )
        else uri
        }
        
//    @Override
 /*    def samePrefixMappingAs(  other:Prefixes )=
        {
        return other instanceof PrefixMappingImpl 
            ? equals( (PrefixMappingImpl) other )
            : equalsByMap( other )
            ;
        }
   */ 
 //   @Override
     def hasNoMappings() =
        {  prefixToURI.isEmpty; }

 //   @Override
     def numPrefixes()=
        {  prefixToURI.size; }
    
    protected def equals(  other:Prefixes )=
        {  other.sameAs( this ); }
    
    protected def sameAs(  other:Prefixes )=
        {  prefixToURI.equals( other.prefixToURI ); }
    
    protected def equalsByMap( other:Prefixes )=
        {  getNsPrefixMap().equals( other.getNsPrefixMap() ); }
    
    /**
        Answer a prefixToURI entry in which the value is an initial substring of <code>uri</code>.
        If <code>partial</code> is false, then the value must equal <code>uri</code>.
        
        Does a linear search of the entire prefixToURI, so not terribly efficient for large maps.
        
        @param uri the value to search for
        @param partial true if the match can be any leading substring, false for exact match
        @return some entry (k, v) such that uri starts with v [equal for partial=false]
    */
    private def findMapping( uri:String, partial:Boolean )={
      prefixToURI.find{case (k,v) => 
        uri.startsWith(v) && (partial || v.length==uri.length)
      }
    } 

}