package com.loggly.frameworks.java.benchmark.runners

import com.ealva.ealvalog.Logger
import com.ealva.ealvalog.Loggers
import com.ealva.ealvalog.e
import com.ealva.ealvalog.invoke
import com.ealva.ealvalog.lazyLogger
import com.ealva.ealvalog.log4j.Log4jLoggerFactory
import org.apache.logging.log4j.core.config.Configurator

private val logger: Logger by lazyLogger(EalvalogLog4j::class)

/**
 * Created by Eric A. Snell on 8/17/18.
 */
class EalvalogLog4j : BaseRunner() {

  override fun run(iteration: Int, numRuns: Int) {
    for (run in 1..numRuns) {
      logger.e { it("Iteration %s, run %d", iteration, run) }
    }
  }

  companion object {
    fun setConfigurationFile(fileName: String) {
      System.out.println("Log file name:$fileName")
      Configurator.initialize(null, fileName)
      Loggers.setFactory(Log4jLoggerFactory)
    }
  }
}