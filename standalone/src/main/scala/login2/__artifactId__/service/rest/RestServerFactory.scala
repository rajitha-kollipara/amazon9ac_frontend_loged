package login2.${artifactId}.service.rest

import java.net.URI
import javax.ws.rs.core.UriBuilder

import com.typesafe.scalalogging.LazyLogging
import org.glassfish.jersey.jetty.JettyHttpContainerFactory
import org.glassfish.jersey.server.ResourceConfig

import scala.collection.JavaConverters._
import scala.collection.immutable._

trait RestServerFactory {

  def create(uri: String, properties: Map[String, AnyRef], resourceClasses: Set[Class[_]]): RestServer

}

class RestServerFactoryJerseyImpl extends RestServerFactory with LazyLogging {

  override def create(uri: String, properties: Map[String, AnyRef], resourceClasses: Set[Class[_]]): RestServer = {
    val serverUri = createServerUri(uri)
    val resourceConfig = createResourceConfig(properties, resourceClasses)
    new RestServerJerseyImpl(JettyHttpContainerFactory.createServer(serverUri, resourceConfig))
  }

  private def createServerUri(uri: String): URI = {
    require(uri != null, s"service uri cannot be null")
    logger.trace(s"service uri: $uri")
    UriBuilder.fromUri(uri).build()
  }

  private def createResourceConfig(properties: Map[String, AnyRef], resourceClasses: Set[Class[_]]): ResourceConfig = {
    logger.trace(s"resource classes: $resourceClasses")
    new ResourceConfig(resourceClasses.toSeq: _*).setProperties(properties.asJava)
  }

}
