package login2.${artifactId}.service.rest.metrics

import java.lang.reflect.Method
import java.util.concurrent.atomic.AtomicReference
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.Context
import javax.ws.rs.ext.Provider

import com.typesafe.scalalogging.LazyLogging

import scala.collection.immutable.Map

@Provider
class MetricsRequestFilter extends ContainerRequestFilter with LazyLogging {

  @Context
  var resource: ResourceInfo = null

  override def filter(requestContext: ContainerRequestContext): Unit = {
    val propertiesAccessor = MetricsRequestContextPropertiesAccessor(requestContext)
    val identifier = findIdentifier(resource.getResourceClass, resource.getResourceMethod)
    propertiesAccessor.identifier.set(identifier)
    propertiesAccessor.startTime.set(System.currentTimeMillis())
    logger.trace("filtered identifier: {}", identifier)
  }

  private type IdentifierKey = (Class[_], Method)

  private val identifierCacheReference = new AtomicReference[Map[IdentifierKey, String]](Map.empty)

  private def findIdentifier(key: IdentifierKey): String = {
    val cache = identifierCacheReference.get()
    cache.get(key) match {
      case Some(identifier) => identifier
      case None =>
        val identifier = key match { case (clazz, method) => s"${clazz.getCanonicalName}.${method.getName}" }
        identifierCacheReference.set(cache + (key -> identifier))
        identifier
    }
  }

}
