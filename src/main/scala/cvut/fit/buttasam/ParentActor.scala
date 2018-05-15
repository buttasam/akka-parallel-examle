package cvut.fit.buttasam

import akka.actor.{Actor, ActorRef, Props}

case class Sentence(s: String)

case class Word(s: String)

case class Result(num: Int)

class ChildActor extends Actor {

  override def receive: Receive = {
    case Word(w) => {
      sender() ! Result(w.size)
    }
  }

}

class ParentActor extends Actor {

  var sum = 0
  var counter = 0
  var user: ActorRef = _

  override def receive: Receive = {

    case Sentence(s) => {
      user = sender()
      val words = s.split(" ")
      counter = words.length

      // pro jednotliva slova vytvori aktor
      for (word <- words) {
        val childActor = context.actorOf(Props[ChildActor])
        childActor ! Word(word)

      }
    }

    case Result(num) => {
      sum += num
      counter -= 1

      if (counter == 0) {
        user ! Result(sum)
      }

    }

  }

}
