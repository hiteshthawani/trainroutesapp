package com.crea.strategy

import com.crea.exceptions.InvalidInputException
import com.crea.model.Route
import org.scalatest.{FunSuite, Matchers}

class CSVFileRouteReaderTest extends FunSuite with Matchers {

  test("given valid input file with valid routes" +
    "readInput should parse the file and return the list of routes") {
    val fileName = "valid_input.csv"
    val fileReader = new CSVFileRouteReader()
    val actual = fileReader.readInput(fileName)
    val expected = List(Route("A", "B", 5))
    actual should be(expected)
  }

  test("given valid input file with incomplete routes" +
    "readInput should parse the file and throw invalid input exception") {
    val fileName = "incomplete_routes.csv"
    val fileReader = new CSVFileRouteReader()
    an[InvalidInputException] should be thrownBy fileReader.readInput(fileName)
  }

  test("given empty input file" +
    "readInput should parse the file and return empty route list") {
    val fileName = "empty_file.csv"
    val fileReader = new CSVFileRouteReader()
    val actual = fileReader.readInput(fileName)
    val expected = List.empty[Route]
    actual should be(expected)
  }

}
