package com.example.testApp

import org.arrowwood.gatling.common.simulation._

import com.example.testApp.testAPI.scenarios._

// This simulation is controlled MOSTLY by the environment variables
// but the target users per second is controlled by the individual scenarios

// Each object listed in the scenarios list should extend PopulationBehavior
// and define 'behavior(x)' which uses 'perHour(x,rate,behavior)'
// to build an injection profile that follows the standard ramp cycle:
//  * ramp up over GATLING_RAMP_UP_TIME
//  * sustain for GATLING_DURATION
//  * ramp down over GATLING_RAMP_DOWN_TIME

// This simulation class will call the '.behavior(x)' methods of each
// of the provided objects, passing in GATLING_MULTIPLIER.  This allows 
// you to test variations of load levels by only verying the 
// environment variable, no code change required.

// The intention is that you can simulate all of Production traffic
// by defining each of the different use cases as scenarios,
// assigning them an arrival rate that corresponds to peak usage,
// and then building a simulation that combines them all together,
// as shown below

class MultipleFlows
extends MultiScenarioTest
{
    def scenarios = List(
        TestApp.Api.scenario.get,
        TestApp.Api.scenario.post,
        TestApp.Api.scenario.put,
        TestApp.Api.scenario.delete,
        TestApp.Api.scenario.crud
    )
}
