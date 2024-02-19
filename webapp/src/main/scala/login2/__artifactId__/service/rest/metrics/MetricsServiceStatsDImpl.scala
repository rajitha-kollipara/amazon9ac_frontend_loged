package login2.${artifactId}.service.rest.metrics

import java.time.Clock

import com.flozano.metrics.MetricsBuilder
import com.flozano.metrics.client.statsd.StatsDMetricsClientBuilder

class MetricsServiceStatsDImpl(host: String, port: Int, flushRate: Double) extends MetricsService {

  private val metricsClient = StatsDMetricsClientBuilder.create()
    .withHost(host)
    .withPort(port)
    .withFlushRate(flushRate)
    .build()

  private val metrics = MetricsBuilder.create().withClient(metricsClient).withClock(Clock.systemUTC()).build()

  override def count(identifier: String): Unit = metrics.counter(identifier).hit()

  override def measure(identifier: String, input: Long): Unit = metrics.measure(identifier).value(input)

}
