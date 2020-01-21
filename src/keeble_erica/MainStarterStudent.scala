package keeble_erica

import scala.io.StdIn


object MainStarterStudent extends App {

    var choice = -1

    val menu: String =
        """
          |1) Load XML
          |2) Write XML
          |3) Display data
          |4) Add data
          |5) Remove zip code
          |6) Find service
          |7) Total value insured
          |8) Insurance for
          |0) Quit
          |Choice: """.stripMargin

    while (choice != 0) {
        try {
            print(menu)
            choice = StdIn.readInt()

            Menu.handle(choice)

        } catch {
            case e: Throwable => print("")
        }
    }
}

