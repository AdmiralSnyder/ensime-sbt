addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.4.0")

// really to test the gen-ensime-project code
scalacOptions ++= Seq("-unchecked", "-deprecation")
