# Component Scenarios

Unless you have a very good reason not to, every scenario is defined as an object that extends two base traits: `UserBehavior`, and `PopulationBehavior`.

`UserBehavior` requires that the object define a `.behavior` property.  That property returns the Gatling scenario for the behavior of a single user.

`PopulationBehavior` defines `.beahvior(x)`, which returns the behavior with an **Injection Profile** added.  The object is expected to know (or obtain from its config) how many requests per hour is typical during peak hours in the production system, and define the injection profile accordingly.  As seen in the examples, the standard way of doing this is to call `perHour(x,rate,behavior)`.

Note that PopulationBehavior is intended for use with Open Injection Model simulations.  That is, where users are added to the simulation every so often, the users do what they are meant to do, and then exit.  In a Closed Injection Model, users engage in an endless (or very long-lived) loop. You add so many users, and then those users do what they do for the life of the simulation.  This is seldom the way real-world systems behave, however, which is why `PopulationBehavior` and `MultiScenarioTest` does not support that model.

There is a valid use case for the closed model, however.  If you are using Gatling to drive a parallelized utility process, the closed model is ideal.  Each time through the loop, you get the next piece of data, do what you need to do with it, and move on.  The Closed Model allows you to tightly control how much load your scenario puts on the server, without respect to how hard the process is making the system work.  With an open model, if you did not inject users fast enough, you have wasted compute cycles.  If you inject them too fast, the system will queue up and eventually be overloaded.  The closed model solves that problem.