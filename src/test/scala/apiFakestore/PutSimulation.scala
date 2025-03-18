package apiFakestore

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class PutSimulation extends Simulation {

  val httpProtocol = http.baseUrl("https://fakestoreapi.com")
    .header("Content-Type", "application/json")
    .header("Accept", "application/json")

  // Cargar datos desde CSV
  val csvFeeder = csv("data/putBody/updateData.csv").circular

  // Escenario para PUT con carga de datos dinámica
  val putScenarioAbierto = scenario("PUT Product - Modelo Abierto")

    .feed(csvFeeder) // Carga una fila del CSV por iteración
    .exec(http("Update Product - ID: #{id} - Abierto")
      .put("/products/#{id}") // Inserta el ID dinámicamente
      .body(ElFileBody("data/putBody/update_product.json")).asJson //
      .check(status.is(200)) // Verifica respuesta
      .check(bodyString.saveAs("responseBody")) // Guarda respuesta para logs
    )
    .exec(session => {
      println("🔹 Respuesta API: " + session("responseBody").as[String]) // Imprimir respuesta
      session
    })

  // Escenario para PUT con carga de datos dinámica
  val putScenarioCerrado = scenario("PUT Product - Modelo Cerrado")

    .feed(csvFeeder) // Carga una fila del CSV por iteración
    .exec(http("Update Product - ID: #{id} - Cerrado")
      .put("/products/#{id}") // Inserta el ID dinámicamente
      .body(ElFileBody("data/putBody/update_product.json")).asJson
      .check(status.is(200)) // Verifica respuesta
      .check(bodyString.saveAs("responseBody")) // Guarda respuesta para logs
    )
    .exec(session => {
      println("Respuesta API: " + session("responseBody").as[String]) // Imprimir respuesta
      session
    })


  // Modelo Abierto: 3 usuarios en rampa
  val modeloAbierto = putScenarioAbierto.inject(rampUsers(3).during(10))

  // Modelo Cerrado: 2 usuarios concurrentes por 15s
  val modeloCerrado = putScenarioCerrado.inject(constantConcurrentUsers(2).during(15))

  // Ejecución del Test
  setUp(
    modeloAbierto,modeloCerrado
  ).protocols(httpProtocol)

}
