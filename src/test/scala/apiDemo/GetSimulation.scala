package apiDemo


import io.gatling.core.Predef._
import io.gatling.http.Predef._


class GetSimulation extends Simulation{

  val httpProtocol = http
    .baseUrl("https://fakestoreapi.com") // URL base de la API
    .header("Content-Type", "application/json")

  // Escenario para GET
  val getScenario = scenario("GET Productos")
    .exec(http("Obtener productos")
      .get("/products")
      .check(status.is(200))
    )


  setUp(
    getScenario.inject(atOnceUsers(15)).protocols(httpProtocol)
  )

}
