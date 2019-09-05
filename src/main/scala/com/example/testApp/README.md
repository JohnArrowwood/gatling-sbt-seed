# The Top Level

An Application.  A Project.  The System.  

Whatever word you choose, this level of the source tree represents the top-level unit that all of the APIs and UIs combine to make up.

This package should contain a sub-package for each API and/or UI that makes up the system.

In addition, if you have any scenarios that exercise the whole system, or otherwise cross component boundaries, it is best to put them in the scenarios folder.

In this example, we have called our system "TestApp".  So the package is `testApp` and the object is `TestApp`.  If your system is called XYZ, then you would call the package `xyz` and the object `Xyz` or `XYZ`.  

The Application Object exists to serve as a namespace, to help make scenarios self-documenting.  It allows you to import the Application Object and then reference sub-objects via that namespace.  You do not HAVE to do that, you can import specific objects and then refer to them by name, or their fully qualified name, like in normal Java/Scala.  I prefer to do it via the Application Object because I believe it makes the code easier to read.  Especially when you are referring to objects from multiple packages in the same source file.

You will also find here `TestConfig`, which uses the value of the GATLING_ENVIRONMENT variable to load a config file, read in key values, and expose them to the source base.

If you have component-specific config options, I suggest adding a component-level object as well, and have that object initialize itself by accessing the configuration loaded in the top level config object.  That way you have clean separation of concerns.  

Alternatively, you can add additional config files, and have the components load a different file than the one loaded by the top-level config.  Do what makes sense for your situation.  Just keep it clean, and don't let it grow into a monster! :)