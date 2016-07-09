package jms

import javax.jms.{ConnectionFactory, Destination, Session}
import javax.naming.InitialContext

import resource._

object Producer extends App {
  println("starting running of producer...")

  for(ctx <- managed(new InitialContext())) {
    val factory     = ctx.lookup("jmsScalaFactoryName").asInstanceOf[ConnectionFactory]
    val destination = ctx.lookup("queue/JMSScalaDemo").asInstanceOf[Destination]

    for {
      connetion <- managed(factory.createConnection("admin", "admin"))
      session   <- managed(connetion.createSession(false, Session.AUTO_ACKNOWLEDGE))
      producer  <- managed(session.createProducer(destination))
    } {

      println("Enter message to send (type exit to exit)")

      for (message <- io.Source.stdin.getLines().takeWhile(_ != "exit")) {
        producer.send(session.createTextMessage(message))
      }

      println("exiting producer...")
    }
  }
}