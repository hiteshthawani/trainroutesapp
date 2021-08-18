package com.crea.strategy

import com.crea.model.Route

trait InputRouteReaderStrategy {

  def readInput(source: String): List[Route]

}
