package keeble_erica

import scala.collection.mutable
import scala.xml.Node

class Insurance extends RDP {
  var zips = new mutable.ListBuffer[Zip]
  var shops = new mutable.ListBuffer[CarShop]
  var firstShop:CarShop = _

  def display():String = {
    val test = zips.map(_.display())
    test.mkString("\n")
  }

  def removeZip(code: Int):Unit = {
    //creates a new list that does not contain any zips with the given code
    zips = zips.filter(_.code!=code)
  }

  def addData():Unit = {
    print("What zip code: ")
    val code = scala.io.StdIn.readLine().toInt
    //search zip codes for code
    val existingZip = zips.filter(_.code==code)

    if(existingZip.size == 1) {
      existingZip.head.addData()
    }
    else {
      val newZip = new Zip()
      // TODO Also does this zip update correctly
      newZip.code_(code)
      zips += newZip
      println("Added zip code")
    }
  }

  def writeXML():Node = {
    val zipsXml = zips.map(x => x.writeXML())
    val shopsXml = shops.map(x => x.writeXML())
    val children = zipsXml ++ shopsXml //need ALL siblings at one time

    XMLHelper.makeNode("insurance", null, children)
  }

  def loadXML(node:Node): Unit = {
    val children = node.child //grab all children

    for (child <- children) {
      var tag = child.label
      tag match {
        case "zip" => //add new zip code
          val newCode = child.attribute("code")
          val newCodeInt = newCode.getOrElse("").toString.toInt
          val newZip = new Zip()
          // TODO does this set correctly??
          newZip.code_(newCodeInt)
          zips += newZip
          newZip.loadXML(child)
        case "carShop" => //add new carShop
          val newShopName = (child.attribute("name")).getOrElse("none").toString
          val newShop = new CarShop()
          newShop.name_(newShopName)
          if (firstShop == null) {
            firstShop = newShop
          }
          else {
            firstShop.addNext(newShop)
          }
          shops += newShop
          newShop.loadXML(child)
        case "owner" => //call owner.loadXML
        case _ => //pass
      }
    }
  }

  def deleteZip(): Unit = {
    zips.clear()
  }

  def findService(code:Int):(CarShop, Service) = {
    //val shop = new CarShop("dummyShop")
    //shop
    firstShop.findService(code)
  }

  //find total insurance for a person
  def findInsuranceFor(code: Int, person: String): Double = {
    val zipOfInterest = zips.filter(_.code==code)

    val ownerOfInterest = zipOfInterest.head.findOwner(person)

    getInsCost(ownerOfInterest)
  }

  def getInsCost(owner:Owner):Double = {
    val carVal = owner.getCarValue
    val numAccidents = owner.getNumAccidents

    (carVal * 0.01) + (numAccidents * carVal * 0.01)
  }

  def calcCarVals(code:Int):Double = {
    val zipOfInterest = zips.filter(_.code==code)
    if(zipOfInterest.nonEmpty) {
      zipOfInterest.head.getCarVals
    }
    else {
      0
    }
  }
}
