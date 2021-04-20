name := "rdf-tools-shacl"
  
libraryDependencies ++= Seq(
  "org.codehaus.groovy" % "groovy" % "3.0.7" % Test,
  "org.scalatest" %% "scalatest" % "3.2.7" % Test,
  "junit" % "junit" % "4.12" % Test
)

resolvers ++= Seq(
//  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
//  Resolver.sonatypeRepo("public"),
//  "jitpack" at "https://jitpack.io"
)

scalacOptions ++= Seq("-feature","-deprecation")

