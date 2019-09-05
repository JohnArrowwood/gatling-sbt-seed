package com.example.testApp

import com.typesafe.config._

import org.arrowwood.gatling.common._

object TestConfig
{
    // Environment-specific configuration files go here: src/main/resources/env/{env}/
    def environmentConfigPath ( env : String ) : String = "env/" + env + "/"
    def environmentConfigPath                  : String = environmentConfigPath( Test.environment )

    // The main config file is "config.conf"
    def environmentConfigFile ( env : String ) : String = environmentConfigPath(env) + "config.conf"
    def environmentConfigFile                  : String = environmentConfigFile( Test.environment )
    
    private lazy val defaultConfig = ConfigFactory.parseResources( "default.conf" )
    private lazy val userConfig    = ConfigFactory.parseResources( "application.conf" )

    // we define a layered config, so that you can define values in one layer, and customize them in another
    def loadConfig ( env : String ) : Config = 
    {
        val environmentConfig = ConfigFactory.parseResources( environmentConfigFile( env ) )
        userConfig
            .withFallback( environmentConfig )
            .withFallback( defaultConfig )
            .resolve()
    }
    def loadConfig : Config = loadConfig( Test.environment )

    lazy val config = loadConfig

    // example of how to get a value from the config and expose it through the Config namespace
    lazy val BaseURL = config.getString( "postman.echo" )
    lazy val rate = config.getConfig("postman.rate")
}