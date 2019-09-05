# Integration Test / Resources

This folder contains all of the resources needed when running end-to-end (integration) tests

* `/conf/default.conf` - default values for config items, items that are not environment specific
* `/conf/{env}/` - base directory for storing environment-specific configuration files, etc.
* `/conf/{env}/config.conf` - base configuration for a particular environment
* `/data/` - the base directory that Gatling looks in to find its feeder files
* `/bodies/` - if you use the external request bodies feature of Gatling, you can store them here 
* `/gatling.conf` - customizations to core Gatling behavior
* `/recorder.conf` - place to customize the recorder configuration
* `/logback-test.xml` - place to configure the logging system