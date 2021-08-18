package com.crea.exceptions

case class StationDoesNotExistException(stationName: String) extends RuntimeException {
  override def getMessage = s"Station $stationName does not exist"
}



