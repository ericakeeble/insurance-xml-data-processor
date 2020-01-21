package keeble_erica

import scala.xml.{Elem, Node, Null, Text, TopScope}

class Accident() extends RDP {
  var _date = ""
  def date:String = { _date }
  def date_(newVal:String):Unit = { _date = newVal }

  override def toString: String = {
    s"---------Accident date: $date"
  }

  def writeXML():Node = {
    def dateNode =  Elem(null, "date", Null, TopScope, Text(date))
    XMLHelper.makeNode("accident", null, dateNode)
  }

  def display():String = {
    toString
  }

  def loadXML(node:Node):Unit = {
    //do nothing
  }

  def addData():Unit = {
    //do nothing
  }

}
