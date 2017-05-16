package nz.co.fnzc.scalasamp

import java.util
import java.util.Optional

import nz.co.fnzc.samp.MessageI
import spray.json.JsValue

trait MessageBodyWriter[A] {
  def write(b: A): Array[Byte]
}

trait DefaultMessageWriters {
  import collection.JavaConverters._

  def toOptional[T](op: Option[T]) = Optional.ofNullable(op.getOrElse(null).asInstanceOf[T])

  implicit val JsonWriter = new MessageBodyWriter[JsValue] {
    override def write(m: JsValue): Array[Byte] = m.toString.getBytes
  }

  implicit val StringWriter = new MessageBodyWriter[String] {
    override def write(b: String): Array[Byte] = b.getBytes
  }

  implicit val EmptyWriter = new MessageBodyWriter[Nothing] {
    override def write(empty: Nothing): Array[Byte] = Array[Byte]()
  }
}

object DefaultMessageWriters extends DefaultMessageWriters
