package sample

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://dummy.restapiexample.com") // Here is the root for all relative URLs
    .disableCaching
 

  def Api() = {
    exec(http("request")
      .get("/api/v1/employees")
      .proxy(           Proxy("proxy_access.eu-west-2.aws.uk.tsb", 3128)             .httpsPort(3128))
    )
  }


  val scn = scenario("Api").forever() {
    pace(60)
    .exec(Api())
  }

  // Note that Gatling has recorder real time pauses

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol).maxDuration(10 minutes)
}
