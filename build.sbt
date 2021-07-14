inThisBuild(List(
  organization := "org.virtuslab",
  homepage := Some(url("https://github.com/VirtusLab/unicorn")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "jsroka",
      "jsroka",
      "jsroka@virtuslab.com",
      url("https://virtuslab.com/")
    )
  )
))

val `unicorn-core` = project
  .settings(Settings.core: _*)
  .settings(
    libraryDependencies ++= Dependencies.core,
    // cannot be higher due to tests not able to reproduce abnormal DB behavior
    coverageMinimum := 100,
    (scalastyleConfig in Test) := file("scalastyle-test-config.xml")
  )

val `unicorn-play` = project
  .settings(Settings.play: _*)
  .settings(
    libraryDependencies ++= Dependencies.core,
    libraryDependencies ++= Dependencies.play,
    coverageMinimum := 100,
    (scalastyleConfig in Test) := file("scalastyle-test-config.xml")
  )
  .dependsOn(`unicorn-core` % Settings.alsoOnTest)

val unicorn = project
  .in(file("."))
  .aggregate(`unicorn-core`, `unicorn-play`)
  .dependsOn(`unicorn-core`, `unicorn-play`)
  .settings(Settings.parent: _*)
  .enablePlugins(ScalaUnidocPlugin)
  .settings(
    name := "unicorn"
  )