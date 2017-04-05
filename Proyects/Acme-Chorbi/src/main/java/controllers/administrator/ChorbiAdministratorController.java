package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;

@Controller
@RequestMapping("/chorbi/administrator")
public class ChorbiAdministratorController {

	//Services

		@Autowired
		private ChorbiService	chorbiService;


		//Constructor

		public ChorbiAdministratorController() {
			super();
		}

		//Register
		
		@RequestMapping(value = "/ban", method = RequestMethod.GET)
		public ModelAndView register(@RequestParam int chorbiId) {
			ModelAndView result;

			chorbiService.banChorbi(chorbiId);
			result = new ModelAndView("redirect:/chorbi/actor/list.do");

			return result;
		}
		
		
		
		// Ancillary methods
	
}
