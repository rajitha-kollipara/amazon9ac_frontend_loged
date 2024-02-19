package login2.${artifactId}.service.rest.protobuf

import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

import com.google.protobuf.InvalidProtocolBufferException
import com.typesafe.scalalogging.LazyLogging
import login2.${artifactId}.service.api.exception.ServiceException
import login2.${artifactId}.service.api.v1.exception.TransportableServiceException

@Provider
class InvalidProtocolBufferExceptionMapper
  extends ExceptionMapper[InvalidProtocolBufferException]
  with LazyLogging {

  override def toResponse(exception: InvalidProtocolBufferException): Response = {
    val message = "unable to process protocol buffer message"
    val serviceException = new ServiceException(message)
    val transportable = TransportableServiceException.from(serviceException)
    logger.debug(message, exception)
    Response.status(Response.Status.BAD_REQUEST)
      .`type`(MediaType.APPLICATION_OCTET_STREAM_TYPE)
      .entity(transportable)
      .build()
  }

}
