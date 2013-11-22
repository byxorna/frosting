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
  command: String,
  num_args: Integer
)


object Command {

  val command = {
    get[Long]("id") ~
    get[String]("name") ~
    get[String]("command") ~
    get[Integer]("num_args") map {
      case id~name~command~num_args => Command(id,name,command,num_args)
    }
  }

  def all(): List[Host] = DB.withConnection { implicit conn =>
    SQL("select * from command").as(command *)
  }

  def create(name: String, command: String) {
    // parse the command for any args
    //val num_args: Integer := 0

    DB.withConnection { implicit conn =>
      SQL("""insert into command (name,command,num_args)
             values ({name},{command},{num_args})""").on(
        'name -> name,
        'command -> command,
        'num_args -> num_args
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

