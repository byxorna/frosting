
# --- !Ups

CREATE SEQUENCE host_id_seq;
CREATE TABLE host (
    id integer NOT NULL DEFAULT nextval('host_id_seq'),
    hostname varchar(255) NOT NULL,
    attributes_json text NULL,
    monitored boolean NOT NULL DEFAULT 1
);

# --- !Downs

DROP TABLE host;
DROP SEQUENCE host_id_seq;

