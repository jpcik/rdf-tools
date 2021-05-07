name := "rdf-tools-shacl"
  
libraryDependencies ++= Seq(
  "org.codehaus.groovy" % "groovy" % "3.0.7" % Test
)

resolvers ++= Seq(
//  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
//  Resolver.sonatypeRepo("public"),
//  "jitpack" at "https://jitpack.io"
)

scalacOptions ++= Seq("-feature","-deprecation")

