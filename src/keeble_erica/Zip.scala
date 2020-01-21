package keeble_erica

import scala.collection.mutable
import scala.xml.Node

class Zip() {
  var _code = 0
  var owners = new mutable.ListBuffer[Owner]

  def getCarVals:Double = {
    // ********** GRADING: TOTAL *************
    val parOwners = owners.par
    parOwners.map(_.getCarValue).sum
  }

  def findOwner(name:String):Owner = {
    // ********* GRADING: INSURANCE ************
    val parOwners = owners.par
    parOwners.filter(_.getName.toLowerCase()==name.toLowerCase()).head
  }

  def code:Int = {
    _code
  }

  def code_(newValue:Int):Unit = {
    _code = newValue
  }

  def addData():Unit = {
    //get owners
    print("What owner: ")
    val name = scala.io.StdIn.readLine()

    val existingOwner = owners.filter(_.getName.toLowerCase()==name.toLowerCase())

    if(existingOwner.nonEmpty) {
      existingOwner.head.addData()
    }
    else {
      val newOwner = new Owner()
      newOwner.name_(name)
      owners += newOwner
      println("Added owner")
    }
  }

  override def toString: String = {
    s"Zip Code: $code"
  }

  def writeXML():Node = {
    //make owner node
    val attr: mutable.HashMap[String, String] = mutable.HashMap(("code", code.toString))
    val children = owners.map(x => x.writeXML())

    XMLHelper.makeNode("zip", attr, children)
  }


  def display():String = {
    //owners.head.display()
    val test = owners.map(_.display())
    toString + "\n" + test.mkString("\n")

  }

  def loadXML(node:Node):Unit = {
    val children = node.child //grab all children

    //load new owner
    for (child <- children) {
      var tag = child.label
      tag match {
        case "owner" =>
          val name = child.attribute("name").getOrElse("").toString
          val newOwner = new Owner()
          newOwner.name_(name)
          owners += newOwner
          newOwner.loadXML(child)
        case _ => //pass
      }
    }
  }

  def deleteZip():Unit = {
    //do nothing. Just here to satisfy the OOP diagram
  }
}
