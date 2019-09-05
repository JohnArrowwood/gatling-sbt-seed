# gatling-common

Gatling does a lot of things for you.  But there are some things that are good practices which Gatling does not provide for you out of the box.  Compile time checking for your session variable names, for instance.

In order to support the best practices that I have adopted in my Gatling projects, I have built a library of common code.  You can find the source [here](https://github.com/JohnArrowwood/gatling-common).

This project includes that library as a dependency, so everything described here is available to you here.

The key things provided by the **gatling-common** library are:

* A collection of configuration variables that can all be overridden by setting OS environment variables

* Standardized Simulation templates that reference those configuration variables, so you can get certain common injection profile patterns by simply inheriting from the right base class

* A class to facilitate defining and referring to Session variables in a way that can be validated at compile time, and supports IDE completion and refactoring

* A set of pre-defined Session variables, most of which exist to aid debugging

* A base class to aid in the construction of REST api endpoint abstraction classes, which makes use of the debugging Session variables by default

* Quick and easy, one-line debug dumping of request/response

* A couple of useful objects that can be stored in the Session that return a new value every time they are referrenced
* * One returns a timestamp of the form YYYYMMDDhhmmss
* * The other returns a v4 random GUID
