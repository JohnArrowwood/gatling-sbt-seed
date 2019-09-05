# Coding Principles

## Don't Repeat Yourself (DRY)

In a production simulation, there will be copious amounts of duplication within the code.  URLs, headers, requests, flows, and even validations.

In keeping with the DRY principle, if something needs to change, there should be exactly one place in the code base to change it.

## Self Documenting Code

In Gatling, a single request can be as much as a dozen lines of code.  

```
.exec(
    http( "description / report category" )
    .get( url )
    .requestParam( key, value )
    .requestParam( key2, value2 )
    .header( header1, value )
    .header( header2, value )
    .header( header3, value )
    .check( ... )
    .check( ... )
    .check( ... )
)
```

String several of them together, and soon it becomes difficult to understand the intent of the code.  As the saying goes, "I can't see the forest for the trees!"

Besides violating the DRY principle, code written like that is hard to read.  

Instead, every request should be assigned to a variable, and the flows constructed by referencing the variables.  The resulting code becomes one line per request, making it much easier to understand what is happening, and why.

```
object theAPI {
    object serviceA {
        object endpointX {
            val GET = http( ... )
            val POST = http( ... )
            val PUT = http( ... )
            val DELETE = http( ... )
        }
        object endpointY { ... }
        object endpointZ { ... }
    }
    object ServiceB { ... }
}
object thisFlow {
    val getSuchAndSuch = 
        theAPI.serviceA.endpointX.GET
        .check( ... )

    val doThis = ...
    val doThat = ...
}

def behavior = 
    Scenario( "description" )
    ...
    .exec( thisFlow.doThis )
    .exec( thisFlow.doThat )
    ...
```

Of course, you wouldn't put all of this in the same file.  See [Code Organization](./code-organization.md) for a description of what to put where.