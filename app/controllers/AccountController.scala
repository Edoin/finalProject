package controllers

import javax.inject._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{ I18nSupport, MessagesApi }

import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import models.User
import models.tables.Users
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class AccountController @Inject() (
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets,
  val users: Users
) extends Controller with I18nSupport with PageMetaSupport {

  /**
   * Create an Action to render an HTML page.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  
  val loginForm = Form (
    tuple(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )
  )

  val forgotPasswordForm = Form (
    tuple(
      "username" -> nonEmptyText,
      "email" -> nonEmptyText
    )
  )

  val userForm = Form (
    mapping(
      "id"          -> optional(number),  
      "fullname"    -> nonEmptyText,
      "birthdate"   -> sqlDate("yyyy-MM-dd"),
      "email"       -> nonEmptyText,
      "number"      -> nonEmptyText,
      "address"     -> nonEmptyText,
      "nationality" -> nonEmptyText,
      "username"    -> nonEmptyText,
      "password"    -> nonEmptyText
    )(User.apply)(User.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.auth.index(userForm))
  }
  
  // def index = Action { implicit request =>
  //   request.session.get("connected").map { user =>
  //     Ok("Hello " + user)
  //   }.getOrElse {
  //     Unauthorized("Oops, you are not connected")
  //   }
  // }

  def friends = Action { implicit request =>
    Ok(views.html.auth.friends())
  }
  // def edit(id: Int) = Action.async { implicit request =>
  // contacts.findById(id).map {
  //   case Some(contact) =>  Ok(views.html.update(signupForm.fill(contact)))
  //   case None => NotFound
  // }


}
