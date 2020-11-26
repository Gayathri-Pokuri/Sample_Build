package sample

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://dummy.restapiexample.com") // Here is the root for all relative URLs
       // .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  def Api() = {
      exec(http("request")
        .get("/api/v1/employees")
      )
  }


  val scn = scenario("Scenario Name").forever(){ // A scenario is a chain of requests and pauses
    pace(60)
    .exec(Api())
      }
     // Note that Gatling has recorder real time pauses


  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol)).maxDuration(10 minutes)
}
