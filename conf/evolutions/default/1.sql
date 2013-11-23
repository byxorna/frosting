
# --- !Ups

CREATE SEQUENCE host_id_seq;
CREATE TABLE host (
    id integer NOT NULL DEFAULT nextval('host_id_seq'),
    hostname varchar(255) NOT NULL,
    attributes_json text NULL,
    monitored boolean NOT NULL DEFAULT 1
);

CREATE SEQUENCE command_id_seq;
CREATE TABLE command (
  id integer NOT NULL DEFAULT nextval('command_id_seq'),
  name varchar(255) NOT NULL,
  command varchar(255) NOT NULL
);

CREATE SEQUENCE service_id_seq;
CREATE TABLE service (
  id integer NOT NULL DEFAULT nextval('service_id_seq'),
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  arguments_json text NULL,
  command_id integer NOT NULL,
  contact_id integer NULL
);

# --- !Downs

DROP TABLE host;
DROP SEQUENCE host_id_seq;

DROP TABLE command;
DROP SEQUENCE command_id_seq;

DROP TABLE service;
DROP SEQUENCE service_id_seq;
