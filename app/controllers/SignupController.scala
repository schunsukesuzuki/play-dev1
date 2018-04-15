package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import views.html._

import play.api.data._
import play.api.data.Forms._

import com.github.j5ik2o.spetstore.domain.model.customer._
import com.github.j5ik2o.spetstore.infrastructure.db._

import play.api.i18n.{ I18nSupport }

class SignupController @Inject() extends Controller
    with I18nSupport {

  /*

  def signup = Action {

    Ok(views.html.signup("Your new application is ready."))

  }

  def signupresult = Action {

    Ok(views.html.signupresult("Your new application is ready."))

  }

*/

  object SignupController1 extends Controller {

    val customerForm: Form[CustomerRecord] = Form(
      // Userフォームマッピング
      mapping(
        "id" -> longNumber,
        "status" -> number,

        "name" -> nonEmptyText(minLength = 4),
        "sexType" -> number,
        "zipCode" -> text,
        "prefCode" -> number,
        "cityName" -> text,
        "addressName" -> text,
        "buildingName" -> optional(text),
        "email" -> email,
        "phone" -> text,
        "loginName" -> text,
        "password" -> text,
        /*        "password" -> tuple("main" -> text(minLength = 8), "confirm" -> text).verifying(
          // パスワードの入力ルール定義
          "Passwords don't match", passwords => passwords._1 == passwords._2
        ),
*/
        "favoriteCategoryId" -> optional(longNumber),
        "version" -> longNumber

      )(CustomerRecord.apply)(CustomerRecord.unapply)
    )
  }

  // 入力ページを表示するAction
  def signup = Action {
    Ok(views.html.signup(customerForm))
  }

  // 結果ページを表示するAction
  def signupresult = Action { implicit request => // リクエストオブジェクトを宣言
    customerForm.bindFromRequest().fold(
      errorForm => { // バインドエラー ＝ 入力エラーが発生した場合
        Ok(views.html.signup(errorForm)) // 入力画面を再表示します。
      },
      requestForm => { // バインド成功 ＝ 入力エラーがない場合
        Ok(views.html.signupresult(form.fill(customerForm))) // 結果画面を表示します。
      }
    )
  }

  /*https://dev.classmethod.jp/server-side/play-yabe-2/#toc-
*/

  /**
   * ユーザー登録画面初期処理
   */

  /*
 def signup  = Action { implicit request =>

    Ok(views.html.signup("please input", customerForm))
  }

  
     ユーザー登録処理.(入力エラーがなければそのまま返す)
   
  def signupresult = Action { implicit request =>
    customerForm.bindFromRequest.fold(
      errors => BadRequest(views.html.signup(errors)),
      customerForm => {
        Ok(views.html.signupresult("confirmation", customerForm))
      }
    )
  }
*/

}

