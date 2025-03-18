package apiFakestore

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetSimulation extends Simulation {

  // Configuración HTTP reutilizable

  val httpProtocol = http
    .baseUrl("https://fakestoreapi.com")
    .header("Content-Type", "application/json")
    .header("Accept", "application/json")

  // Feeder para evaluar endpoints dinamicos
  val csvFeeder = csv("data/getEndpoints.csv").circular


  // Escenario para GET-Modelo Abierto
  val getScenarioAbierto = scenario("GET Productos - Modelo Abierto")

    .feed(csvFeeder)
    .exec(
      http("Obtener producto #{endpoint} - Abierto")
        .get("#{endpoint}")
        .check(status.is(200))
        .check(bodyString.saveAs("responseBody")) // Guarda la respuesta para impresión
    )
    .exec(session => {
      println("Respuesta de la API (Abierto): " + session("responseBody").as[String]) // Imprimir en consola
      session
    })

  //  Escenario para GET con Feeder (Modelo Cerrado)
  val getScenarioCerrado = scenario("GET Productos - Modelo Cerrado")
    .feed(csvFeeder)
    .exec(
      http("Obtener producto #{endpoint} - Cerrado")
        .get("#{endpoint}")
        .check(status.is(200))
        .check(bodyString.saveAs("responseBody"))
    )
    .exec(session => {
      println("Respuesta de la API (Cerrado): " + session("responseBody").as[String])
      session
    })


  //  Modelo Abierto: 3 peticiones con usuarios llegando aleatoriamente
  val modeloAbierto = getScenarioAbierto.inject(
    constantUsersPerSec(1).during(3) // 1 usuario por segundo, durante 3 segundos (3 peticiones)
  )

  //  Modelo Cerrado: 2 usuarios concurrentes ejecutando pruebas
  val modeloCerrado = getScenarioCerrado.inject(
    constantConcurrentUsers(2).during(5) // 2 usuarios ejecutando en paralelo durante 5s
  )

  // Ejecución del Test**
  setUp( modeloAbierto,modeloCerrado
  ).protocols(httpProtocol)

}
