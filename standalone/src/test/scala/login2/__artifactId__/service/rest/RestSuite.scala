package login2.${artifactId}.service.rest

import javax.ws.rs.client.ClientBuilder

import login2.${artifactId}.service.ConsoleApplication
import login2.${artifactId}.service.ConsoleProperties
import login2.${artifactId}.service.random.RandomService
import login2.${artifactId}.service.random.RandomServiceImpl
import login2.${artifactId}.service.rest.metrics.MetricsService
import login2.${artifactId}.service.rest.metrics.MetricsServiceConsoleImpl
import login2.${artifactId}.service.rest.properties.BuildProperties
import login2.${artifactId}.service.rest.properties.PropertiesService
import login2.${artifactId}.service.rest.v1.random.RandomResourceTest
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(classOf[Suite])
@SuiteClasses(Array(
  classOf[ExceptionMapperImplTest],
  classOf[RandomResourceTest]))
class RestSuite

object RestSuite {

  // properties

  val buildProperties = new BuildProperties

  val consoleProperties = new ConsoleProperties

  // services

  val metricsService: MetricsService = new MetricsServiceConsoleImpl

  val propertiesService = PropertiesService(buildProperties, consoleProperties)

  val randomService: RandomService = new RandomServiceImpl

  // instantiate reset server

  val restServiceRegistry = new RestServiceRegistry(
    metricsService = metricsService,
    propertiesService = propertiesService,
    randomService = randomService)

  val restConfig = new RestConfigImpl(restServiceRegistry) {

    override def providerClasses = super.providerClasses + classOf[ExceptionMapperImplTest.ExceptionResource]

  }

  val restServerFactory = new RestServerFactoryJerseyImpl

  val restServer = ConsoleApplication.createServer(consoleProperties, restServerFactory, restConfig)

  val rootWebTarget = ClientBuilder.newClient.target(restServer.uri())

  @BeforeClass
  def startApplication(): Unit = restServer.start()

  @AfterClass
  def stopApplication(): Unit = restServer.stop()

}
