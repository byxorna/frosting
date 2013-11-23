package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._


object Application extends Controller {

  val hostForm = Form(
    "hostname" -> nonEmptyText
  )
  val commandForm = Form(tuple(
    "name" -> nonEmptyText,
    "command" -> nonEmptyText
  ))

  def index = Action {
    Ok(views.html.index("Hello world"))
  }

  def hosts = Action {
    Ok(views.html.hosts(Host.all(),hostForm))
  }

  def newHost = Action { implicit request =>
    hostForm.bindFromRequest.fold(
      errors => BadRequest(views.html.hosts(Host.all(), errors)),
      hostname => {
        Host.create(hostname)
        Redirect(routes.Application.hosts)
      }
    )
  }

  def deleteHost(id: Long) = Action {
    Host.delete(id)
    Redirect(routes.Application.hosts)
  }

  def commands = Action {
    Ok(views.html.commands(Command.all(),commandForm))
  }

  def newCommand = Action { implicit request =>
    commandForm.bindFromRequest.fold(
      errors => BadRequest(views.html.commands(Command.all(), errors)),
      value => {
        Command.create(value._1,value._2)
        Redirect(routes.Application.commands)
      }
    )
  }

  def deleteCommand(id: Long) = Action {
    Command.delete(id)
    Redirect(routes.Application.commands)
  }
}
