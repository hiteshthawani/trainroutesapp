package com.crea.strategy

import com.crea.exceptions.StationDoesNotExistException
import com.crea.model.{DistanceVector, Route}

import scala.util.Try

trait RouteFinderStrategy {
  @throws[StationDoesNotExistException]
  def findDistance(src: String, dest: String): Try[Option[DistanceVector]]
}
