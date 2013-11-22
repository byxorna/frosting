package models


/**
service states:
http://nagios.sourceforge.net/docs/3_0/statetypes.html
OK
WARNING
CRITICAL
UNKNOWN

host states:
http://nagios.sourceforge.net/docs/3_0/hostchecks.html
up - up
down - at least one parent is up
unreachable - parents are either down or unreachable
**/


object HostState extends Enumeration {
  type HostState = Value
  val Up, Down, Unreachable = Value
}

object State extends Enumeration {
  type State = Value
  val Ok, Warning, Critical, Unknown = Value
}




