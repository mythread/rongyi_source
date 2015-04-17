package base.config;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import base.context.SpringContextUtil;

import com.rongyi.cms.service.JobService;

public class ConfigTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ConfigTag.class);

//	@Autowired
//	private PropertyConfigurer propertyConfigurer;
	private String key = null;
	
	public int doEndTag() throws JspException {
		try {
			// 参数存session中
			JspWriter out = pageContext.getOut();
			if(StringUtils.isNotBlank(key)) {
				PropertyConfigurer propertyConfigurer = (PropertyConfigurer) SpringContextUtil.getBean("propertyPlaceholderConfigurer");
				String outValue = propertyConfigurer.getProperty(key).toString();
				out.println(outValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		return super.doStartTag();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

//	public PropertyConfigurer getPropertyConfigurer() {
//		return propertyConfigurer;
//	}
//
//	public void setPropertyConfigurer(PropertyConfigurer propertyConfigurer) {
//		this.propertyConfigurer = propertyConfigurer;
//	}
}
