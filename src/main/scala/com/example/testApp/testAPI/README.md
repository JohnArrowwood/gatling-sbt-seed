# The Component Level

The Component might be an API, or a UI (or ...?).  In this example, we are treating it as an API, and we have named our API "TestAPI".  Therefore, the package is `testAPI` and the object is `TestAPI`.

As with the top-level object, the component level object exists to be a namespace, a convenient reference to the individual sub-components.  The top-level object should have a property that refers to this component object.  Similarly, this object will have properties that refer to the individual service objects.  The names in the namespaces do not have to match the package or object names, as you will see in this example code.  Do what makes sense in context.

If you have any component-specific configuration, you would also define a config object, and reference that object in the component object.  See `TestAPIConfig.scala` for an example.

If all of the sub-components share common behavior, you can also declare a base class (or trait) here.  In the case of an API, it is highly recommended that you create a base class whether there is common behavior or not, that way if you ever need to add common behavior, you have a place to put it without having to refactor everything.  See `TestService.scala` for an example/template base class.  There is also the `PagingParameters.scala` for an example of an add-on trait.

As with the top level object, at the component level is another `scenarios` package/folder, to hold scenarios that are limited to interacting with this component. 

Typically, sub-components are defined in single source files.  To keep things cleanly delineated, these have all been put under a `services` folder (or name it as appropriate for your component type).

