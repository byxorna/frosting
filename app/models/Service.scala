package models

// for building SQL parser
import anorm._
import anorm.SqlParser._

// for querying DB
import play.api.db._
import play.api.Play.current

import play.api.libs.json._

case class Service(
  id: Long,
  name: String,
  description: String,
  arguments_json: Option[String],
  arguments: List[String],
  command_id: Long,
  contact_id: Option[Long]
)


object Service {

  val service = {
    get[Long]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[Option[String]]("arguments_json") ~
    get[Long]("command_id") ~
    get[Option[Long]]("contact_id") map {
      case id~name~desc~args_json~cmd_id~contact_id => args_json match {
        case None =>
          Service(id,name,desc,args_json,List[String](),cmd_id,contact_id)
        case Some(s) => Service(
          id,name,desc,args_json,
          Json.parse(s).as[List[String]],
          cmd_id,contact_id)
      }
    }
  }

  def all(): List[Service] = DB.withConnection { implicit conn =>
    SQL("select * from service").as(service *)
  }

  def create(name: String, description: String, arguments: List[String], command_id: Long, contact_id: Option[Long]) {
    DB.withConnection { implicit conn =>
      SQL("""insert into service 
            (name,description,arguments_json,command_id,contact_id)
             values ({name},{description},{arguments_json},{command_id},{contact_id})""").on(
        'name -> name,
        'description -> description,
        'arguments_json -> Json.stringify(Json.toJson[List[String]](arguments)),
        'command_id -> command_id,
        'contact_id -> contact_id
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit conn =>
      SQL("delete from service where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}

