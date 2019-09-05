# Web App Testing

A Web **Application** is composed of **features**, which are bits of functionality that 
all operate on a particular data type, and features are made up of **pages**, or 
**screens**.  Often, web applications also call an API in the background.  Often, the 
application host serves as proxy to the API server, but sometimes the application will 
call a 3rd-party server directly.

Like an API, user interactions with the web application often have side-effects.  So
it is necessary to model each [use case](./use-cases.md) in a separate scenario.  

When load testing a web application, we are usually concerned with the functionality of 
the back-end services, more than the HTTP server itself.  For that reason, we usually do 
not bother to simulate requests for html templates, css files, static javascript files, images, etc.  