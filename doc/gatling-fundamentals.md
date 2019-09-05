# Gatling Fundamentals

## What is What

If you are new to Gatling, it might be helpful to get an explanation of what the different things are that you are going to create.

**Request** - an individual interaction with the back end server, typically an HTTP request.  In terms of Gatling, the **Request* includes the actual request and the validations that are performed once the response is received.

**Flow** - a series of requests (and/or logic) that taken together accomplish a specific operation.

**Scenario** - the description of the flow of a single use case, from start to finish.  A scenario can be composed of just requests or it can re-use flows.

**Simulation** - the model of how the system is used.  Consists of one or more scenarios, each with its own injection profile.

## Injection Profiles

In other tools, like JMeter (last time I checked), you control the load that is generated by specifying the number of concurrent users.  If you want 300 users, then JMeter creates 300 threads, and each thread executes its requests in a loop until the simulation is finished.  

A more realistic usage model is that users arrive every so often, do what they need to do, and then leave.  They may arrive all at once, linearly over time, or with a gaussian distribution across a certain time window.

Gatling supports both approaches.  The recommendation is to use the latter, however, since it is more realistic.

## The Importance of Telemetry

Since the whole point of testing is to answer a question, it's imperative that the test can actually answer the question.  And in most cases, that means analyzing the telemetry of the system.

Telemetry are measurements of a system while it runs.  In a car, telemetry are things like RPM, temperature, and fuel level.  In a software system, it is things like CPU usage, load level, memory used, disk IO, network IO, and anything and everything you can measure.  If it changes over time as the system is used, it should be captured and graphed so you can correlate how it responds as different load profiles are applied.

If you do not already have a telemetry gathing tool, you can explore using [graphite](https://graphiteapp.org)/[grafana](https://grafana.com).  One advantage to using grafana is that Gatling has built-in support for sending its own activity to it, so you can see test run statistics side-by-side with the measurements of the system under test.

Here are some examples of how you analyze telemetry in order to answer questions:

### The Ramp Test

Start out at zero users per second.  Ramp up to N users per second, linearly, over a long duration (at least 30 minutes).  Choose N to be a large enough number that it is more than the system can handle.

When you look at the telemetry, you will see two distinct patterns.  Before the system reaches the saturation point, response times will be relatively flat, even though the number of requests per second is continually growing.

Once the system begins to reach capacity, bandwidth (requests completed per second) will remain noisy but relatively flat, and response times will grow as the request count grows.

The place where the system transitions from one to the other is called the inflection point.

Once you have determined the appximate value of N that represents the inflection point, repeat the test going from 75% to 125% of capacity.  This will allow you to more accurately measure exactly where the inflection point is.

### The Sustain Test

In this test, you quickly (1-5 minutes) ramp up to a value near capacity, and hold it for a long time (30 minutes to 4 hours).

If you are looking for memory leaks or things of that nature, set the sustained traffic rate at 90-95% of capacity.  

If you are trying to identify bottlenecks in the system, load the system up at 110-125%.

### Spotting the Bottleneck

When a system is overloaded, something, somewhere, is working as fast as it can, and it's just not fast enough to keep up.  The goal is to figure out which piece that is, and why.

Any part of the system, including dependent systems, or even the load generation machine, can be the bottleneck.  So you need telemetry across the whole platform as you test, Gatling machine included.

When you find the piece that is the bottleneck, you will see either:

* High/maxed-out CPU
* Low CPU but high IO or Network 

You can often recognize when a system is waiting on a dependent system if you notice that CPU is not very busy but the number of open network connections keeps growing.

There is one other common type of bottleneck that does NOT manifest in the OS-level telemetry.  That is where the system has too small of a thread pool size.  When that happens, the entire thread pool is busy working, so incoming connections get queued up.  The connections get processed in the order in which they were received, and the total time includes the time spent waiting for them to be processed.  When the queue gets too large, connections either start timing out, or the service starts rejecting incoming connections.

In order to spot that condition easily, you want to graph queued incoming connections.  This requires specialized telemetry gathering modules customized to the specific application run-time.  

### Remedies

Obviously, exactly what you need to do depends on how your system is constructed, and exactly what the bottleneck is.  But here are some common issues and how to resolve them.

In the case of a CPU-bound process, the first step is to profile the code while it is under load.  Refactor the code until it is as CPU efficient as possible.  Once it is as efficient as it is going to get, then you have to start throwing more CPUs or machines at it.

If the system is IO or Network bound, again, rule out code inefficiencies.  See if the code is transferring more data than is absolutely necessary to perform its function.  Is it requesting the same data multiple times?  Is it transferring data it doesn't need in order to find the data it does need?  Are you making proper use of indexes?  Are you delegating enough of the logic to the database so you minimize the data transfer?

Once you are as efficient as possible, if you are still IO bound, then you need to look at spreading the load across multiple servers, each responsible for a subset of the data.  This is called sharding, or partitioning.

If you are still Network bound, then first see if you can get the data faster.  Can you cache it locally?  Can you speed up the network interconnect?  Can you reduce the number of hops to the data?  Can you enable protocol compression?

If the problem is that you have too much data to transfer, and one network interface can only transfer so much, then you can look into adding a second.  Either a second card in one machine, or a second machine to help handle the load.

In the case of queued up requests due to CPU starvation, simply increasing the thread pool size should help.  But you have to be careful, and increase it only until you have made optimum use of your available CPU.  To scale beyond that requires adding additional hosts.

## Reproducibility

There are countless reasons why you might get inconsistent results from one request to the next.  

There are some steps you can take to minimize that inconsistency.  For example:

* If your simulation includes multiple scenarios each with a different injection model, use the `randomized` flag to avoid periodic simultaneous injection and keep things a little more smooth.

* Make sure that you are using enough unique test data so that you avoid or minimize caching effects.  Exactly how many that requires depends on your system, but as a general rule, more is better.

* Isolate your test system.  Make sure no one else is using it during your test.  

* If using virtual machines, make sure that the physical hosts are only hosting your system and not anybody else's, lest somebody else's traffic impacts your performance.

Even once you have done all that, there can still be things which are beyond your control that can influence test results.  

The first thing you will want to do in order to deal with that variability is to have long-running (30m - 4h) sustained load tests, so you can have a statistically significant sample size.  

The second is to repeat your tests (preferably on multiple days), and look to see if the results are consistent.  If they are not, it is important to identify what the source of that inconsistency is, and try to control for it.

## The Session

Each virtual user is supposed to be like their own separate individual.  Logic within your scenario often dictates that you store pieces of data extracted in one request and transform and re-transmit that information in a future request.

But all of the virutal users are executing at the same time.  If you actually define Scala variables to hold that information, then that variable is shared between all of the virtual users!  And since you may have hundreds if not thousands of virtual users active all at the same time, each doing its own thing with that variable, the result is chaos, and seldom if ever what you expected!

Instead, Gatling defines something called a **Session**.  This is basically a collection of data, into which you can store whatever you want.  Each Virtual User gets their own Session, so you don't have to worry about different virtual users messing with one another's data.  The Session is just a Map<String,Any>.

The Session variables are identified by strings.  For that reason, a lot of sample Gatling code will show the code referring to the variables as exactly that.  They do this for simplicity, but it is a bad practice.

* It violates the DRY principle
* You lose compile-time checking to make sure you type your variable name correctly
* You can't take advantage of IDE auto-complete
* You can't safely use the IDE to refactor them

Instead, you will want to use the purpose-built SessionVariable class, like so:

```
case object RECORD_ID extends SessionVariable
. . .
.exec( session => {
    session.set( RECORD_ID, some-value )
})
. . .
.exec( 
    http( ... )
    .get( ... + "/" + RECORD_ID.value ) 
)
```

In places where Gatling expects the name of a variable, just use your `VARIABLE` and it will be automatically converted to a string.  Where you want to reference the content of the Session variable, Gatling expects a string of the syntax "${variable}", which is what VARIABLE.value returns.

## Build Time vs. Execution Time

When reading source code, typically the way the CPU will execute the code follows the pattern described by the code.  That's the whole point of programming.

And yet, in Gatling, that's not always the case.  Gatling can sometimes defy our expectations if we don't fully understand what is going on.

This is because virtual users do not execute the scenario code directly.  The scenario class is executed exactly once.  The result is an object whose internals are a black box, but which when Gatling tells that object to do its thing, it will do it.

This gives rise to two execution contexts.  Each behaves a little differently.  And those differences usually mess people up at least once.

The first execution context is object construction time, aka scenario definition.  When your class is being instantiated, its constructor is executed.  At that time, _no requests are being made_.  Instead, the request is being _described_, and the return value of that description is an object which is capable of making that request.

Let me say that again, to get the point across:  When you see code that looks like this:

```
http( ... )
.post( url )
.body( json )
.check( ... )
```

The presence of the leading period indicates that each piece is a method being called on the return value of the previous call.  The return value of that whole expression is an object that knows how to make a request.  BUT NO REQUEST IS MADE!

This is different than most HTTP libraries, which do not return until the request is made.  Gatling doesn't work like that, which is where the gotcha comes from.

The biggest gotcha associated with this behavior is when you define a function to return the values that you use when constructing your requests.  Say, for example, you define a function that returns a timestamp.  If you do this:  

```
http( ... )
.get( url + "?when=" + timestamp() )
```

Our usual way of understanding code would tell us that this code will generate a unique timestamp and append it to the URL.  We assume that every request will have a _different_ timestamp.

But because of this build time vs. execution time, what _actually_ happens is that `timestamp()` is called exactly once, during build time, and every request that is made uses the same value for the timestamp.

If what you want is for your function to be executed every time the scenario reaches that step in the execution, then you want to use the `.exec( Session => Session )` syntax.  In that function (closure), you call your function, and save the result into the session.  Then, in the description of the request, you refer to that session variable.  It might look something like this:

```
case object TIMESTAMP extends SessionVariable
...
.exec( session => session.set( TIMESTAMP, timestamp() ) )
.exec( http(...).get( url + "?when=" + TIMESTAMP.value ) )
```

Taking advantage of some of Scala's syntactic sugar, you can simplify the session variable setting code and just say:

```
.exec( _.set( TIMESTAMP, timestamp() ) )
```

It's up to you which you find easier to understand and follow along with.
