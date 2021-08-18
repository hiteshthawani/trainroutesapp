package com.crea.utils

import com.crea.exceptions.InputFileNameMissing

object InputUtils {
  def getInputFileName(args: Array[String]): String = {
    if (args.isEmpty)
      throw new InputFileNameMissing
    else {
      val fileArgs = args(0).toString.split("--file=")
      if (fileArgs.isEmpty || fileArgs.last.isEmpty)
        throw new InputFileNameMissing
      else
        args(0).split("--file=").last
    }
  }
}
