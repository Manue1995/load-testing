package apiFakestore

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.io.Source

class PostSimulation extends Simulation {

  val httpProtocol = http.baseUrl("https://fakestoreapi.com")
    .header("Content-Type", "application/json")
    .header("Accept", "application/json")

  // Cargar datos del CSV
  val csvFeeder = csv("data/postBody/postProduct.csv").circular

  val postScenarioAbierto  = scenario("POST Product - Modelo Abierto")

    .feed(csvFeeder) // Carga una fila en cada ejecución
    .exec(http("Crear Producto en /products - Abierto")
      .post("/products")
      .body(ElFileBody("data/postBody/postProduct.json")).asJson
      .check(status.is(200))
      .check(jsonPath("$.id").saveAs("productId"))
    )
    .exec(session => {
      println(s"✔ Producto creado con ID: " + session("productId").as[String])
      session
    })

  val postScenarioCerrado   = scenario("POST Product - Modelo Cerrado")

    .feed(csvFeeder) // Carga una fila en cada ejecución
    .exec(http("Crear Producto en /products - Cerrado")
      .post("/products")
      .body(ElFileBody("data/postBody/postProduct.json")).asJson
      .check(status.is(200))
      .check(jsonPath("$.id").saveAs("productId"))
    )
    .exec(session => {
      println(s"✔ Producto creado con ID: " + session("productId").as[String])
      session
    })

  // Modelo Abierto: 3 peticiones con usuarios llegando gradualmente
  val modeloAbierto = postScenarioAbierto.inject(
    constantUsersPerSec(1).during(3) // 1 usuario por segundo durante 3 segundos
  )

  // Modelo Cerrado: 2 usuarios concurrentes ejecutando pruebas
  val modeloCerrado = postScenarioCerrado.inject(
    constantConcurrentUsers(2).during(5) // 2 usuarios en paralelo durante 5s
  )

  // Ejecución del Test
  setUp(modeloAbierto, modeloCerrado
  ).protocols(httpProtocol)

}
