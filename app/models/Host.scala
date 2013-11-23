package models

// for building SQL parser
import anorm._
import anorm.SqlParser._

// for querying DB
import play.api.db._
import play.api.Play.current

import play.api.libs.json._

case class Host(
  id: Long,
  hostname: String,
  attributes_json: Option[String],
  monitored: Boolean
)


// companion object for Host
object Host {

  // the host parser, to easily get Host objects from a SQL query
  // I know, storing json blobs in attributes is lame. but I am not willing
  // to bring the pain of a denormalized EAV schema
  val host = {
    get[Long]("id") ~
    get[String]("hostname") ~
    get[Option[String]]("attributes_json") ~
    get[Boolean]("monitored") map {
      case id~hostname~attributes_json~monitored => attributes_json match {
        case None => Host(id,hostname,None,monitored)
        //TODO: parse attrs to Map[String,String] here
        case Some(attrs) => Host(id,hostname,Some(attrs),monitored)
      }
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

