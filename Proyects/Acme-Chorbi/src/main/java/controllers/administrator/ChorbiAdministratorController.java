package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Chorbi;
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
			result = new ModelAndView("redirect:/chorbi/administrator/list.do");

			return result;
		}
		
		
		//Listing
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(@RequestParam(required = false) String errorMessage) {
			ModelAndView result;

			Collection<Chorbi> chorbis;

			chorbis = chorbiService.findAll();

			result = new ModelAndView("chorbi/list");
			result.addObject("chorbis", chorbis);

			return result;
		}
	
}
