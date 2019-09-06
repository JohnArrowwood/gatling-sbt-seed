package com.example.testApp.testAPI

import io.gatling.core.Predef._
import io.gatling.core.session.Expression

import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

import org.arrowwood.gatling.common.rest._

import com.example.testApp.TestApp

// This is an example of a base class extending RESTfulService

// Use this file as a starting point, and then tweak:
//  * change "Test" to whatever your API naming convention is
//  * change url to reference the base URL as defined in YOUR config object
//  * if necessary, override '.modify()' to add custom headers or whatever

trait TestService
extends RESTfulService
{
    def path : String
    def url : Expression[String] = TestApp.Api.Config.BaseURL + path

    // If you needed to universally modify the generated requests,
    // you would uncomment this and tweak it as necessary
    // override def modify ( request : HttpRequestBuilder ) : HttpRequestBuilder = request
}

abstract class TestGET( desc : String )
extends TestService
with RESTfulGET
{
    val description = desc
}

abstract class TestPUT( desc : String )
extends TestService
with RESTfulPUT
{
    val description = desc
}

abstract class TestPOST( desc : String )
extends TestService
with RESTfulPOST
{
    val description = desc
}

abstract class TestFormPOST( desc : String )
extends TestService
with RESTfulFormPOST
{
    val description = desc
}

abstract class TestDELETE( desc : String )
extends TestService
with RESTfulDELETE
{
    val description = desc
}

abstract class TestHEAD( desc : String )
extends TestService
with RESTfulHEAD
{
    val description = desc
}
