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

  def deleteHost(id: Long) = TODO

}
