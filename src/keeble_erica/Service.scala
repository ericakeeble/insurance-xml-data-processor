package keeble_erica

import scala.collection.mutable
import scala.xml.{Node, Text}

class Service() extends RDP {
  //TODO builtin setters?
  //TODO Change access levels of variables!
  private var _code = 0
  private var _descrip = ""

  override def toString: String = {
    _descrip
  }

  def code:Int = { _code }
  def code_(newValue:Int):Unit = {
    _code = newValue
  }

  def descrip:String = { _descrip }
  def descrip_(newValue:String):Unit = {
    _descrip = newValue
  }

  def writeXML():Node = {
    val attr: mutable.HashMap[String, String] = mutable.HashMap(("code", _code.toString))
    XMLHelper.makeNode("service", attr, Text(_descrip))
  }

  def loadXML(node:Node):Unit = {
    //do nothing
  }

  def display():String = {
    //do nothing
    "yo"
  }

  def addData():Unit = {
    //do nothing
  }

}
