package com.example.testApp.testAPI

// this is an example of how to extend the {API}Service base class
// with additional functionality that only applies to some endpoints
// just mix in this trait on the endpoints where it applies

trait PagingParameters 
extends TestService
{
    def limit  ( n : Int ) : this.type = this.queryParameter( "limit", n )
    def offset ( n : Int ) : this.type = this.queryParameter( "offset", n )
    def page   ( num : Int, size : Int ) : this.type = this.limit( size ).offset( size * ( num-1 ) )
}