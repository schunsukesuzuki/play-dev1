package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import com.github.j5ik2o.spetstore.domain.model.customer._
import com.github.j5ik2o.spetstore.infrastructure.db.{ CustomerRecord }

import play.api.i18n.{ I18nSupport }

import play.api.i18n.Messages.Implicits._

class SignupController @Inject() extends Controller
    with I18nSupport {

  object SignupController extends Controller {

    val customerForm = Form(

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

  /*
  val filledForm = customersampleForm.fill(SignupController
     (0,0,name,1,Tokyo,1234,Harajuku,address,building,gmail.com,123-456-78900,nickname,password))

*/

  // 入力ページを表示するAction
  def signup = Action {
    Ok(views.html.signup("test"))
  }

  // 結果ページを表示するAction
  def signupresult = Action { implicit request => // リクエストオブジェクトを宣言

    customerForm.bindFromRequest.fold(
      signup => { // バインドエラー ＝ 入力エラーが発生した場合
        Ok(views.html.signup(signup)) // 入力画面を再表示します。

      },
      signupresult => { // バインド成功 ＝ 入力エラーがない場合
        Ok(views.html.signupresult(customerData.fill(signupresult))) // 結果画面を表示します。
      }
    )
  }

}

