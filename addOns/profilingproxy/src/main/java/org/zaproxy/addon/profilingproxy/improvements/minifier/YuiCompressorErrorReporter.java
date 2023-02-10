package org.zaproxy.addon.profilingproxy.improvements.minifier;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 * This class generates warning messages when syntax errors are found in javascript code.
 */
public class YuiCompressorErrorReporter implements ErrorReporter {
    @Override
    public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
//        if (line < 0) {
//            logger.log(Level.WARNING, message);
//        } else {
//            logger.log(Level.WARNING, line + ':' + lineOffset + ':' + message);
//        }
    }
    @Override
    public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
//        if (line < 0) {
//            logger.log(Level.SEVERE, message);
//        } else {
//            logger.log(Level.SEVERE, line + ':' + lineOffset + ':' + message);
//        }
    }

    @Override
    public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
        error(message, sourceName, line, lineSource, lineOffset);
        return new EvaluatorException(message);
    }
}
