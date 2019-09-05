package com.example.testApp

import org.arrowwood.gatling.common.simulation._

// This simulation is controlled entirely by the environment variables
// It will ramp from 0 to GATLING_USERS * GATLING_MULTIPLIER users per second
// over GATLING_RAMP_UP_TIME seconds
// it then sustains that rate for GATLING_DURATION
// ramps back down over GATLING_RAMP_DOWN_TIME
// then waits up to 5 minutes for the last virtual user to finish
// before forcing the simulation to finish

// It expects a ScenarioBuilder object (the result of calling Gatling's
// scenario() method).  You could define the scenario directly here, 
// or as is illustrated below, reference an object defined elsewhere
// (recommended)
class SingleFlow
extends OpenRampTest
{
    def behavior = TestApp.Api.scenario.crud.behavior
}