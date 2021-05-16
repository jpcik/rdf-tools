name := "rdf-tools-r2rml"
  
libraryDependencies ++= Seq(
  "org.codehaus.groovy" % "groovy" % "3.0.7" % Test,
  "com.github.tototoshi" %% "scala-csv" % "1.3.7"

)

resolvers ++= Seq(
//  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
//  Resolver.sonatypeRepo("public"),
//  "jitpack" at "https://jitpack.io"
)

scalacOptions ++= Seq("-feature","-deprecation")

