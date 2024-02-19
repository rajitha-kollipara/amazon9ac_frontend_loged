package login2.${artifactId}.service

import login2.${artifactId}.service.random.RandomService
import login2.${artifactId}.service.random.RandomServiceImpl
import login2.${artifactId}.service.rest.RestConfig
import login2.${artifactId}.service.rest.RestConfigImpl
import login2.${artifactId}.service.rest.RestServiceRegistry
import login2.${artifactId}.service.rest.metrics.MetricsService
import login2.${artifactId}.service.rest.metrics.MetricsServiceStatsDImpl
import login2.${artifactId}.service.rest.metrics.MetricsServiceStatsDProperties
import login2.${artifactId}.service.rest.properties.BuildProperties
import login2.${artifactId}.service.rest.properties.PropertiesService
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.ServerProperties

import scala.collection.JavaConverters._

class ServletApplication extends ResourceConfig(ServletApplication.resourceClasses.toSeq: _*) {

  setProperties(ServletApplication.properties.asJava)

}

object ServletApplication {

  // properties

  val buildProperties = new BuildProperties

  val metricsServiceStatsDProperties = new MetricsServiceStatsDProperties

  // rest services

  val metricsService: MetricsService =
    new MetricsServiceStatsDImpl(
      host = metricsServiceStatsDProperties.host,
      port = metricsServiceStatsDProperties.port,
      flushRate = metricsServiceStatsDProperties.flushRate)

  val propertiesService = PropertiesService(buildProperties, metricsServiceStatsDProperties)

  val randomService: RandomService = new RandomServiceImpl

  val restServiceRegistry = new RestServiceRegistry(
    metricsService = metricsService,
    propertiesService = propertiesService,
    randomService = randomService)

  val restConfig: RestConfig = new RestConfigImpl(restServiceRegistry)

  // jax-rs application exports

  val properties = restConfig.properties + (ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED -> true)

  val resourceClasses = restConfig.providerClasses ++ restConfig.resourceClasses

}
