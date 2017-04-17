
package controllers.chorbi;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.LikesService;
import services.SearchTemplateService;
import domain.Chorbi;
import domain.Likes;

@Controller
@RequestMapping("/chorbi/chorbi")
public class ChorbiChorbiController {

	//Services

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private LikesService			likesService;

	@Autowired
	private SearchTemplateService	searchTemplateService;


	//Constructor

	public ChorbiChorbiController() {
		super();
	}

	//Register

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();
		result = this.createEditModelAndView(chorbi);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();

		result = new ModelAndView("chorbi/display");
		result.addObject("chorbi", chorbi);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Chorbi editChorbi, final BindingResult binding) {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.reconstruct(editChorbi, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(editChorbi);
		else
			try {
				chorbi = this.chorbiService.register(chorbi);
				result = new ModelAndView("redirect:/chorbi/chorbi/edit.do?chorbiId=" + chorbi.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(editChorbi, "chorbi.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Chorbi> chorbis;
		Collection<Likes> likes;

		chorbis = this.chorbiService.findAllNotBanned();
		likes = this.likesService.findAllByPrincipal();

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbis", chorbis);
		result.addObject("likes", likes);

		return result;
	}

	@RequestMapping(value = "/listFound", method = RequestMethod.GET)
	public ModelAndView listFound(@RequestParam(required = false, defaultValue = "0") final int searchTemplateId) {

		ModelAndView result;
		Collection<Chorbi> chorbies;
		Collection<Likes> likes;
		Chorbi principal;

		if (searchTemplateId != 0)
			chorbies = this.chorbiService.findAllFound(searchTemplateId);
		else {
			principal = this.chorbiService.findByPrincipal();
			chorbies = this.chorbiService.findAllFound(this.searchTemplateService.findSearchTemplateByChorbi(principal).getId());
		}
		likes = this.likesService.findAllByPrincipal();

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbis", chorbies);
		result.addObject("likes", likes);

		return result;

	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Chorbi chorbi) {
		ModelAndView result;

		result = this.createEditModelAndView(chorbi, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Chorbi chorbi, final String message) {
		ModelAndView result;

		final String requestURI = "chorbi/chorbi/edit.do";

		result = new ModelAndView("chorbi/edit");
		result.addObject("chorbi", chorbi);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
