package login2.${artifactId}.service.rest.properties

import login2.${artifactId}.service.util.PropertiesAccessor

import scala.collection.immutable._

trait PropertiesService {

  def content: Map[String, String]

}

object PropertiesService {

  def apply(accessors: PropertiesAccessor*): PropertiesService =
    new PropertiesService {
      override val content = accessors.flatMap(_.toMap).toMap
    }

}
