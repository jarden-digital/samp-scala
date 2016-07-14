package nz.co.fnzc.scalasamp

import java.util
import java.util.Optional

import nz.co.fnzc.samp.MessageI
import spray.json.JsValue

trait MessageWriter[A] {
  def write(m: Message[A]): MessageI
}

trait DefaultMessageWriters {
  import collection.JavaConverters._

  private def toOptional[T](op: Option[T]) = Optional.ofNullable(op.getOrElse(null).asInstanceOf[T])

  implicit val JsonWriter = new MessageWriter[JsValue] {
    override def write(m: Message[JsValue]): MessageI = {
      new  MessageI {
        override def action(): String = m.action
        override def kind(): String = m.kind
        override def body(): Optional[Array[Byte]] = toOptional(m.body.map(_.toString.getBytes))
        override def status(): Optional[String] = toOptional(m.status)
        override def headers(): util.Map[String, String] = m.headers.asJava
        override def version(): String = ""
      }
    }
  }

  implicit val StringWriter = new MessageWriter[String] {
    override def write(m: Message[String]): MessageI = {
      new  MessageI {
        override def action(): String = m.action
        override def kind(): String = m.kind
        override def body(): Optional[Array[Byte]] =toOptional(m.body.map(_.getBytes))
        override def status(): Optional[String] = toOptional(m.status)
        override def headers(): util.Map[String, String] = m.headers.asJava
        override def version(): String = ""
      }
    }
  }

  implicit val ByteArrayWriter = new MessageWriter[Array[Byte]] {
    override def write(m: Message[Array[Byte]]): MessageI = {
      new  MessageI {
        override def action(): String = m.action
        override def kind(): String = m.kind
        override def body(): Optional[Array[Byte]] =toOptional(m.body)
        override def status(): Optional[String] = toOptional(m.status)
        override def headers(): util.Map[String, String] = m.headers.asJava
        override def version(): String = ""
      }
    }
  }
}

object DefaultMessageWriters extends DefaultMessageWriters
