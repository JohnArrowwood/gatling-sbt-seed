package com.example.testApp.testAPI.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.PopulationBuilder

import org.arrowwood.gatling.common.simulation._

import com.example.testApp.TestApp
import com.example.testApp.testAPI.services.PostmanEcho

object OnlyPOST
extends UserBehavior
with PopulationBehavior
{
    val name = "Postman POST"
    val PER_HOUR = TestApp.Api.Config.rate.getInt( "post" )

    // define the behavior of a single user - the Scenario
    def behavior = 
        scenario( name )
        .group( name ) {
            exec( PostmanEcho.POST.request )
        }

    // define the behavior of this population of users - the Scenario with an Injection Profile
    def behavior( x : Double ) : List[PopulationBuilder] = List(
        perHour( x, PER_HOUR )( behavior )
    ) 
}