package login2.${artifactId}.service.rest.metrics

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter

import com.typesafe.scalalogging.LazyLogging
import login2.${artifactId}.service.rest.RestServiceRegistryAware

class MetricsResponseFilter extends ContainerResponseFilter with RestServiceRegistryAware with LazyLogging {

  override def filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext): Unit = {
    val stopTime = System.currentTimeMillis()
    val propertiesAccessor = MetricsRequestContextPropertiesAccessor(requestContext)
    Option(propertiesAccessor.identifier.get()) foreach { identifier =>
      val startTime = propertiesAccessor.startTime.get()
      val duration = stopTime - startTime
      report(identifier, duration)
    }
  }

  private def report(identifier: String, duration: Long): Unit = {
    logger.trace(s"filtered identifier: $identifier ($duration ms)")
    registry.metricsService.count(s"$identifier.hits")
    registry.metricsService.measure(s"$identifier.duration", duration)
  }

}
