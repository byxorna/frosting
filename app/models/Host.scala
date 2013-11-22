package models

// for building SQL parser
import anorm._
import anorm.SqlParser._

// for querying DB
import play.api.db._
import play.api.Play.current

case class Host(id: Long, hostname: String, monitored: Boolean)


// companion object for Host
object Host {

  // the host parser, to easily get Host objects from a SQL query
  val host = {
    get[Long]("id") ~
    get[String]("hostname") ~
    get[Boolean]("monitored") map {
      case id~hostname~monitored => Host(id,hostname,monitored)
    }
  }

  def all(): List[Host] = DB.withConnection { implicit conn =>
    SQL("select * from host").as(host *)
  }

  def create(hostname: String) {
    DB.withConnection { implicit conn =>
      SQL("insert into host (hostname) values ({hostname})").on(
        'hostname -> hostname
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit conn =>
      SQL("delete from host where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}

