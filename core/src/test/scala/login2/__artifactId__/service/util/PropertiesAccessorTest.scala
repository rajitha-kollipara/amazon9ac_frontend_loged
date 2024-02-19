package login2.${artifactId}.service.util

import org.junit.Assert
import org.junit.Test

class PropertiesAccessorTest extends PropertiesAccessor {

  override val propertiesFileName: String = "test.properties"

  @Test
  def shouldReadText(): Unit =
    Assert.assertEquals("abc def", getProperty("description"))

  @Test
  def shouldReadInt(): Unit =
    Assert.assertEquals(1, getProperty("counter", _.toInt))

  @Test(expected = classOf[IllegalArgumentException])
  def shouldFailConvertingTextToInt(): Unit =
    getProperty("description", _.toInt)

  @Test(expected = classOf[IllegalArgumentException])
  def shouldFailOnNonExistingProperty(): Unit =
    getProperty("non-existent")

  @Test(expected = classOf[IllegalArgumentException])
  def shouldFailOnNonExistingFile(): Unit = {
    new PropertiesAccessor {
      override val propertiesFileName: String = "unknown.properties"
      getProperty("description")
    }
  }

}
