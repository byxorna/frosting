package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.Host


object Application extends Controller {

  val hostForm = Form(
    "label" -> nonEmptyText
  )

  def index = Action {
    Ok("Hello world")
  }

  def hosts = Action {
    Ok(views.html.index(Host.all(),hostForm))
  }
      
  def newHost = TODO
          
  def deleteHost(id: Long) = TODO

}
