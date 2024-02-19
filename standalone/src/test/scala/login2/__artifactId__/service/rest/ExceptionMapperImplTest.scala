package login2.${artifactId}.service.rest

import java.io.InputStream
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

import login2.${artifactId}.service.api.transport.Transportables
import login2.${artifactId}.service.api.transport.v1.Transportable
import org.junit.Assert
import org.junit.Test

class ExceptionMapperImplTest {

  @Test
  def testIllegalArgumentExceptionsAreMapped(): Unit = {
    val expectedMessage = "test illegal argument exception content"
    testExceptionsAreMapped(
      s"/test/throwIllegalArgumentException/$expectedMessage",
      Status.BAD_REQUEST.getStatusCode,
      expectedMessage)
  }

  @Test
  def testThrowablesAreMapped(): Unit = {
    val expectedMessage = "test throwable content"
    testExceptionsAreMapped(
      s"/test/throwThrowable/$expectedMessage",
      Status.INTERNAL_SERVER_ERROR.getStatusCode,
      expectedMessage)
  }

  private def testExceptionsAreMapped(requestPath: String, expectedStatus: Int, expectedMessage: String): Unit = {
    val response = RestSuite.rootWebTarget
      .path(requestPath)
      .request()
      .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
      .get()
    Assert.assertEquals(response.getStatus, expectedStatus)
    val responseEntity = response.getEntity
    Assert.assertNotNull(responseEntity)
    val responseEntityStream = responseEntity.asInstanceOf[InputStream]
    val transportable = Transportables.readBinary(classOf[Transportable.ServiceException], responseEntityStream)
    val actualMessage = transportable.getMessage
    Assert.assertEquals(expectedMessage, actualMessage)
  }

}

object ExceptionMapperImplTest {

  @Path("/test")
  class ExceptionResource {

    @GET
    @Path("throwIllegalArgumentException/{message}")
    def throwIllegalArgumentException(@PathParam("message") message: String): Response =
      throw new IllegalArgumentException(message)

    @GET
    @Path("throwThrowable/{message}")
    def throwThrowable(@PathParam("message") message: String): Response =
      throw new Throwable(message)

  }

}
