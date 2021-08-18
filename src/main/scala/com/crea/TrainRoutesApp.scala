package com.crea

import com.crea.model.Route
import com.crea.strategy.{CSVFileRouteReader, ShortestPathFinder}
import com.crea.utils.InputUtils._

import scala.io.StdIn.readLine
import scala.util.{Failure, Success}

object TrainRoutesApp {

  def main(args: Array[String]) {
    val inputFileName = getInputFileName(args)
    val inputReader = new CSVFileRouteReader()
    val input: List[Route] = inputReader.readInput(inputFileName)
    while (true) {
      val src = readLine()
      val dest = readLine()
      val routeFinder = new ShortestPathFinder(input)
      val result = routeFinder.findDistance(src, dest)
      result match {
        case Success(None) => println(s"Result: No Routes from $src to $dest")
        case Success(Some(distanceVector)) => println(s"Result: ${distanceVector.hops} stops,${distanceVector.timeInMinutes} minutes")
        case Failure(exception) => println(exception.getMessage)
      }
    }
  }

}
