package login2.${artifactId}.service.rest

import login2.${artifactId}.service.rest.metrics.MetricsRequestFilter
import login2.${artifactId}.service.rest.metrics.MetricsResponseFilter
import login2.${artifactId}.service.rest.properties.PropertiesResource
import login2.${artifactId}.service.rest.protobuf.InvalidProtocolBufferExceptionMapper
import login2.${artifactId}.service.rest.protobuf.ProtocolBufferBinaryMessageBodyProvider
import login2.${artifactId}.service.rest.protobuf.ProtocolBufferJsonMessageBodyProvider
import login2.${artifactId}.service.rest.protobuf.ProtocolBufferXmlMessageBodyProvider
import login2.${artifactId}.service.rest.v1.random.RandomResource

import scala.collection.immutable.Map
import scala.collection.immutable.Set

trait RestConfig {

  def properties: Map[String, AnyRef]

  def providerClasses: Set[Class[_]]

  def resourceClasses: Set[Class[_]]

}

class RestConfigImpl(serviceRegistry: RestServiceRegistry) extends RestConfig {

  override def properties: Map[String, AnyRef] = Map(classOf[RestServiceRegistry].getCanonicalName -> serviceRegistry)

  override def providerClasses: Set[Class[_]] = Set(
    classOf[ExceptionMapperImpl],
    classOf[InvalidProtocolBufferExceptionMapper],
    classOf[ProtocolBufferBinaryMessageBodyProvider],
    classOf[ProtocolBufferJsonMessageBodyProvider],
    classOf[ProtocolBufferXmlMessageBodyProvider],
    classOf[MetricsRequestFilter],
    classOf[MetricsResponseFilter],
    classOf[PropertiesResource])

  override def resourceClasses: Set[Class[_]] = Set(classOf[RandomResource])

}
