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

import domain.Chorbi;
import domain.Likes;
import services.ChorbiService;
import services.LikesService;

@Controller
@RequestMapping("/likes/chorbi")
public class LikesChorbiController {

	//Services

		@Autowired
		private LikesService	likesService;

		@Autowired
		private ChorbiService	chorbiService;


		//Constructor

		public LikesChorbiController() {
			super();
		}
		
		//edit
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam final int likedId) {
			ModelAndView result;
			Likes likes;
			final Chorbi liked = chorbiService.findOne(likedId);

			likes = likesService.create(liked);
			result = createEditModelAndView(likes);
			
			return result;
		}
		
		//edit
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int likesId) {
			ModelAndView result;
			Likes likes;

			likes = likesService.findByOneToEdit(likesId);
			result = createEditModelAndView(likes);

			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Likes likes, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(likes);
			} else {
				try {
					likes = likesService.save(likes);
					result = new ModelAndView("redirect:/likes/chorbi/list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(likes, "chorbi.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int likesId) {
			ModelAndView result;
			Likes likes;

			likes = likesService.findOne(likesId);
			likesService.delete(likes);
			
			result = new ModelAndView("redirect:/likes/chorbi/list.do");

			return result;
		}
		
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(@RequestParam(required = false) String errorMessage) {
			ModelAndView result;
			
			Collection<Likes> likes;
			
			likes = likesService.findAllByPrincipal();

			result = new ModelAndView("likes/list");
			result.addObject("likes", likes);

			return result;
		}
		
		//Display		
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int likesId) {
			ModelAndView result;
			Likes likes;
			
			likes = likesService.findOne(likesId);
			result = new ModelAndView("likes/display");
			
			result.addObject("likes", likes);
			
			return result;
		}
		
		// Ancillary methods
		
		protected ModelAndView createEditModelAndView(Likes likes) {
			ModelAndView result;

			result = createEditModelAndView(likes, null);

			return result;
		}
		protected ModelAndView createEditModelAndView(Likes likes, String message) {
			ModelAndView result;

			String requestURI = "likes/chorbi/edit.do";

			result = new ModelAndView("likes/edit");
			result.addObject("likes", likes);
			result.addObject("message", message);
			result.addObject("requestURI", requestURI);

			return result;
		}
	
}
