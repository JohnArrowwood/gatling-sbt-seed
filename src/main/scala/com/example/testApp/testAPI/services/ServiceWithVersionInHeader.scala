package com.example.testApp.testAPI.services

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import io.gatling.core.body.Body
import io.gatling.http.request.builder.HttpRequestBuilder

import org.arrowwood.gatling.common.rest._
import org.arrowwood.gatling.common.session._

import com.example.testApp.testAPI._

// Example of a service where there are version numbers in the URL
// and how to deal with mixed version numbers within a service
object ServiceWithVersionInHeader
{
    // we pass parameters into these methods through session variables
    // WARNING: there is no Session variable name scoping,
    // so either define all your SessionVariables in one file (preferred), or
    // make darned sure that they all have a unique name
    case object RECORD_ID extends SessionVariable

    // Because each service/method may be on a different version, and we want 
    // to support executing all versions concurrently, we can not just modify
    // the base class, we have to override the .request method
    // we do NOT want to override the .modify method, lest we lose

    // this is just one example of how to make this work.  
    // There may be other, maybe even cleaner, ways.

    // Update this to be whatever header the service is actually looking for
    def useVersion( s : String ) : HttpRequestBuilder => HttpRequestBuilder =
        request => request.header( "X-Service-Version", s )

    // update this to use the actual version string
    val make_v1 = useVersion("v1")
    val make_v2 = useVersion("v2")


    object v1 {

        def POST = new TestPOST( "Service.v1.POST") with CustomJson
            {
                def path = "/service"
                
                json = StringBody( "{}" )

                // Because the change is not being made in the base class, we have to override .request
                // and inject the version header onto the request
                // in addition to any other processing we would otherwise have had to do
                override def request = 
                    make_v1( super.request )
                    .check( jsonPath("$.id").optional.saveAs( RECORD_ID ) )

                // could have just as easily overridden .modify() as well
                // do what makes the most sense to you
            }
        def GET = new TestGET( "Service.v1.GET" ) with IdSetter
            {
                // we will use the Id stored in the session, unless the scenario specifies otherwise
                // adding "with IdSetter" means the scenario will be able to change it by calling .id( value )
                var id : String = RECORD_ID.value

                // construct the path using the id - note that by default ID contains an expression, "${RECORD_ID}"
                def path = "/service/" + id

                // make sure the request includes the version header
                override def request = make_v1( super.request )
            }
        def PUT = new TestPUT( "Service.v1.PUT" ) with IdSetter with CustomJson
            {
                var id : String = RECORD_ID.value
                def path = "/service/" + id

                json = StringBody( "{}" )

                override def request = make_v1( super.request )
            }
        def DELETE = new TestDELETE( "Service.v1.DELETE" ) with IdSetter
            {
                var id : String = RECORD_ID.value
                def path = "/service/" + id

                override def request = make_v1( super.request )
            }
    }

    object v2 {
        def GET = new TestGET( "Service.v2.GET" ) with PagingParameters
            {
                var id : String = RECORD_ID.value
                def path = "/service/" + id

                override def request = make_v2( super.request )
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