package login2.${artifactId}.service.util

import java.util.Properties

import scala.collection.immutable._
import scala.collection.JavaConverters._

trait PropertiesAccessor { self =>

  val propertiesFileName: String

  lazy val properties: Properties = {
    val instance = new Properties
    val inputStream = self.getClass.getClassLoader.getResourceAsStream(propertiesFileName)
    require(inputStream != null, s"could not locate properties: $propertiesFileName")
    instance.load(inputStream)
    instance
  }

  def toMap: Map[String, String] =
    properties.stringPropertyNames().asScala.map { name => name -> properties.getProperty(name) }.toMap

  def getProperty(name: String): String =
    Option(properties.getProperty(name)).getOrElse {
      throw new IllegalArgumentException(s"missing $propertiesFileName property: $name")
    }

  def getProperty[T](name: String, parse: String => T): T =
    try { parse(getProperty(name)) }
    catch { case exception: Exception =>
      throw new IllegalArgumentException(
        s"illegal $propertiesFileName content for property: $name", exception)
    }

}
