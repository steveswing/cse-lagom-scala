package com.example.hello.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

/**
  * The Hello service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the HelloService.
  */
trait HelloService extends Service {

  /**
    * Example:
    * curl http://localhost:9000/api/hello/Alice
    * curl http://localhost:9000/api/hello/Bob
    * curl http://localhost:9000/api/hello/Bacon
    */
  def hello(id: String): ServiceCall[NotUsed, String]

  /**
    * Example:
    * curl -H "Content-Type: application/json" -X POST -d '{"message":"Hi"}' http://localhost:9000/api/hello/Alice
    * curl -H "Content-Type: application/json" -X POST -d '{"message":"Hello"}' http://localhost:9000/api/hello/Bob
    * curl -H "Content-Type: application/json" -X POST -d '{"message":"Eat me or drink me"}' http://localhost:9000/api/hello/Alice
    * curl -H "Content-Type: application/json" -X POST -d '{"message":"What's shakin'"}' http://localhost:9000/api/hello/Bacon
    */
  def useGreeting(id: String): ServiceCall[GreetingMessage, Done]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("hello").withCalls(
      pathCall("/api/hello/:id", hello _),
      pathCall("/api/hello/:id", useGreeting _)
    ).withAutoAcl(true)
    // @formatter:on
  }
}

/**
  * The greeting message class.
  */
case class GreetingMessage(message: String)

object GreetingMessage {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
}
