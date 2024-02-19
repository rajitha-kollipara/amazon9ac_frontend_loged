package login2.${artifactId}.service.random

import com.typesafe.scalalogging.LazyLogging

import scala.util.Random

trait RandomService {

  def int: Int

  def float: Float

  def seed(value: Long): Unit

}

class RandomServiceImpl extends RandomService with LazyLogging {

  private val random = new Random

  override def int: Int = random.nextInt()

  override def float: Float = random.nextFloat()

  override def seed(value: Long): Unit = random.setSeed(value)

}
