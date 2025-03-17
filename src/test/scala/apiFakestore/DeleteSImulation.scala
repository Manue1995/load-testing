package apiFakestore

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class DeleteSImulation extends Simulation{

  val httpProtocol = http.baseUrl("https://fakestoreapi.com")

  val deleteScenario = scenario("DELETE Product")
    .exec(http("Delete Product")
      .delete("/products/1")
      .check(status.is(200))
    )

  setUp(deleteScenario.inject(atOnceUsers(1))).protocols(httpProtocol)

}
