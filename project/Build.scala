import sbt._
import Keys._


object Build extends Build {
  import BuildSettings._
  import Dependencies._

  // configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
  }


lazy val imageTester = Project("image-tester", file("."))
  .settings(exampleSettings: _*)
  .settings(libraryDependencies ++=
    compile(akkaActor, sprayHttp, sprayJson, sprayCan, sprayUtil) ++
    runtime(akkaSlf4j, logback) 
    )



}