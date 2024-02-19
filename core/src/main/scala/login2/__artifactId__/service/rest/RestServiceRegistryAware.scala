package login2.${artifactId}.service.rest

import javax.ws.rs.core.Application
import javax.ws.rs.core.Context

trait RestServiceRegistryAware {

  @Context
  var application: Application = null

  protected def registry: RestServiceRegistry =
    application
      .getProperties
      .get(classOf[RestServiceRegistry].getCanonicalName)
      .asInstanceOf[RestServiceRegistry]

}
