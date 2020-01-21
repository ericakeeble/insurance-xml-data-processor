package keeble_erica

import scala.xml.{Elem, Node}

trait RDP {
  def loadXML(node:Node):Unit

  def writeXML():Node

  def display():String

  def addData():Unit
}
