package jms

import javax.jms._

import scala.annotation.tailrec
import scala.concurrent.duration._

import resource._

object Helper {

  val receiveAndPrintText = receiveText(_: MessageConsumer, _: Duration) {
    m => println(s"received ${m.getText}")
  }


  @tailrec
  def receiveText(consumer: MessageConsumer, timeOut: Duration = 10.seconds)(txtReceive: TextMessage => Unit): Unit = {
    Option(consumer.receive(timeOut.toMillis)) match {
      case Some(m: TextMessage) =>
        txtReceive(m)
        receiveText(consumer, timeOut)(txtReceive)
      case Some(m: Message) =>
        println("Received a non-text message")
        receiveText(consumer, timeOut)(txtReceive)
      case None => println("timed out")
    }
  }


}