import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

appender("STDOUT", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{56} - %msg%n"
  }
}
logger("org.apache.jena.util", INFO)
logger("org.apache.jena.sparql.core.mem", INFO)
root(DEBUG, ["STDOUT"])