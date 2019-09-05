# Use Cases

Software Systems exist to serve the needs of their users.  In all but the simplest cases, the only way to model the load on the system is to model the behavior of individual users, and the way they interact with the system.

## Who, What, When, Where, Why, How

### Who Are Your Users?

Who doesn't use the system?  Why not?

So, who DOES use the system?  

Do all users use it the same way?  If not, what do the users that use it the same have in common?  Is it delineated by job function?  Location?  Time of day?

The goal here is to aid the identification of use cases by thinking about a day in the life of each of your different users, and how they will interact with your system.

### What Do They Use It For?

Describe the high-level interactions that users have with your system.  Which features do they use?  What are they doing with them? 

The goal is to make a list of the specific use cases that have to be modeled.

### When Do They Use It?

What is the general usage pattern?  
* What time does traffic start to pick up?
* What time does it fall off?
* What time is the typical peak hour?

The goal is to know how much traffic has to be generated in order to simulate typical or peak prouction load levels.  Ideally, if at all possible, you would break it down by use case.

### Where Do They Use It?

Where are the users?  Are they all in the same building?  Different buildings in the same city?  Same time zone?  All across the country?

Are they all on the network?  On VPN?  Connecting through the Internet?

The goal is to identify things that might be relevant to system performance, or that might influence the traffic model.

### Why?

Ask yourself, why do users use the system?  Why do some users choose NOT to use the system?

The goal here is to hopefully prompt recognition of additional use cases.

### How Is It Done?

For each use case, capture the low level interactions between the client and server.

Your options are:

* Set up the Gatling Recorder as a proxy for your browser, and then perform the use case manually, and let Gatling generate a scenario that represents the interactions.

* Use Chrome, Dev Tools, Network tab, enable Preserve, and then perform the use case.  Export all the requests as a HAR file, then import that into Gatling to generate a scenario.

* Capture the traffic using Fiddler, and maybe automatically generate a scenario, maybe manually create it step by step

* Capture all network packets using Wireshark, and piece together the sequence.  Not ideal, but if all else fails, this should work.

## Scale

When a new user starts to use the system, they will follow one of the use cases.  But which one?  They are not all equally likely.  Use case A might occur twice as often as use case B.  And use case B might occur ten times as often as use case C.

Your goal is to determine the number of requests per hour for each use case.  

Hopefully this is as easy as finding something that is unique to that use case, and counting how often it occurs during peak times.  

In practice it may be significantly more difficult, either because there is nothing unique to a particular use case, or because the logs do not capture enough information.  In those cases, you have to estimate, perhaps by polling users.  

As a side note, if the logs don't tell you what you need to know, that's a problem that needs to be fixed.