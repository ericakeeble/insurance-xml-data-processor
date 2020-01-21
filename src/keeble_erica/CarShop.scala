package keeble_erica

import scala.collection.mutable
import scala.xml.Node

class CarShop() extends RDP {
  var nextInChain:CarShop = _
  var services = new mutable.ListBuffer[Service]
  var _name = ""
  def name:String = { _name }
  def name_(newVal:String):Unit = { _name = newVal}

  def addData():Unit = {
    //does nothing
  }

  def addNext(next:CarShop):Unit = {
    if(nextInChain == null) {
      nextInChain = next
    }
    else {
      nextInChain.addNext(next)
    }
  }

  def writeXML():Node = {
    //make owner node
    val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", name))

    val children = services.map(x => x.writeXML())
    XMLHelper.makeNode("carShop", attr, children)
  }

  def loadXML(node:Node):Unit = {
    val children = node.child //grab all children

    //load new service
    for (child <- children) {
      var tag = child.label
      tag match {
        case "service" => //add service
          val code = child.attribute("code").getOrElse("").toString.toInt
          val descrip = child.text
          val newService = new Service()
          newService.code_(code)
          newService.descrip_(descrip)
          services += newService
        case _ => //pass
      }
    }
  }

  def findService(code:Int):(CarShop, Service) = {
    //see if this shop has the service we want
    val matchingService = services.filter(_.code==code)
    if(matchingService.nonEmpty) {
      return (this, matchingService.head)
    }
      else {
      if(nextInChain!=null) {
        /************ GRADING: CHAIN ******************/
        return nextInChain.findService(code)
      }
      else {
        var newShop = new CarShop()
        newShop.name_("failed")
        var newService = new Service()
        newService.code_(-1)
        newService.descrip_("")
        (newShop, newService)
      }
    }
  }

  def serviceFound:Boolean = {
    if(name=="failed") {
      return false
    }
    else {
      return true
    }
  }

  override def toString: String = {
    name
  }

  def display():String = {
    //do nothing
    "hi"
  }

}
