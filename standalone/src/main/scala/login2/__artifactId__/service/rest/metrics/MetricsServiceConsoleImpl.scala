package login2.${artifactId}.service.rest.metrics

import com.typesafe.scalalogging.LazyLogging

class MetricsServiceConsoleImpl extends MetricsService with LazyLogging {

  override def count(identifier: String): Unit =
    logger.trace(s"hit(): identifier=$identifier")

  override def measure(identifier: String, input: Long): Unit =
    logger.trace(s"measure(): identifier=$identifier, input=$input")

}
