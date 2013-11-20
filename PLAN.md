Plan and Notes
==============

Build a new monitoring solution! Feature set like Icinga, but with improvements!

## Must have features

* Multi master for front end (db backed for shared state?)
* Compatible with NRPE and existing checks (active checks)
* Responsive UI
* HTTP API for integration into external services (add hosts, schedule downtime, query status, etc)
* Scalable by default (uses queues for all checks)
* Supports all features Nagios does
  * Hosts, services, active and passive checks, downtime, muting, escalation paths.
* Configurations changes can be made on a live system using API, no reloads/config checks required
* Integration into external inventory databases and can have other attributes associated with hosts (pools, nodeclass, etc).
  * Attributes should be able to define what services apply to what nodes. (Should this reimplement Collins? Or plug into Collins)
* ??? Support for config file for generating config??? (Not service oriented)
* Support callbacks for easy hooks
  * host built
  * host decommed
  * attributes changed
  * maintenance mode enabled
  * check alerted
* Support native plugins? Is this just a more complicated way to do hooks?
* Can throw probe results into a graphing database? (Records data point, time, and has hook to store data?)
* Service dependencies? (www:de depends on www, dont page for www:de if www is down)
* Leverages existing library of nagios checks
* Some kind of query language (like solr+collins?)

## Design features

Would have to be scalable service that is easily distributed. Scala?
Checks metrics+conditions vs probes (load 3>1.5 vs cluster.rb:cluster down)
Uses queues to do work to stay scalable; keep the frontend very clean and push work into queues
PIPEDREAM: Pluggable storage infrastructure? Mysql or redis or... Sharding capability? Lets address that when we get there

## API

RESTful interaction, should support query language like solr

* Update config with new host
* Update config with new service
* Update asset with new attributes
* Query for assets by attribute
* Get check status
* Get check history?
* Set downtime/maintenance mode on service or host or dependant services





