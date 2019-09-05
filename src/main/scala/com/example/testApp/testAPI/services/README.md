# Sub-Components

In this case, the component is an API, so the sub-components are services.  

If the component was a UI, the sub-components might be features or screens.

Each object defined here contains within it one method for each kind of operation or interaction that is possible with that sub-component.  For example, if the sub-component is a RESTful API, then it might contain a method for each of the HTTP methods supported by the API.  In the example, the methods were named after the HTTP methods (GET, POST, PUT, DELETE).  This need not be the case, however.  If the API is a CRUD interface, you might name your methods after the operation performed - create, retrieve, list, update, delete.

Sometimes, you need to specify a version when interacting with an API.  And often, when testing, you need to test multiple versions of that API at the same time.  Included are two examples of service definitions that handle the Version issue, each in their own way, according to the needs of the service in question.  It is strongly suggested that you follow the versioning pattern when constructing your Service abstraction, so that when (not if) a new version of a service is added, you have a pattern to follow to accommodate it.