package apiFakestore

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.io.Source

class PostSimulation extends Simulation {
/*
  val httpProtocol = http.baseUrl("https://fakestoreapi.com")

  //val postBody = Source.fromFile("src/main/resources/data/valid_product.json").mkString

  // Cargar datos del CSV
  val csvFeeder = csv("data/postData.csv").circular

  val postScenario = scenario("POST Product")
    .feed(csvFeeder) // Carga una fila en cada ejecución
    .exec(http("Create Product")
      .post("/products")
      .body(StringBody("""{
          "title": "${title}",
          "price": "${price}",
          "description": "${description}",
          "category": "${category}",
          "image": "${image}"
        }""")).asJson
      .check(status.is(200))
    )

  setUp(postScenario.inject(atOnceUsers(15))).protocols(httpProtocol)*/

}
