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
* Profiling capability and internal metrics (check duration, latency, etc)

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

# Components

## HTTP

Talks to DB to read/store host, service, chcek information. Talks to results datastore (redis?) to pull check history.
Provides API for clients to modify configuration data.

* POST /api/check/:id/results
  * record results
  * change state of service if necessary
  * enqueue alerts if necessary
  * enqueue callback if necessary
* GET /api/check/:id
  * returns the check command
* POST /api/log/:facility
  * log message

## Queue

Centralize messaging here.

## Clockwatcher

Sends checks into the runqueue when it is appropriate
* pulls check command from API
* enqueue {check:123,host:456,service:789,command:whatever}

## Check Runners

Dequeues jobs to run, enqueues results. Same as a DNX worker. Independent of API availability

## Result consumers

Dequeues results, and takes action on them. This is what will queue up when API is down.

* Pushes results to API
  * API performs state change
  * API enqueues alert if necessary (make sure to record that we asked for alert to be sent)
  * API stores historical data
  * API enqueues callback to callback queue

## Alerter

Reads from alert queue. Builds alert text, sends alert. Depends on API availability?

* Constructs email from templates
* Sends email
* Pushes notice of email to API


## Database

Take a look at [http://docs.icinga.org/latest/en/db_model.html]
