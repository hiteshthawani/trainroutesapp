package com.crea.strategy

import com.crea.exceptions.StationDoesNotExistException
import com.crea.model.{DistanceVector, Route}

import scala.collection.mutable
import scala.util.Try

class ShortestPathFinder(routeList: List[Route]) extends RouteFinderStrategy {
  @throws[StationDoesNotExistException]
  override def findDistance(src: String, dest: String): Try[Option[DistanceVector]] = {

    val connectivityGraphOFStations: Map[String, Map[String, Int]] =
      routeList
        .groupBy(_.src)
        .map {
          case (station: String, routeList: List[Route]) => (station, routeList.map(r => (r.dest, r.distance)).toMap)
        }

    findShortestPath(connectivityGraphOFStations, src, dest)
  }

  @throws[StationDoesNotExistException]
  private def findShortestPath(graph: Map[String, Map[String, Int]],
                               src: String,
                               dest: String): Try[Option[DistanceVector]] = Try {
    val allStations: Set[String] = graph.keySet ++ graph.values.flatMap(_.keys).toSet
    val normalizedSourceStationName = src.toUpperCase().trim
    val normalizedDestinationStationName = dest.toUpperCase().trim
    if (!allStations.contains(normalizedSourceStationName)) throw new StationDoesNotExistException(src)
    if (!allStations.contains(normalizedDestinationStationName)) throw new StationDoesNotExistException(dest)

    val nearestDestinationQue = new mutable.PriorityQueue[(String, Int)]()(Ordering.by(-_._2))
    val distanceVectorMap: mutable.Map[String, DistanceVector] = allStations
      .map(station => (station, DistanceVector()))
      .to(collection.mutable.Map)


    distanceVectorMap.put(normalizedSourceStationName, DistanceVector(timeInMinutes = 0))
    nearestDestinationQue.enqueue((normalizedSourceStationName, 0))

    while (nearestDestinationQue.nonEmpty) {
      val (currentNode, currentDistance) = nearestDestinationQue.dequeue()

      if (currentDistance <= distanceVectorMap(currentNode).timeInMinutes) {
        for ((nextNode, distance) <- graph.getOrElse(currentNode, mutable.Map.empty)) {
          val totalTime = distanceVectorMap(currentNode).timeInMinutes + distance
          val totalHops = distanceVectorMap(currentNode).hops + 1
          if (distanceVectorMap(nextNode).timeInMinutes > totalTime) {
            distanceVectorMap.put(nextNode, DistanceVector(totalTime, totalHops))
            nearestDestinationQue.enqueue(((nextNode, totalTime)))
          }
        }
      }


    }

    distanceVectorMap
      .get(normalizedDestinationStationName)
      .filter(filterUnreachableDistanceVector)
  }

  private def filterUnreachableDistanceVector(dv: DistanceVector): Boolean =
    dv.timeInMinutes != Int.MaxValue

}
