import nz.co.fnzc.samp.Samp
import nz.co.fnzc.scalasamp.{EmptyMessage, MessageFormatter}
import org.scalatest.{FlatSpec, Matchers}

import collection.JavaConverters._

class MessageFormatterSpec extends FlatSpec with Matchers {

  "MessageFormatter" should "write default CorrelationsId and Date" in {
    val msg = EmptyMessage(Samp.Event, None, "test", Map.empty)
    val mI = MessageFormatter.write(msg)
    val headers = mI.headers().asScala
    headers.keys should contain(Samp.CorrelationId)
    headers.keys should contain(Samp.Date)
  }

  it should "not override CorrelationId or Date if they are already set" in {
    val headers = Map(Samp.CorrelationId -> "CorrelationId", Samp.Date -> "Date")
    val msg = EmptyMessage(Samp.Event, None, "test", headers)
    val mI = MessageFormatter.write(msg)
    mI.headers().asScala should equal(headers)
  }
}
