package login2.${artifactId}.service.rest.v1.random

import com.typesafe.scalalogging.LazyLogging
import login2.${artifactId}.service.api.client.hystrix.v1.random.RandomHystrixServiceImpl
import login2.${artifactId}.service.api.client.v1.random.RandomServiceImpl
import login2.${artifactId}.service.rest.RestSuite
import org.junit.Assert
import org.junit.Test

class RandomResourceTest extends LazyLogging {

  import RandomResourceTest._

  @Test
  def testNextInt(): Unit = {
    val samples = Stream.continually(randomHystrixService.nextInt().execute()).take(sampleSize)
    Assert.assertEquals(samples.distinct, samples)
  }

  @Test
  def testNextFloat(): Unit = {
    val samples = Stream.continually(randomHystrixService.nextFloat().execute()).take(sampleSize)
    Assert.assertEquals(samples.distinct, samples)
  }

  @Test
  def testSeed(): Unit = {
    randomService.seed(0).get()
    val fstSamples = Stream.continually(randomHystrixService.nextInt().execute()).take(sampleSize)
    Assert.assertEquals(fstSamples.distinct, fstSamples)
    randomHystrixService.seed(0).execute()
    val sndSamples = Stream.continually(randomHystrixService.nextInt().execute()).take(sampleSize)
    Assert.assertEquals(sndSamples.distinct, sndSamples)
    Assert.assertEquals(fstSamples, sndSamples)
  }

}

object RandomResourceTest {

  val randomService = new RandomServiceImpl(RestSuite.rootWebTarget)

  val randomHystrixService = new RandomHystrixServiceImpl(randomService)

  val sampleSize = 100

}
