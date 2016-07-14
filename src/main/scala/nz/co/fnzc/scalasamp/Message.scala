package nz.co.fnzc.scalasamp

import nz.co.fnzc.samp.MessageI

case class Message[T](kind: String,
                      status: Option[String],
                      action: String,
                      headers: Map[String, String],
                      body: Option[T])


object Message extends DefaultMessageReaders with DefaultMessageWriters {

  def read[A](mI: MessageI)(implicit msgReader: MessageReader[A]): Message[A] = msgReader.read(mI)

  def write[A](m: Message[A])(implicit msgWriter: MessageWriter[A]): MessageI = msgWriter.write(m)
}
