package login2.${artifactId}.service

import login2.${artifactId}.service.random.RandomServiceImpl
import login2.${artifactId}.service.rest._
import login2.${artifactId}.service.rest.metrics.MetricsServiceConsoleImpl
import login2.${artifactId}.service.rest.properties.BuildProperties
import login2.${artifactId}.service.rest.properties.PropertiesService

object ConsoleApplication {

  def main(args: Array[String]): Unit = {

    // properties
    val consoleProperties = new ConsoleProperties
    val buildProperties = new BuildProperties
    val propertiesService = PropertiesService(consoleProperties, buildProperties)

    // instantiate rest server
    val restServerFactory = new RestServerFactoryJerseyImpl
    val restServiceRegistry = new RestServiceRegistry(
      metricsService = new MetricsServiceConsoleImpl,
      propertiesService = propertiesService,
      randomService = new RandomServiceImpl)
    val restConfig = new RestConfigImpl(restServiceRegistry)
    val restServer = createServer(consoleProperties, restServerFactory, restConfig)

    // start rest server
    try {
      restServer.start()
      restServer.join()
    } finally {
      restServer.stop()
    }

  }

  def createServer
  (consoleProperties: ConsoleProperties,
   restServerFactory: RestServerFactory,
   restConfig: RestConfig): RestServer =
    restServerFactory.create(
      uri = consoleProperties.serverUri,
      properties = restConfig.properties,
      resourceClasses = restConfig.providerClasses ++ restConfig.resourceClasses)

}
