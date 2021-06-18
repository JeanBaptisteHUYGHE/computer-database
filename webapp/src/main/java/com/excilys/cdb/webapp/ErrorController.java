package com.excilys.cdb.webapp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {
	
	private Logger logger;
	private MessageSource messageSource;
	
	public ErrorController(MessageSource messageSource) {
		logger = LoggerFactory.getLogger(ErrorController.class);
		this.messageSource = messageSource;
	}
	
	@RequestMapping(path = "/error")
    public String handle(HttpServletRequest request, 
    		@RequestParam(name = "errorMessage", required = false, defaultValue = "") String errorMessage,
    		Model model) {
		
		Integer errorCode = null;
		if (errorMessage.isBlank()) {
			String strErrorCode = request.getAttribute("javax.servlet.error.status_code").toString();
			errorCode = Integer.valueOf(strErrorCode);
			errorMessage = getMessage(errorCode);
		}
		
        logger.debug("Error {}: {}", errorCode, errorMessage);
        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorMessage", errorMessage);

        return "/WEB-INF/jsp/error.jsp";
    }
	
	/**
	 * Return the correspondent error message of the code in the good language.
	 * @param errorCode the html error code
	 * @return the correspondent error message
	 */
	private String getMessage(Integer errorCode) {
		String message = null;
		
		switch (errorCode) {
		
			case 403:
				message = messageSource.getMessage("label.error.403", null, LocaleContextHolder.getLocale());
				break;
			
			case 404:
				message = messageSource.getMessage("label.error.404", null, LocaleContextHolder.getLocale());
				break;
				
			case 500:
				message = messageSource.getMessage("label.error.500", null, LocaleContextHolder.getLocale());
				break;
				
			default:
				message = messageSource.getMessage("label.error.unknow", null, LocaleContextHolder.getLocale());
				break;
		}
		
		return message;
	}	
}
