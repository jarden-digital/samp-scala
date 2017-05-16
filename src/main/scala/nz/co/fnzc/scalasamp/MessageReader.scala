package nz.co.fnzc.scalasamp

import java.util.Optional

import spray.json.{JsValue, JsonParser}

trait MessageBodyReader[A] {
  def read(body: Array[Byte]): A
}

trait DefaultMessageReaders {

  def toOption[T](op: Optional[T]): Option[T] = if (op.isPresent) Some(op.get()) else None

  implicit val JsonReader = new MessageBodyReader[JsValue] {
    override def read(body: Array[Byte]): JsValue =  JsonParser(body)
  }

  implicit val StringReader = new MessageBodyReader[String] {
    override def read(body: Array[Byte]): String = new String(body)
  }

  /**
    * pass through reader, no processing done to body
    */
  implicit val ArrayByteReader = new MessageBodyReader[Array[Byte]] {
    override def read(body: Array[Byte]): Array[Byte] = body
  }
}

object DefaultMessageReaders extends DefaultMessageReaders
