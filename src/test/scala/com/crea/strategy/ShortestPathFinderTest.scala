package com.crea.strategy

import com.crea.exceptions.StationDoesNotExistException
import com.crea.model.{DistanceVector, Route}
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}

import scala.util.{Failure, Success}

class ShortestPathFinderTest extends FunSuite with Matchers with BeforeAndAfterAll {


  var routeList = List.empty[Route]
  override def beforeAll(): Unit = {
    routeList = List(
      Route("A", "B", 5),
      Route("X", "Y", 5),
      Route("A", "D", 3),
      Route("D", "B", 1),
      Route("B", "C", 1)
    )
  }

  test("given invalid source station,findDistance should throw StationDoesNotExistException") {
    val shortestPathFinder = new ShortestPathFinder(routeList)
    val expected = StationDoesNotExistException("invalidStation")
    val result = shortestPathFinder.findDistance("invalidStation", "b")
    assert(result.isFailure)
    result should be(Failure(expected))
  }

  test("given invalid destination station,findDistance should throw StationDoesNotExistException") {
    val shortestPathFinder = new ShortestPathFinder(routeList)
    val expected = StationDoesNotExistException("invalidStation")
    val result = shortestPathFinder.findDistance("a", "invalidStation")
    assert(result.isFailure)
    result should be(Failure(expected))
  }

  test("findDistance should return None if route does not exist between source and destination") {
    val shortestPathFinder = new ShortestPathFinder(routeList)
    val result = shortestPathFinder.findDistance("a", "x")
    assert(result.isSuccess)
    result should be(Success(None))
  }

  test("findDistance should return valid DistanceVector if route exists between source and destination") {
    val shortestPathFinder = new ShortestPathFinder(routeList)
    val result = shortestPathFinder.findDistance("a", "b")
    val expected = Some(DistanceVector(4, 1))
    assert(result.isSuccess)
    result should be(Success(expected))
  }


}