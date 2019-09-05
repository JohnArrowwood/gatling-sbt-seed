package com.example.testApp

import org.arrowwood.gatling.common.simulation.UnitTest

// This simulation is intended to be used with a non-looping flow
// It injects only one virtual user, and when that user finishes
// doing its thing, the simulation exits.

// This allows one to do functional testing (of either the application,
// or of the scenario code) using Gatling, and not have to mess around
// with injection profiles

class ProveItWorks
extends UnitTest
{
    def behavior = TestApp.Api.scenario.crud.behavior
}