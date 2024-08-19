package cn.taskeren.op

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.MarkerManager

/**
 * This is a util class that provides logger and marker factory.
 *
 * You can also directly `implements` this class to get the [logger].
 */
interface OP_Logger {

	val logger: Logger get() = LogManager.getLogger("OP:"+this::class.java.simpleName)

	companion object {
		fun logger(name: String): Logger = LogManager.getLogger(name)
		fun marker(name: String): Marker = MarkerManager.getMarker(name)
	}

}
