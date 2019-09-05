package com.example.testApp.testAPI.services

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.body.Body

import org.arrowwood.gatling.common.rest._
import org.arrowwood.gatling.common.session._

import com.example.testApp.testAPI._

// Example of a service where there are version numbers in the URL
// and how to deal with mixed version numbers within a service
object ServiceWithVersionInPath 
{
    // we pass parameters into these methods through session variables
    // WARNING: there is no Session variable name scoping,
    // so either define all your SessionVariables in one file (preferred), or
    // make darned sure that they all have a unique name
    case object RECORD_ID extends SessionVariable

    object v1 {
        def POST = new TestPOST( "Service.v1.POST") with CustomJson
            {
                def path = "/v1/service"
                
                json = StringBody( "{}" )

                // example of how to add a custom check to the base request
                override def request = 
                    super.request
                    .check( jsonPath("$.id").optional.saveAs( RECORD_ID ) )
            }
        def GET = new TestGET( "Service.v1.GET" ) with IdSetter
            {
                // we will use the Id stored in the session, unless the scenario specifies otherwise
                var id : String = RECORD_ID.value
                def path = "/v1/service/" + id
            }
        def PUT = new TestPUT( "Service.v1.PUT" ) with IdSetter with CustomJson
            {
                var id : String = RECORD_ID.value
                def path = "/v1/service/" + id

                json = StringBody( "{}" )
            }
        def DELETE = new TestDELETE( "Service.v1.DELETE" ) with IdSetter
            {
                var id : String = RECORD_ID.value
                def path = "/v1/service/" + id
            }
    }

    object v2 {
        def GET = new TestGET( "Service.v2.GET" ) with PagingParameters
            {
                var id : String = RECORD_ID.value
                def path = "/v2/service/" + id
            }
    }

    // we define the "current" version once, so the whole file can be updated at once by making a single change
    // and to make our intent clear, namely that you are accessing the current version, whatever that is
    val currentVersion = v1

    // point the versionless methods at the latest available method
    def POST   = currentVersion.POST
    def PUT    = currentVersion.PUT
    def DELETE = currentVersion.DELETE

    // override the one that is not on the same version as everyone else
    def GET = v2.GET
}