package com.example.testApp.testAPI.services

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import io.gatling.core.body.Body
import io.gatling.http.request.builder.HttpRequestBuilder

import org.arrowwood.gatling.common.rest._

import com.example.testApp.testAPI._

// a service that can actually do something
// so we can actually compile and run something out of the box
object PostmanEcho
{
    def GET = new TestGET( "Postman.GET" )
        {
            def path = "/get"
        }

    def POST = new TestPOST( "Postman.POST") with CustomJson
        {
            def path = "/post"
            json = StringBody( "{}" )
        }

    def FormPOST = new TestFormPOST( "Postman.FormPOST" )
        {
            def path = "/post"
        }

    def PUT = new TestPUT( "Postman.PUT" ) with CustomJson
        {
            def path = "/put"
            json = StringBody( "{}" )
        } 

    def DELETE = new TestDELETE( "Postman.DELETE" )
        {
            def path = "/delete"
        }
}