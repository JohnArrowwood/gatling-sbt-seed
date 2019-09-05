package com.example.testApp.testAPI

import com.typesafe.config._
import org.arrowwood.gatling.common._

import com.example.testApp.TestConfig

object TestAPIConfig
{
    // load the entire component-specific sub-tree of the config
    lazy val config = TestConfig.config.getConfig("postman")

    // set key values from what is in the config
    lazy val BaseURL = config.getString( "echo" )
    lazy val rate = config.getConfig("rate")
}