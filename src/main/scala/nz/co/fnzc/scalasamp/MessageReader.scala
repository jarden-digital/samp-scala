package nz.co.fnzc.scalasamp

import java.util.Optional

import spray.json.{JsValue, JsonParser}

trait MessageBodyReader[A] {
  def read(mI: Array[Byte]): A
}

trait DefaultMessageReaders {

  def toOption[T](op: Optional[T]): Option[T] = if (op.isPresent) Some(op.get()) else None

  implicit val JsonReader = new MessageBodyReader[JsValue] {
    override def read(mI: Array[Byte]): JsValue =  JsonParser(mI)
  }

  implicit val StringReader = new MessageBodyReader[String] {
    override def read(mI: Array[Byte]): String = new String(mI)
  }
}

object DefaultMessageReaders extends DefaultMessageReaders
