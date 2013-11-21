package models

// for building SQL parser
import anorm._
import anorm.SqlParser._

// for querying DB
import play.api.db._
import play.api.Play.current

case class Host(id: Long, hostname: String)


// companion object for Host
object Host {
  
  // the host parser, to easily get Host objects from a SQL query
  val task = {
    get[Long]("id") ~
    get[String]("hostname") map {
      case id~hostname => Host(id,hostname)
    }
  }

  def all(): List[Host] = DB.withConnection { implicit conn =>
    SQL("select * from host").as(host *)
  }
      
  def create(hostname: String) {}
          
  def delete(id: Long) {}
              
}

