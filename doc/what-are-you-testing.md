# What Are You Testing?

The _oft-stated_ goal of performance testing is to be able to simulate the load that the production system does or may experience. 

The _actual_ goal of all testing, not just performance, stress, or load testing, is to
**answer a question**.  

But what question?  Because the question matters.  

## Bad Questions

Take for example the question: "If I push this system to Production, will it hold up?"

To answer that question in Gatling is a virtually impossible task for any reasonably 
complex system.  You would have to simulate everything that every user has ever done, or 
will ever do in the future.  You can't actually PROVE it will hold up!  And if you did 
prove that it can't hold up, now you still have a ton of work to do to find out why not, 
and what to do about it.

## Good Questions

Flip the question around and instead ask what might stop the system from holding up:
"What's the weakest link in the system, and what can we do to make it stronger?"  

If you repeatedly ask that question and apply the answer, the end result will be a 
system that can be scaled to meet both present and future demand.  You won't just 
(indirectly) answer the first question, you'll ensure that the answer is yes!

Here are some examples of good questions to ask:

* What are the weak points / bottlenecks in the system as it is now?
* What are the effects of manipulating this (one|set of) tuning parameters?
* How do two different configurations or implementations compare to one another?

## Get Everyone on the Same Page

Before you begin a new Gatling project, start by asking, "what is the question that this 
project is going to answer?"  Ensure that the question is not a simple yes/no question, 
but one where the answer provides actionable intelligence on how to make the system 
faster, stronger, more efficient, resilient, etc.
