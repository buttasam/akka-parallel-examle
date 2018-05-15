package cvut.fit.buttasam

import java.util.concurrent.TimeUnit
import akka.actor.{ActorSystem, Inbox, Props}
import scala.concurrent.duration.FiniteDuration


object AkkaMainApp extends App {

  // Create the 'helloAkka' actor system
  val system: ActorSystem = ActorSystem("helloAkka")

  val parent = system.actorOf(Props[ParentActor])

  val inbox = Inbox.create(system)
  parent.tell(Sentence("xxx yyyyy zzzzzzzzz"), inbox.getRef())

  val fd = FiniteDuration.apply(5, TimeUnit.SECONDS)
  println(inbox.receive(fd))

}
