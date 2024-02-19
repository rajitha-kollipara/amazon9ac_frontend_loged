package login2.${artifactId}.service.rest

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

import com.typesafe.scalalogging.LazyLogging
import login2.${artifactId}.service.api.exception.ServiceException
import login2.${artifactId}.service.api.v1.exception.TransportableServiceException

@Provider
class ExceptionMapperImpl extends ExceptionMapper[Throwable] with LazyLogging {

  override def toResponse(throwable: Throwable): Response =
    throwable match {
      case wae: WebApplicationException => wae.getResponse
      case _ =>
        val message = "uncaught endpoint exception"
        logger.debug(message, throwable)
        val status =
          if (throwable.isInstanceOf[IllegalArgumentException]) Status.BAD_REQUEST
          else Status.INTERNAL_SERVER_ERROR
        val serviceException = new ServiceException(getFirstValidMessage(throwable).getOrElse(message))
        val transportable = TransportableServiceException.from(serviceException)
        Response
          .status(status)
          .entity(transportable)
          .build()
    }

  def getFirstValidMessage(exception: Throwable): Option[String] =
    Option(exception.getMessage)
      .map(_.trim)
      .orElse(
        Option(exception.getCause)
          .flatMap(getFirstValidMessage))

}
