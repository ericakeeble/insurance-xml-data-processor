package keeble_erica

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.xml.Node

class Owner() extends RDP {
  var accidents = new mutable.ListBuffer[Accident]
  var cars = new mutable.ListBuffer[Car]
  var _name = ""
  def name:String = { _name }
  def name_(newVal:String):Unit = { _name = newVal }

  def getName:String = {
    name
  }

  def insuranceCalculation:Double = {
    val x = (getCarValue * 0.01) + (getNumAccidents * getCarValue * 0.01)
    x
  }

  def getCarValue:Int = {
    if(cars.nonEmpty) {
      var value = 0;
      for (car <- cars) {
        value += car.value
      }
      value
    }
    else {
      0
    }
  }

  def getNumAccidents:Int = {
    accidents.size
  }

  def addData():Unit = {
    //add cars and accidents
    print("Add Car y/n? ")
    val addCar = scala.io.StdIn.readLine()
    if(addCar == "y") {
      print("Make: ")
      val make = scala.io.StdIn.readLine()
      print("Model: ")
      val model = scala.io.StdIn.readLine()
      print("Year: ")
      val year = scala.io.StdIn.readLine()
      print("Value: ")
      val value = scala.io.StdIn.readLine()

      val newCar = new Car()
      newCar.make_(make)
      newCar.model_(model)
      newCar.year_(year.toInt)
      newCar.value_(value.toInt)
      cars += newCar
    }

    print("Add Accident y/n? ")
    val addAccident = scala.io.StdIn.readLine()
    if(addAccident == "y") {
      print("Date: ")
      val accDate = scala.io.StdIn.readLine()
      val newAcc = new Accident()
      newAcc.date_(accDate)
      accidents += newAcc
    }

  }

  override def toString: String = {
    s"---Name: $name"
  }

  def writeXML():Node = {
    //make owner node
    val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", name))

    val accidentsXml = accidents.map(x => x.writeXML())
    val carsXml = cars.map(x => x.writeXML())
    val children = carsXml ++ accidentsXml //need ALL siblings at one time

    XMLHelper.makeNode("owner", attr, children)
  }

  def display():String = {
    var carsString = cars.map(_.display()).mkString("\n")
    var accString = accidents.map(_.display()).mkString("\n")

    if(!carsString.isEmpty) {
      carsString = "------cars:" + "\n" + carsString
    }
    if(!accString.isEmpty) {
      accString = "------accidents:" + "\n" + accString
    }
    toString + "\n" + carsString + "\n" + accString

  }

  def loadXML(node:Node):Unit = {
    //add accidents and cars
    val children = node.child //grab all children

    //load new owner
    for (child <- children) {
      var tag = child.label
      tag match {
        case "car" => //add car
          val make = child.attribute("make").getOrElse("").toString
          val model = child.attribute("model").getOrElse("").toString
          val year = child.attribute("year").getOrElse("").toString.toInt
          val value = child.attribute("value").getOrElse("").toString.toInt
          val newCar = new Car()
          newCar.make_(make)
          newCar.model_(model)
          newCar.year_(year.toInt)
          newCar.value_(value.toInt)
          cars += newCar
        case "accident" => //add accident
          val date = child.child
          val d = date.text.trim()
          val newAcc = new Accident()
          newAcc.date_(d)
          accidents += newAcc
        case _ => //pass
      }
    }
  }

  def calcInsurance:Float = {
    var ins = 0
    ins
  }
}

