package base.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

/**
 * 图片上传超过最大限制的时候，会被调用 2014.4.10
 * 
 * @author lim
 * 
 */
public class SystemExceptionResolver extends AbstractHandlerExceptionResolver {
	private Logger logger = Logger.getLogger(this.getClass());
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		if (ex instanceof MaxUploadSizeExceededException) {// 图片上传超过最大限制的时候，会被调用
			long fileSize = ((MaxUploadSizeExceededException) ex).getMaxUploadSize();
			// System.out.println(fileSize);
			String error = "文件超过限制大小,最大限制为".concat("" + fileSize / (1024)).concat("KB");
			String message = "{\"status\":\"0\",\"msg\":\"".concat(error).concat("\"}");
			response.setContentType("text/html;charset=UTF-8");
			setResponse(response, -1, message);
			return new ModelAndView();
		} else {
			logger.info("\n----------------------------------------\nsome error happen\n----------------------------------------");
			ex.printStackTrace();
			return new ModelAndView("/error");
		}

	}

	/**
	 * 设置返回的Response信息
	 * 
	 * @param response
	 * @param errorCode
	 * @param errorMsg
	 */
	private void setResponse(HttpServletResponse response, int errorCode, String errorMsg) {
		if (-1 != errorCode)
			response.setStatus(errorCode);
		if (errorMsg != null)
			printJSON(response, errorMsg);
	}

	/**
	 * 设置Response 的 responseText值
	 * 
	 * @param response
	 * @param errorMsg
	 */
	private void printJSON(HttpServletResponse response, String errorMsg) {
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(errorMsg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}