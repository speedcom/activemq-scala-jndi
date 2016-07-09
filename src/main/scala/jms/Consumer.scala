package jms

import javax.jms._
import javax.naming.InitialContext
import org.apache.activemq.command.ActiveMQQueue
import Helper._
import resource._
import scala.concurrent.duration._

object Consumer extends App {

  for(ctx <- managed(new InitialContext())) {
    val factory     = ctx.lookup("jmsScalaFactoryName").asInstanceOf[ConnectionFactory]
    val queue       = ctx.lookup("queue/JMSScalaDemo") .asInstanceOf[ActiveMQQueue]

    println(s"Receiving from queue with physical name: ${queue.getPhysicalName} and properties ${queue.getProperties}  ")

    for {
      connection <- managed(factory.createConnection("admin", "admin"))
      session    <- managed(connection.createSession(false, Session.AUTO_ACKNOWLEDGE))
      consumer   <- managed(session.createConsumer(queue))
    } {

      println("Starting receiving (timeout is 10s)")
      connection.start()

      receiveAndPrintText(consumer, 10.seconds)
    }
  }
}