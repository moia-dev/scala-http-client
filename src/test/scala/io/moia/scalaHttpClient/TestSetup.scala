package io.moia.scalaHttpClient

import java.time.Clock

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpMethod, HttpResponse, Uri}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.ExecutionContext

trait TestSetup extends AnyWordSpecLike with Matchers with FutureValues {
  implicit val system: ActorSystem                = ActorSystem("test")
  implicit val executionContext: ExecutionContext = system.dispatcher

  val clock: Clock = Clock.systemUTC()
  val httpMetrics: HttpMetrics[NoLoggingContext] = new HttpMetrics[NoLoggingContext] {
    override def meterResponse(method: HttpMethod, path: Uri.Path, response: HttpResponse)(implicit ctx: NoLoggingContext): Unit = ()
  }

  val httpClientConfig: HttpClientConfig = HttpClientConfig("http", "127.0.0.1", 8888)

  val retryConfig: RetryConfig = RetryConfig.default
}
