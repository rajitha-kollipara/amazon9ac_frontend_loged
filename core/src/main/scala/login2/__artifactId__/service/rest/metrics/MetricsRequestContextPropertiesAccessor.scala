package login2.${artifactId}.service.rest.metrics

import javax.ws.rs.container.ContainerRequestContext

case class MetricsRequestContextPropertiesAccessor(context: ContainerRequestContext) {

  private val packageName = classOf[MetricsRequestContextPropertiesAccessor].getPackage.getName

  val startTime = Accessor[Long](s"$packageName.startTime")

  val identifier = Accessor[String](s"$packageName.identifier")

  sealed case class Accessor[T](key: String) {

    def get(): T = context.getProperty(key).asInstanceOf[T]

    def set(value: T): Unit = context.setProperty(key, value)

  }

}
