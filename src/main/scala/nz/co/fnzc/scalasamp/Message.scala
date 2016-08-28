package nz.co.fnzc.scalasamp

import nz.co.fnzc.samp.Samp

sealed trait Message[+A] {
  def kind: String
  def status: Option[String]
  def action: String
  def headers: Map[String, String]

  def response[B](kind: String, status: Option[String], action: String, tracePath: Option[String]): Message[A] = {
    val copiedHeaders = this.headers.filterKeys(Seq(Samp.CorrelationId, Samp.From).contains)
    val trace = this.headers.get(Samp.Trace).map(t => Samp.appendTracePath(t, tracePath.getOrElse("?")))
    val updatedHeaders = copiedHeaders ++ tracePath.map(Samp.Trace -> _) ++ trace.map(Samp.Trace -> _)
    EmptyMessage(kind, status, action, updatedHeaders)
  }

  def withBody[B](body: B) = {
    MessageWith[B](this.kind, this.status, this.action, this.headers, body)
  }
}

object Message {
  def apply(status: String, action: String, tracePath: String) = {
    val headers = Map(Samp.Trace -> tracePath)
    EmptyMessage(Samp.Event, Some(status), action, headers)
  }
}

final case class MessageWith[+A](kind: String,
                          status: Option[String],
                          action: String,
                          headers: Map[String, String],
                          body: A) extends Message[A] {
}

final case class EmptyMessage(kind: String,
                        status: Option[String],
                        action: String,
                        headers: Map[String, String]
                       ) extends Message[Nothing] {
}
