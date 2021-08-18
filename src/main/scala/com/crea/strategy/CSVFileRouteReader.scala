package com.crea.strategy

import java.io.FileNotFoundException

import com.crea.exceptions.InvalidInputException
import com.crea.model.Route

import scala.io.Source
import scala.util.{Failure, Success, Try}

class CSVFileRouteReader extends InputRouteReaderStrategy {
  @throws[FileNotFoundException]
  @throws[InvalidInputException]
  override def readInput(source: String): List[Route] = {
    Try(Source.fromResource(source).getLines()) match {
      case Success(lines) =>
        lines.map(line => {
          val routeVector = line.split(",")
          if (routeVector.length < 3) throw new InvalidInputException
          Route(routeVector(0).toUpperCase, routeVector(1).toUpperCase, routeVector(2).toInt)
        }).toList
      case Failure(_) => throw new FileNotFoundException

    }
  }
}



