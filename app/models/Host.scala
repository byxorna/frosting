package models

case class Host(id: Long, hostname: String)

object Host {
  
  def all(): List[Host] = Nil
      
  def create(hostname: String) {}
          
  def delete(id: Long) {}
              
}
