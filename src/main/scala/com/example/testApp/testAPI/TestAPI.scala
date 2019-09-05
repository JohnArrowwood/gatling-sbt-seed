package com.example.testApp.testAPI

import com.example.testApp.testAPI.services.PostmanEcho
import com.example.testApp.testAPI.scenarios._

object TestAPI
{
    // The API-level configuration object, for easy reference
    lazy val Config = TestAPIConfig

    // the available services
    lazy val Echo = PostmanEcho

    // the available scenarios
    object scenario 
    {
        val crud   = CRUD
        val get    = OnlyGET
        val put    = OnlyPUT
        val post   = OnlyPOST
        val delete = OnlyDELETE
    }
}