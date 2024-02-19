package login2.${artifactId}.service.rest.protobuf

import java.lang.annotation.Annotation
import java.lang.reflect.Type
import javax.ws.rs.core.MediaType
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.MessageBodyWriter

import com.google.protobuf.Message

trait ProtocolBufferMessageBodyProviderIntf
  extends MessageBodyReader[Message]
  with MessageBodyWriter[Message] {

  override def isReadable
  (`type`: Class[_],
   genericType: Type,
   annotations: Array[Annotation],
   mediaType: MediaType): Boolean =
    classOf[Message].isAssignableFrom(`type`)

  override def getSize
  (message: Message,
   `type`: Class[_],
   genericType: Type,
   annotations: Array[Annotation],
   mediaType: MediaType): Long = -1

  override def isWriteable
  (`type`: Class[_],
   genericType: Type,
   annotations: Array[Annotation],
   mediaType: MediaType): Boolean =
    classOf[Message].isAssignableFrom(`type`)

}
