package models

// for building SQL parser
import anorm._
import anorm.SqlParser._

// for querying DB
import play.api.db._
import play.api.Play.current

case class Command(
  id: Long,
  name: String,
  command: String
)


object Command {

  val command = {
    get[Long]("id") ~
    get[String]("name") ~
    get[String]("command") map {
      case id~name~command => Command(id,name,command)
    }
  }

  def all(): List[Command] = DB.withConnection { implicit conn =>
    SQL("select * from command").as(command *)
  }

  def create(name: String, command: String) {
    DB.withConnection { implicit conn =>
      SQL("""insert into command (name,command)
             values ({name},{command})""").on(
        'name -> name,
        'command -> command
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit conn =>
      SQL("delete from command where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}

