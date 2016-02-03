// Copyright (C) 2015 ENSIME Authors
// License: Apache-2.0

import SonatypeSupport._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import sbt._
import sbt.Keys._
import sbt.ScriptedPlugin._
import scalariform.formatter.preferences._
import util.Properties

object EnsimeSbtBuild extends Build {

  override val settings = super.settings ++ Seq(
    organization := "org.ensime",
    version := "0.3.4-SNAPSHOT",
    scalaVersion := "2.10.6",
    ivyLoggingLevel := UpdateLogging.Quiet,
    scalacOptions in Compile ++= Seq(
      "-encoding", "UTF-8", "-target:jvm-1.6", "-feature", "-deprecation",
      "-Xfatal-warnings",
      "-language:postfixOps", "-language:implicitConversions"
    )
  ) ++ sonatype("ensime", "ensime-sbt", BSD3)

  lazy val root = (project in file(".")).
    enablePlugins(SbtScalariform).
    settings(scriptedSettings).
    settings(
      name := "ensime-sbt",
      sbtPlugin := true,
      // intentionally old version of scalariform: do not force an upgrade upon users
      libraryDependencies += "org.scalariform" %% "scalariform" % "0.1.4",
      // scalap needed for :scala-compiler-jars
      libraryDependencies += "org.scala-lang" % "scalap" % scalaVersion.value,
      ScalariformKeys.preferences := FormattingPreferences().setPreference(AlignSingleLineCaseStatements, true),
      scriptedLaunchOpts := Seq(
        "-Dplugin.version=" + version.value,
        // .jvmopts is ignored, simulate here
        "-XX:MaxPermSize=256m", "-Xmx2g", "-Xss2m"
      ),
      scriptedBufferLog := false,
      // WORKAROUND https://github.com/sbt/sbt/issues/2253
      fullResolvers -= Resolver.jcenterRepo
    )

}
