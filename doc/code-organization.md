# Code Organization

When testing against an API, you wil want to organize your reusable code like this:

* `src/main/scala/{domain}`
* * `/{app}/` - the project or application that the API is part of
* * `/{app}/{App}.scala` - The Application Object - namespace provider
* * `/{app}/{api}/` - group code related to a particular API into a common folder
* * `/{app}/{api}/{Api}.scala` - The API object - namespace provider
* * `/{app}/{api}/services/{ApiBaseClass}.scala` - Base class for services in API
* * `/{app}/{api}/services/{Service}.scala` - Class implementing a single endpoint
* * `/{app}/{api}/scenarios/{UseCase}.scala` - reusable request flows, and complete use cases

If testing a web app, it follows a similar pattern, but with different names:

* `src/main/scala/{domain}`
* * `/{app}/`
* * `/{app}/{App}.scala` - application namespace
* * `/{app}/{AppBaseClass}.scala` - for functionality that is common across the whole app
* * `/{app}/{feature}/`
* * `/{app}/{feature}/{Feature}.scala` - feature namespace 
* * `/{app}/{feature}/ui/{FeatureBaseClass}.scala` - code common to all or many screens
* * `/{app}/{feature}/ui/{Screen}.scala` - page object model kind of thing
* * `/{app}/{feature}/scenarios/{UseCase}.scala` - reusable request flows, and complete use cases

The code for actually testing the application goes in 

* `src/it/scala/{domain}/{app}/{Simulation}.scala`

You will find examples of each of the various types of files, as well as documentation on what goes where, in

* `src/it/scala/com/example/*`