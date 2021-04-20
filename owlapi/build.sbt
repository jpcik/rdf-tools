name := "rdf-tools-owlapi"
  
libraryDependencies ++= Seq(
  "net.sourceforge.owlapi" % "owlapi-api" % "4.0.0",
  "net.sourceforge.owlapi" % "owlapi-apibinding" % "4.0.0",
  "org.scalatest" %% "scalatest" % "3.2.7" % "test",
  "junit" % "junit" % "4.12" % "test"
)

resolvers ++= Seq(
//  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
//  Resolver.sonatypeRepo("public"),
//  "jitpack" at "https://jitpack.io"
)

scalacOptions ++= Seq("-feature","-deprecation")

