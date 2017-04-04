package controllers.chorbi;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Chorbi;
import services.ChorbiService;

@Controller
@RequestMapping("/chorbi/chorbi")
public class ChorbiChorbiController {

	//Services

		@Autowired
		private ChorbiService	chorbiService;


		//Constructor

		public ChorbiChorbiController() {
			super();
		}

		//Register
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit() {
			ModelAndView result;
			Chorbi chorbi;

			chorbi = chorbiService.findByPrincipal();
			result = createEditModelAndView(chorbi);

			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Chorbi editChorbi, BindingResult binding) {
			ModelAndView result;
			Chorbi chorbi;

			chorbi = chorbiService.reconstruct(editChorbi, binding);
			if (binding.hasErrors()) {
				result = createEditModelAndView(editChorbi);
			} else {
				try {
					chorbi = chorbiService.register(chorbi);
					result = new ModelAndView("redirect:/chorbi/edit.do?chorbiId=" + chorbi.getId());
				} catch (Throwable oops) {
					result = createEditModelAndView(editChorbi, "chorbi.commit.error");
				}
			}
			return result;
		}
		
		// Ancillary methods
		
		protected ModelAndView createEditModelAndView(Chorbi chorbi) {
			ModelAndView result;

			result = createEditModelAndView(chorbi, null);

			return result;
		}
		protected ModelAndView createEditModelAndView(Chorbi chorbi, String message) {
			ModelAndView result;

			String requestURI = "chorbi/chorbi/edit.do";

			result = new ModelAndView("chorbi/edit");
			result.addObject("chorbi", chorbi);
			result.addObject("message", message);
			result.addObject("requestURI", requestURI);

			return result;
		}
	
}
