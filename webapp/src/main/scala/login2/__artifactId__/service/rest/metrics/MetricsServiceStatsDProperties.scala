package login2.${artifactId}.service.rest.metrics

import login2.${artifactId}.service.util.PropertiesAccessor

class MetricsServiceStatsDProperties extends PropertiesAccessor {

  override val propertiesFileName: String = "statsd.properties"

  val host = getProperty("statsd.host")

  val port = getProperty("statsd.port", _.toInt)

  val flushRate = getProperty("statsd.flushRate", _.toDouble)

}
