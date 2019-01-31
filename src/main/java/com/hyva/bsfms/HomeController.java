package com.hyva.bsfms;
import com.hyva.bsfms.bs.bsservice.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@Autowired
	BasicDataService basicDataService;

	@RequestMapping("/home")
	public String home(@CookieValue(value = "accessToken", required = false) String accessToken, Model model) throws Exception {

			if (accessToken!=null) {
				model.addAttribute("pageName", "login");
			}
			else {
				model.addAttribute("pageName", "dashboard");
			}
			basicDataService.pushBasicData();
		return "index";
	}
}
