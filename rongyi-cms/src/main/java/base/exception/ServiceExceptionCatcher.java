package base.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

public class ServiceExceptionCatcher {

	protected static final Log log = LogFactory
			.getLog(ServiceExceptionCatcher.class);

	public void log(JoinPoint proxy, Throwable exception)
			throws ServiceException {

		if (exception instanceof ServiceException)
			return;

		Object target = proxy.getTarget();
		log.error(target, exception);

		throw new ServiceException(exception);
	}
}
