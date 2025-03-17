package apiFakestore

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.io.Source

class PutSimulation extends Simulation {
/*
  val httpProtocol = http.baseUrl("https://fakestoreapi.com")

  val putBody = Source.fromFile("src/main/resources/data/update_product.json").mkString

  val putScenario = scenario("PUT Product")
    .exec(http("Update Product")
      .put("/products/1")
      .body(StringBody(putBody)).asJson
      .check(status.is(200))
    )

  setUp(putScenario.inject(atOnceUsers(2))).protocols(httpProtocol)*/

}
