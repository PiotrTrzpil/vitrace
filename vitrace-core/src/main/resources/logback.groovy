import ch.qos.logback.classic.boolex.GEventEvaluator
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.filter.EvaluatorFilter
import ch.qos.logback.core.status.OnConsoleStatusListener

import static ch.qos.logback.classic.Level.TRACE
import static ch.qos.logback.core.spi.FilterReply.ACCEPT
import static ch.qos.logback.core.spi.FilterReply.DENY

statusListener OnConsoleStatusListener

setupAppenders();

def setupAppenders() 
{
    def logpattern = "%-5level %d{HH:mm:ss.SSS} %-12logger{12} [ip:%X{req.remoteHost}] [user:%X{username}] - %msg%n"
    def logfileDate = timestamp('yyyy-MM-dd') // Formatted current date.
    // hostname is a binding variable injected by Logback.
    appender('logfile', FileAppender)
	{
        file = "logs/${logfileDate}.log"
        encoder(PatternLayoutEncoder) {
            pattern = logpattern
        }


    }

    appender('systemOut', ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = logpattern
        }
        filter(EvaluatorFilter) {
            evaluator(GEventEvaluator) {
                expression = '(e.level.toInt() >= TRACE.toInt() && e.loggerName.startsWith("org.springframework"))'+
                        '|| (e.level.toInt() >= TRACE.toInt() && e.loggerName.startsWith("pt"))'
            }
            onMismatch = DENY
            onMatch = ACCEPT
        }
    }
}

//, ['systemOut','logfile']
root(TRACE)
logger('net.vitrace', TRACE, ['logfile','systemOut'])
logger("org.springframework", TRACE, ['logfile','systemOut'])

