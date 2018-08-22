package com.loggly.frameworks.java.benchmark.runners;

import com.ealva.ealvalog.LogLevel;
import com.ealva.ealvalog.Loggers;
import com.ealva.ealvalog.core.ExtRecordFormatter;
import com.ealva.ealvalog.filter.AlwaysAcceptFilter;
import com.ealva.ealvalog.java.JLogger;
import com.ealva.ealvalog.java.JLoggers;
import com.ealva.ealvalog.jul.FriendlyFileHandler;
import com.ealva.ealvalog.jul.JdkLogger;
import com.ealva.ealvalog.jul.JdkLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;

/**
 * Created by Eric A. Snell on 8/17/18.
 */
public class EalvalogJul extends BaseRunner {
  private final JLogger LOG;

  public static void setConfigurationFile(final String configFile) {
    Loggers.INSTANCE.setFactory(JdkLoggerFactory.INSTANCE);
    JdkLogger rootLogger = JdkLoggerFactory.INSTANCE.getRoot();
    rootLogger.setLogLevel(LogLevel.INFO);
    rootLogger.addHandler(makeFileHandler());
  }

  public EalvalogJul() {
    LOG = JLoggers.get(EalvalogJul.class);
  }

  @Override public void run(final int iteration, final int numRuns) {
    for (int run = 1; run <= numRuns; run++) {
      LOG.log(LogLevel.INFO, "Iteration %s, run %d", iteration, run);
    }
  }

  private static Handler makeFileHandler() {
    try {
      return new FriendlyFileHandler(
          new File("logs", "alvalog.%g.%u.log").getAbsolutePath(),
          Integer.MAX_VALUE,
          1,
          false,
          ExtRecordFormatter.TYPICAL_FORMAT,
          true,
          AlwaysAcceptFilter.INSTANCE,
          null);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
