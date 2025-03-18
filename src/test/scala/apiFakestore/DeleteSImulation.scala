package apiFakestore

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class DeleteSImulation extends Simulation{

  val httpProtocol = http.baseUrl("https://fakestoreapi.com")
    .header("Content-Type", "application/json")
    .header("Accept", "application/json")

  // Cargar datos desde CSV
  val csvFeeder = csv("data/deleteBody/deleteData.csv").circular

  // Escenario para DELETE Abierto
  val deleteScenarioAbierto = scenario("DELETE Product - Modelo Abierto")

    .feed(csvFeeder) // Carga IDs del CSV
    .exec(http("Delete Product - ID: #{id} - Abierto")
      .delete("/products/#{id}") // Inserta el ID dinámicamente
      .check(status.is(200)) // Verifica respuesta
      .check(bodyString.saveAs("responseBody")) // Guarda respuesta para logs
    )
    .exec(session => {
      println("🗑️ Producto eliminado: " + session("responseBody").as[String]) // Imprimir respuesta
      session
    })

  // Escenario para DELETE Cerrado
  val deleteScenarioCerrado = scenario("DELETE Product - Modelo Cerrado")

    .feed(csvFeeder) // Carga IDs del CSV
    .exec(http("Delete Product - ID: #{id} - Cerrado")
      .delete("/products/#{id}") // Inserta el ID dinámicamente
      .check(status.is(200)) // Verifica respuesta
      .check(bodyString.saveAs("responseBody")) // Guarda respuesta para logs
    )
    .exec(session => {
      println("🗑️ Producto eliminado: " + session("responseBody").as[String]) // Imprimir respuesta
      session
    })

  // Modelo Abierto: 3 usuarios en rampa
  val modeloAbierto = deleteScenarioAbierto.inject(rampUsers(3).during(10))

  // Modelo Cerrado: 2 usuarios concurrentes por 15s
  val modeloCerrado = deleteScenarioCerrado.inject(constantConcurrentUsers(2).during(15))

  setUp(
    modeloAbierto,
    modeloCerrado
  ).protocols(httpProtocol)





}
