package login2.${artifactId}.service.rest

import login2.${artifactId}.service.random.RandomService
import login2.${artifactId}.service.rest.metrics.MetricsService
import login2.${artifactId}.service.rest.properties.PropertiesService

case class RestServiceRegistry
(metricsService: MetricsService,
 propertiesService: PropertiesService,
 randomService: RandomService)
