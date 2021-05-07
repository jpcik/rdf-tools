package rdftools.rdf

import org.scalatest._
import flatspec._
import matchers._
import OptionValues._
import io.lemonlabs.uri.Url
import io.lemonlabs.uri.Uri

class IriTest extends AnyFlatSpec with should.Matchers  {
  "an absolute IRI" should "be well formed" in{
    val iri = Iri("dfds")
    iri.scheme shouldBe None
    iri.asUrl.fragment shouldBe None
    iri.path should be ("dfds")
    iri.asUrl.toRelativeUrl.toString should be ("dfds")
  }  

  "a relative IRI" should "resolve" in {
    val iri=Iri("g:h")
    iri.scheme should be (Some("g"))
    iri.path should be ("g:h")
  }

  "a full IRI" should "be parsed" in {
    info(""""
      https://john.doe@www.example.com:123/forum/questions/?tag=networking&order=newest#top
      └─┬─┘   └───────────┬──────────────┘└───────┬───────┘ └───────────┬─────────────┘ └┬┘
      scheme          authority                  path                 query           fragment    """)

    val iri=Iri("https://john.doe@www.example.com:123/forum/questions/?tag=networking&order=newest#top")
    iri.scheme.value should be ("https")
    iri.asUrl.hostOption.value.toString should be ("www.example.com")
    iri.asUrl.user.value should be ("john.doe")
    iri.asUrl.port.value should be (123)
    iri.asUrl.fragment.value should be ("top")
  }

  "an ldap iri" should "be parsed" in {
    info("""
      ldap://[2001:db8::7]/c=GB?objectClass?one
      └┬─┘   └─────┬─────┘└─┬─┘ └──────┬──────┘
      scheme   authority   path      query     """)

    val ldap=Iri("ldap://[2001:db8::7]/c=GB?objectClass?one")
    ldap.scheme.value should be ("ldap")
    ldap.asUrl.hostOption.value.toString should be ("[2001:db8::7]")
    ldap.asUrl.query.params should not be empty
  }

  "a malito" should "be parsed" in {
    info("""
      mailto:John.Doe@example.com
      └─┬──┘ └────┬─────────────┘
      scheme     path             """)
    
    val mailto=Iri("mailto:John.Doe@example.com")
    mailto.scheme.value should be ("mailto")
    //mailto.asUrl.user.value should be ("John.Doe")
    mailto.path should be ("mailto:John.Doe@example.com")

  }
/*


  news:comp.infosystems.www.servers.unix
  └┬─┘ └─────────────┬─────────────────┘
  scheme            path

  tel:+1-816-555-1212
  └┬┘ └──────┬──────┘
  scheme    path

  telnet://192.0.2.16:80/
  └─┬──┘   └─────┬─────┘│
  scheme     authority  path

  urn:oasis:names:specification:docbook:dtd:xml:4.1.2
  */
}
