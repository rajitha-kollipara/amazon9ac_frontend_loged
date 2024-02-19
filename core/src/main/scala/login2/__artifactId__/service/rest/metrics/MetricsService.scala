package login2.${artifactId}.service.rest.metrics

trait MetricsService {

  def count(identifier: String): Unit

  def measure(identifier: String, value: Long): Unit

}
