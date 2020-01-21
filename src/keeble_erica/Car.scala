package keeble_erica

import scala.collection.mutable
import scala.xml.Node

class Car() extends RDP {
  var _make = ""
  var _model = ""
  var _year = 0
  var _value = 0

  def make:String = { _make }
  def make_(newVal:String):Unit = {
    _make = newVal
  }

  def model:String = { _model }
  def model_(newVal:String):Unit = {
    _model = newVal
  }

  def year:Int = { _year }
  def year_(newVal:Int):Unit = {
    _year = newVal
  }

  def value:Int = { _value }
  def value_(newVal:Int):Unit = {
    _value = newVal
  }

  override def toString: String = {
    val pmake = s"---------Make: $make"
    val pmodel = s"Model: $model"
    val pyear = s"Year: $year"
    val pvalue = "Value: $" + s"$value"

    var s1 = f"$pmake%-30s"
    var s2 = f"$pmodel%-22s"
    var s3 = f"$pyear%-20s "
    var s4 = f"$pvalue%-20s"

    s1 + s2 + s3 + s4
  }

  def display():String = {
    toString
  }

  def writeXML():Node = {
    //make car node
    val attr: mutable.HashMap[String, String] = mutable.HashMap(("make", _make), ("model", _model), ("year", _year.toString), ("value", _value.toString))
    XMLHelper.makeNode("car", attr)
  }

  def addData():Unit = {
    //do nothing
  }

  def loadXML(node:Node):Unit = {
    //do nothing
  }

}
