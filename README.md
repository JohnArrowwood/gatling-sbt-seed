# gatling-sbt-seed
### Gatling starter project, which pulls in the gatling-common library

Usage:

* Browse to http://www.github.com/JohnArrowwood/gatling-sbt-seed
* Download as a `.zip` file
* Extract the zip file into an appropriate directory
* Rename the directory to match what you want it to be called
* Modify the `build.sbt` file in the root and set the name, organization, and version
* Run `sbt compile` to make sure it compiles
* Run `sbt gatling-it:testOnly com.example.testApp.ProveItWorks` to see it do something (meaningless)

To learn more about how to build Gatling projects using this seed, read the [docs](src/readme.md).