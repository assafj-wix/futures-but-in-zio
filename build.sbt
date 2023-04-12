ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.wix.katas.zio"
ThisBuild / organizationName := "Wix"

lazy val root = (project in file("."))
  .settings(
    name := "futures-but-in-zio",
    libraryDependencies ++=
      Seq(
        "dev.zio" %% "zio"               % "2.0.11",
        "dev.zio" %% "zio-test"          % "2.0.11" % Test,
        "dev.zio" %% "zio-test-sbt"      % "2.0.11" % Test,
        "dev.zio" %% "zio-test-magnolia" % "2.0.11" % Test
      ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
