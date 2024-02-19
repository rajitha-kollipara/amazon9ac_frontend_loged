package login2.${artifactId}.service.rest.v1.random

import javax.ws.rs._
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Response

import com.typesafe.scalalogging.LazyLogging
import login2.${artifactId}.service.api.v1.common.TransportableFloatDatum
import login2.${artifactId}.service.api.v1.common.TransportableIntDatum
import login2.${artifactId}.service.api.v1.random.RandomResourceDescription._
import login2.${artifactId}.service.rest.RestServiceRegistryAware

@Path(ROOT_PATH)
class RandomResource extends RestServiceRegistryAware with LazyLogging {

  @GET
  @Path(GetNextInt.RELATIVE_PATH)
  def nextInt: Response = {
    val int = registry.randomService.int
    val transportable = TransportableIntDatum.from(int)
    Response.ok(transportable).build()
  }

  @GET
  @Path(GetNextFloat.RELATIVE_PATH)
  def nextFloat(@Suspended response: AsyncResponse): Unit = {
    val float = registry.randomService.float
    val transportable = TransportableFloatDatum.from(float)
    response.resume(transportable)
  }

  @POST
  @Path(SetSeed.RELATIVE_PATH)
  def seed(@Suspended response: AsyncResponse, @PathParam(SetSeed.Param.VALUE) seed: Long): Unit = {
    logger.trace(s"updating seed: $seed")
    registry.randomService.seed(seed)
    response.resume(Response.noContent().build())
  }

}
