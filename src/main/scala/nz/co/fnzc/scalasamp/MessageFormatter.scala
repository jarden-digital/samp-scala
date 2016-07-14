package nz.co.fnzc.scalasamp

import java.text.SimpleDateFormat
import java.util.{Date, Optional}

import nz.co.fnzc.samp.{MessageI, Samp}

object MessageFormatter extends DefaultMessageReaders with DefaultMessageWriters {
  import collection.JavaConverters._

  def read[A](mI: MessageI)(implicit msgReader: MessageBodyReader[A]): Message[A] = {
    toOption(mI.body) match {
      case Some(body) => MessageWith[A](mI.kind(), toOption(mI.status()), mI.action(), mI.headers().asScala.toMap, msgReader.read(body))
      case None => EmptyMessage(mI.kind(), toOption(mI.status()), mI.action(), mI.headers().asScala.toMap)
    }
  }

  def write(m: Message[Array[Byte]]): MessageI = {
    val mw = new MessageBodyWriter[Array[Byte]] { override def write(mI: Array[Byte]): Array[Byte] = mI }
    write[Array[Byte]](m)(mw)
  }

  def write[A](m: Message[A])(implicit msgWriter: MessageBodyWriter[A]): MessageI = {
    val df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    val date = Some(Samp.Date -> df.format(new Date()))
    new MessageI {
      override def action() = m.action
      override def kind() = m.kind
      override def body() = m match {
        case em: EmptyMessage => Optional.empty()
        case mw: MessageWith[A] => Optional.of(msgWriter.write(mw.body))
      }
      override def status() = toOptional(m.status)
      override def headers() = (m.headers ++ date).asJava
      override def version() = ""
    }
  }
}
