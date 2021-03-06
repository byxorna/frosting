package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._


object Application extends Controller {

  val hostForm = Form(tuple(
    "hostname" -> nonEmptyText,
    "attribute_keys" -> list(text),
    "attribute_values" -> list(text),
    "monitored" -> boolean
  ))
  val commandForm = Form(tuple(
    "name" -> nonEmptyText,
    "command" -> nonEmptyText
  ))
  val serviceForm = Form(tuple(
    "name" -> nonEmptyText,
    "description" -> nonEmptyText,
    "arguments" -> list(text),
    "command_id" -> number,
    "contact_id" -> number
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
      value => {
        Host.create(value._1,(value._2 zip value._3).toMap, value._4)
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

  def services = Action {
    Ok(views.html.services(Service.all(),serviceForm))
  }

  def newService = Action { implicit request =>
    serviceForm.bindFromRequest.fold(
      errors => BadRequest(views.html.services(Service.all(), errors)),
      value => {
        Service.create(value._1,value._2,value._3,value._4,Some(value._5))
        Redirect(routes.Application.services)
      }
    )
  }

  def deleteService(id: Long) = Action {
    Service.delete(id)
    Redirect(routes.Application.services)
  }

}
