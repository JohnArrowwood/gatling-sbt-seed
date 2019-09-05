# API Testing

An Application Programming Interface (**API**), especially a REST API, is a collection of related **services**.  Each Service conists of one or more **methods**.

The service is typically invoked by making an HTTP request to the Service URL, with a 
particular HTTP method.  The method determines what kind of operation you want to 
perform:

* GET - retrieving existing data
* POST - creating new data
* PUT - update existing data
* DELETE - remove or invalidate data

The services method takes its parameters from the URL path, from request parameters (the 
key/value pairs after the question mark in the url), or in some cases, from the request 
body.  Note that the service never looks at the Anchor portion of the URL (the part 
after the pound sign).  By convention, browsers strip off the anchor before sending the 
request, so the server never sees that portion of the URL.

When testing an API, Gatling needs to make the same calls that a client would make.  It 
can be tempting to try to take a short-cut, like just calling methods randomly, or 
replaying production traffic.  But because API interactions usually have side-effects,
that won't typically work.  If you try to update a record that doesn't exist, it will be
an invalid test.

When writing your Gatling test, you will need to identify all the different use cases,
and write a scenario to simulate each one.
