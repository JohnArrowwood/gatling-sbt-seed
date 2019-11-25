package com.example.testApp.testAPI.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.PopulationBuilder

import org.arrowwood.gatling.common.simulation._

import com.example.testApp.TestApp
import com.example.testApp.testAPI.services.PostmanEcho

// This object defines the behavior of a single use case / scenario

// It extends UserBehavior, which means the scenario is defined by '.behavior'
// This is not critical, it is just a nice convention to follow, and makes 
// the intent clear

// Notice that the scenario defines a top-level '.group()'
// This is to ensure that the reporting for the requests of this scenario
// are separate from the same requests that are part of different scenarios

// The Object also extends PopulationBehavior, which means that you obtain the 
// scenario with a pre-defined injection profile calculated to match production 
// levels by calling '.behavior(x)', where x is a multiplier.  

// Simulations that extend MultiScenarioTest will take care of calling 
// '.behavior(x)' automatically, and passes Test.multiplier, or the value of 
// GATLING_MULTIPLIER environment variable, as the value of x.

// There is also no requirement that this object only define one scenario.
// If you have several closely related use cases, you could define them all
// here and then have '.behavior(x)' return all of them, which is why
// '.behavior(x)' returns a List.

// This code also illustrates delegating the definition of production-level
// user injection rate to the config file.  This allows you to create an
// "application.conf" in your classpath, and override some or all of the
// rates as you see fit.  You might use this if you wanted to experiment
// with increasing only one of the use cases, while leaving the rest alone

// Note that the preferred way of adjusting all of the rates uniformly
// is to set the GATLING_MULTIPLIER environment to something other than 1.0
// For example, setting it to 0.5 will cause the simulation to generate
// half as much traffic.  Setting it to 2.0 will cause it to generate
// twice as much traffic.

object CRUD
extends UserBehavior
with PopulationBehavior
{
    val name = "Postman CRUD"
    val PER_HOUR = TestApp.Api.Config.rate.getInt( "crud" )

    // define the behavior of a single user - the Scenario
    def behavior = 
        scenario( name )
        .group( name ) {
             exec( PostmanEcho.POST.request )
            .exec( PostmanEcho.GET.request )
            .exec( PostmanEcho.PUT.request )
            .exec( PostmanEcho.DELETE.request )
        }

    // define the behavior of this population of users - the Scenario with an Injection Profile
    def behavior( x : Double ) : List[PopulationBuilder] = List(
        perHour( x, PER_HOUR )( behavior )
    ) 

}