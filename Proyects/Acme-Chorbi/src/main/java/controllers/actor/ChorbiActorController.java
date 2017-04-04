package controllers.actor;

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
@RequestMapping("/chorbi/actor")
public class ChorbiActorController {

	//Services

		@Autowired
		private ChorbiService	chorbiService;


		//Constructor

		public ChorbiActorController() {
			super();
		}

		//Listing
		
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(@RequestParam(required = false) String errorMessage) {
			ModelAndView result;

			Collection<Chorbi> chorbis;

			chorbis = chorbiService.findAllNotBanned();

			result = new ModelAndView("chorbi/list");
			result.addObject("chorbis", chorbis);
			result.addObject("errorMessage", errorMessage);

			return result;
		}
		
		// Ancillary methods
		
		
}
