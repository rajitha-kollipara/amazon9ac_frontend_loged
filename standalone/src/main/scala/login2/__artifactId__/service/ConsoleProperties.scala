package login2.${artifactId}.service

import login2.${artifactId}.service.util.PropertiesAccessor

class ConsoleProperties extends PropertiesAccessor {

  override val propertiesFileName: String = "console.properties"

  val serverUri = getProperty("console.server.uri")

}
