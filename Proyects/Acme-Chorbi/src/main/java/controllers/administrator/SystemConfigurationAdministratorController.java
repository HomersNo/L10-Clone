
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/systemConfiguration/administrator")
public class SystemConfigurationAdministratorController extends AbstractController {

	//Services

	@Autowired
	private SystemConfigurationService	scService;

	@Autowired
	private ChorbiService				chorbiService;


	//Contructor

	public SystemConfigurationAdministratorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		SystemConfiguration system;

		system = this.scService.findMain();
		result = this.createEditModelAndView(system);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SystemConfiguration system, final BindingResult binding) {
		ModelAndView result;
		final SystemConfiguration sc;
		if (binding.hasErrors())
			result = this.createEditModelAndView(system);
		else
			try {
				sc = this.scService.save(system);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(system, "system.commit.error");
			}
		return result;
	}

	//	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	//	public ModelAndView dashboard() {
	//		ModelAndView result;
	//
	//		result = new ModelAndView("systemConfiguration/dashboard");
	//
	//		return result;
	//	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final SystemConfiguration system) {
		ModelAndView result;

		result = this.createEditModelAndView(system, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final SystemConfiguration system, final String message) {
		ModelAndView result;

		final String requestURI = "systemConfiguration/administrator/edit.do";

		result = new ModelAndView("systemConfiguration/edit");
		result.addObject("system", system);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
