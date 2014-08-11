package models.asset

import java.sql.Timestamp

import scala.math.BigDecimal.long2bigDecimal

import models.Asset
import models.AssetType
import models.State
import models.State.StateFormat
import models.Status
import models.Conversions.TimestampFormat
import play.api.libs.json.Format
import play.api.libs.json.JsNumber
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.json.JsValue
import play.api.libs.json.Json

object Conversions {
  import models.State.StateFormat
  implicit object AssetFormat extends Format[AssetView] {
    override def reads(json: JsValue) = Asset(
      (json \ "TAG").as[String],
      Status.findByName((json \ "STATUS").as[String]).map(_.id).get,
      AssetType.findByName((json \ "TYPE").as[String]).map(_.id).get,
      (json \ "CREATED").as[Timestamp],
      (json \ "UPDATED").asOpt[Timestamp],
      (json \ "DELETED").asOpt[Timestamp],
      (json \ "ID").as[Long],
      (json \ "STATE").asOpt[State].map(_.id).getOrElse(0))
    override def writes(asset: AssetView): JsObject = JsObject(Seq(
      "ID" -> JsNumber(asset.id),
      "TAG" -> JsString(asset.tag),
      "STATE" -> Json.toJson(State.findById(asset.state)),
      "STATUS" -> JsString(asset.getStatusName),
      "TYPE" -> Json.toJson(AssetType.findById(asset.asset_type).map(_.name)),
      "CREATED" -> Json.toJson(asset.created),
      "UPDATED" -> Json.toJson(asset.updated),
      "DELETED" -> Json.toJson(asset.deleted)))
  }
}
