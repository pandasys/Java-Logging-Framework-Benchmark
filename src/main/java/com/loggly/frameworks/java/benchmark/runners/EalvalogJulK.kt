package com.loggly.frameworks.java.benchmark.runners

import com.ealva.ealvalog.LogLevel
import com.ealva.ealvalog.Loggers
import com.ealva.ealvalog.core.ExtRecordFormatter
import com.ealva.ealvalog.filter.AlwaysAcceptFilter
import com.ealva.ealvalog.i
import com.ealva.ealvalog.java.JLogger
import com.ealva.ealvalog.java.JLoggers
import com.ealva.ealvalog.jul.FriendlyFileHandler
import com.ealva.ealvalog.jul.JdkLoggerFactory
import java.io.File
import java.io.IOException
import java.util.logging.Handler

/**
 * Created by Eric A. Snell on 8/17/18.
 */
class EalvalogJulK : BaseRunner() {
  private val logger: JLogger = JLoggers.get(EalvalogJulK::class.java)

  override fun run(iteration: Int, numRuns: Int) {
    for (run in 1..numRuns) {
      logger.i { it.log("Iteration %s, run %d", iteration, run) }
    }
  }

  companion object {

    fun setConfigurationFile(@Suppress("UNUSED_PARAMETER") configFile: String) {
      Loggers.setFactory(JdkLoggerFactory)
      val rootLogger = JdkLoggerFactory.root
      rootLogger.logLevel = LogLevel.INFO
      rootLogger.addHandler(makeFileHandler())
    }

    private fun makeFileHandler(): Handler {
      try {
        return FriendlyFileHandler(
          File("logs", "alvalog.%g.%u.log").absolutePath,
          Integer.MAX_VALUE,
          1,
          false,
          ExtRecordFormatter.TYPICAL_FORMAT,
          true,
          AlwaysAcceptFilter,
          null
        )
      } catch (e: IOException) {
        throw RuntimeException(e)
      }

    }
  }
}
