package keeble_erica

import java.io.FileNotFoundException

import scala.xml.XML

object Menu {
  var insurance = new Insurance()

  def choiceEnd():Unit = {
    insurance.deleteZip()
  }

  def choiceLoadXML():Unit = {
    print("File name: ")
    val filename = scala.io.StdIn.readLine()

    //open file and parse lines
    try {
      val topNode = XML.loadFile(filename) //XML.loadFile will read in the DOM tree

      if (topNode.label != "insurance") { //.label is the "tag"
        println("invalid xml file. needs to be an insurance xml file")
        return
      }

      /************ GRADING: READ ******************/
      insurance.loadXML(topNode)
    }
    catch {
      case  e:FileNotFoundException => print("could not open file: doesnotexist.xml (the system cannot find the file specified)")
    }
  }

  def choiceWriteXML():Unit = {
    print("File name: ")
    val filename = scala.io.StdIn.readLine()

    /************ GRADING: WRITE ******************/
    val newInsurance = insurance.writeXML()
    XML.save(filename, newInsurance, "UTF-8", xmlDecl = true, null)
  }

  def choiceDisplayData():Unit = {
    println("")
    /************ GRADING: PRINT ******************/
    val display = insurance.display()
    println(display)
  }

  def choiceAddData():Unit = {
    /************ GRADING: ADD ******************/
    insurance.addData()
  }


  def choiceRemZip():Unit = {
    print("What zip code: ")
    val code = scala.io.StdIn.readLine()
    insurance.removeZip(code.toInt)
    print("Removed " + code)
  }

  def choiceFindService():Unit = {
    print("Code: ")
    val code = scala.io.StdIn.readLine()

    /************ GRADING: FIND ******************/
    val result = insurance.findService(code.toInt)

    val shop = result._1
    val service = result._2

    if(!shop.serviceFound) {
      print("Service not found")
    }
    else {
      println("\"" + shop + "\" handles code: " + code + ". Description: " + service)
    }
  }

  def choiceZipInsurance():Unit= {
    print("What zip code: ")
    val zip = scala.io.StdIn.readLine()

    //TODO Make this parallel!
    val total = insurance.calcCarVals(zip.toInt)

    val formatter = java.text.NumberFormat.getInstance
    print("Value: $" + formatter.format(total) + ".00")
  }

  def choiceOwnerInsurance():Unit = {
    print("What zip code: ")
    val code = scala.io.StdIn.readLine()
    print("What owner: ")
    val ownerName = scala.io.StdIn.readLine()

    val payment = insurance.findInsuranceFor(code.toInt, ownerName)

    val formatter = java.text.NumberFormat.getInstance
    print("Monthly payment: $" + formatter.format(payment) + ".00")
  }


  def handle(choice: Int): Unit = {
    /*
    1) Load XML
    2) Write XML
    3) Display data
    4) Add data
    5) Remove zip code
    6) Find service
    7) Total value insured
    8) Insurance for
    0) Quit
     */
    var i = 0

    choice match {
      case 1 => choiceLoadXML()
      case 2 => choiceWriteXML()
      case 3 => choiceDisplayData()
      case 4 => choiceAddData()
      case 5 => choiceRemZip()
      case 6 => choiceFindService()
      case 7 => choiceZipInsurance()
      case 8 => choiceOwnerInsurance()
      case 0 => choiceEnd()
    }

  }

}
