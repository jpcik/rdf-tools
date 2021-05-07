name := "rdf-tools-owlapi"
  
libraryDependencies ++= Seq(
  "net.sourceforge.owlapi" % "owlapi-api" % "4.0.0",
  "net.sourceforge.owlapi" % "owlapi-apibinding" % "4.0.0"
)

resolvers ++= Seq(
//  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
//  Resolver.sonatypeRepo("public"),
//  "jitpack" at "https://jitpack.io"
)

scalacOptions ++= Seq("-feature","-deprecation")

