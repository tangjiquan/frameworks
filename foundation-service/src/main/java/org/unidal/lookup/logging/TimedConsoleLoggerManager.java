package org.unidal.lookup.logging;

import java.util.HashSet;
import java.util.Set;

import org.unidal.lookup.annotation.Named;

@Named(type = LoggerManager.class)
public class TimedConsoleLoggerManager extends AbstractLoggerManager {
	private static Set<String> s_skipedClassNames = new HashSet<String>();

	private String m_dateFormat = "MM-dd HH:mm:ss.SSS";

	private String m_logFilePattern;

	private String m_baseDirRef;

	private boolean m_showClass = true;

	private boolean m_devMode;

	private String m_defaultBaseDir;

	private int m_threshold = Logger.LEVEL_INFO;

	private AbstractLogger m_logger;

	public static boolean shouldSkipClass(String className) {
		return s_skipedClassNames.contains(className);
	}

	public static void skipClass(Class<?> clazz) {
		s_skipedClassNames.add(clazz.getName());
	}

	@Override
	public AbstractLogger createLogger(String name) {
		if (m_logger == null) {
			synchronized (this) {
				if (m_logger == null) {
					TimedConsoleLogger logger = new TimedConsoleLogger(m_threshold, name, m_dateFormat, m_logFilePattern,
					      m_showClass, m_devMode);

					logger.setBaseDirRef(m_baseDirRef);
					logger.setDefaultBaseDir(m_defaultBaseDir);
					m_logger = logger;
				}
			}
		}

		return m_logger;
	}

	public void setBaseDirRef(String baseDirRef) {
		m_baseDirRef = baseDirRef;
	}

	public void setDateFormat(String dateFormat) {
		m_dateFormat = dateFormat;
	}

	public void setDefaultBaseDir(String defaultBaseDir) {
		m_defaultBaseDir = defaultBaseDir;
	}

	public void setDevMode(boolean devMode) {
		m_devMode = devMode;
	}

	public void setLogFilePattern(String logFilePattern) {
		m_logFilePattern = logFilePattern;
	}

	public void setShowClass(boolean showClass) {
		m_showClass = showClass;
	}
}
