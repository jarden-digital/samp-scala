package nz.co.fnzc.scalasamp

import java.util.Optional

import nz.co.fnzc.samp.MessageI
import spray.json.{JsValue, JsonParser}

trait MessageReader[A] {
  def read(mI: MessageI): Message[A]
}

trait DefaultMessageReaders {
  import collection.JavaConverters._

  private def toOption[T](op: Optional[T]): Option[T] = if (op.isPresent) Some(op.get()) else None

  implicit val JsonReader = new MessageReader[JsValue] {
    override def read(mI: MessageI): Message[JsValue] = {
      val body = toOption(mI.body()).map(JsonParser(_))
      Message(mI.kind(), toOption(mI.status()), mI.action(), mI.headers().asScala.toMap, body)
    }
  }

  implicit val StringReader = new MessageReader[String] {
    override def read(mI: MessageI): Message[String] = {
      val body = toOption(mI.body()).map(new String(_))
      Message(mI.kind(), toOption(mI.status()), mI.action(), mI.headers().asScala.toMap, body)
    }
  }

  implicit val ByteArrayReader = new MessageReader[Array[Byte]] {
    override def read(mI: MessageI): Message[Array[Byte]] = {
      Message(mI.kind(), toOption(mI.status()), mI.action(), mI.headers().asScala.toMap, toOption(mI.body()))
    }
  }
}

object DefaultMessageReaders extends DefaultMessageReaders
