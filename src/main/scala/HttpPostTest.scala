/**
  * Created by ramsubramani on 2/17/17.
  */

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.nscala_time.time.Imports._
import play.api.libs.ws._
import play.api.libs.ws.ahc.AhcWSClient
//import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

object HttpTest extends App{

  val url = "http://pit-devvm-opswisemaster2.snc1/opswise/resources/task/ops-task-launch"
  val userName = "ramsubramani"
  val password = "Savitha1$"

  implicit val actor = ActorSystem()
  implicit val materializer = ActorMaterializer()
  val ws = AhcWSClient()
  val request = ws.url(url)
                  .withAuth(userName, password, WSAuthScheme.BASIC)
                  .withHeaders( "Content-Type" -> "application/xml")


  val startDate = DateTime.parse("2017-01-01")
  val endDate = DateTime.parse("2017-01-10")
  val jobName = "Ramesh_test"

  for ( date: DateTime <- startDate to endDate by 1.day){

    val dt = date.toString("yyyy-MM-dd")
    val dtKey = dt.replace("-","")
    val dt1 = date + 1.day toString("yyyy-MM-dd")

    val variableName = "context"
    val variableValue = s"""--context=start_date_key:${dtKey},end_date_key:${dtKey},wf_key:${dtKey},start_date:${dt},end_date:${dt},load_date:${dt},load_start_date:${dt1}"""

    val data =
      <task-launch>
        <name>{jobName}</name>
        <variables>
          <variable>
            <name>{variableName}</name>
            <value>{variableValue}</value>
          </variable>
        </variables>
      </task-launch>

    println(data)
    val resp = request.post(data)
    val result = Await.result(resp, Duration( 5, SECONDS))
    println( result.body)
  }

  ws.close()
  actor.terminate()

}
