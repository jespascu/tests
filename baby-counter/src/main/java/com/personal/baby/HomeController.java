package com.personal.baby;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.baby.dao.BabyDao;
import com.personal.baby.model.Baby;
import com.personal.baby.template.TemplateResolver;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private static final String TEMPLATE_LOCATION = "templates/BabyStatusTemplate.html";
	private static final String BABY_NAME = "jefazo";
	
	@Autowired
	TemplateResolver tResolver;
	
	@Autowired
	BabyDao babyDao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	@RequestMapping(value = "/babycounter", 
			method = RequestMethod.GET,
			headers="Accept=application/json",
			produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String counter() throws IOException {
		
		if(logger.isInfoEnabled()){
			logger.info("babycounter - init");
		}
		
		Map<String, String> params = new HashMap<String, String>(3);
		List<Baby> listBaby = babyDao.findAll();
		logger.info("SIZE" + listBaby.size());
		Baby b = babyDao.findByName(BABY_NAME);
		
		Random random = new Random();
		int numCryAdd = random.nextInt(10 - 0 + 1) + 0;
		int numPooAdd = random.nextInt(10 - 0 + 1) + 0;
		
		numCryAdd = numCryAdd > 5 ? 1 : 0;
		numPooAdd = numPooAdd > 5 ? 1 : 0;
		
		b.setNumCry(b.getNumCry()+numCryAdd);
		b.setNumPoo(b.getNumPoo()+numPooAdd);
		
		babyDao.update(b);
		
		params.put("numPoo", b.getNumPoo().toString());
		params.put("numCry", b.getNumCry().toString());
		
		List<String> listStr = new ArrayList<String>(2);
		
		listStr.add("numPoo");
		listStr.add("numCry");
		tResolver.setTokensToReplace(listStr);
		
		String emailBodyStr = tResolver.resolveTemplate(params, TEMPLATE_LOCATION);

		if(logger.isDebugEnabled()){
			logger.debug("Output: {}", emailBodyStr);
		}
		if(logger.isInfoEnabled()){
			logger.info("babycounter - end");
		}
		
		return emailBodyStr;
	}
	
}
