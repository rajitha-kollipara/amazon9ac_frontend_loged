package login2.${artifactId}.service.rest

import java.net.URI

import com.typesafe.scalalogging.LazyLogging
import org.eclipse.jetty.server.Server

trait RestServer {

  def join(): Unit

  def start(): Unit

  def stop(): Unit

  def uri(): URI

}

class RestServerJerseyImpl(server: Server) extends RestServer with LazyLogging {

  override def join(): Unit = {
    logger.trace("joining to the server...")
    server.join()
  }

  override def start(): Unit = {
    logger.trace("starting server...")
    server.start()
    logger.info("server is started at {}", server.getURI)
  }

  override def stop(): Unit = {
    logger.trace("stopping server...")
    server.stop()
    logger.info("server is stopped")
  }

  override def uri(): URI = server.getURI

}
