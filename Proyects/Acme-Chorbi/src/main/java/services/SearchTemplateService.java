package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SearchTemplateRepository;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.SearchTemplate;

@Service
@Transactional
public class SearchTemplateService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SearchTemplateRepository 	searchTemplateRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private CreditCardService 			creditCardService;
	
	@Autowired
	private ChorbiService 				chorbiService;
	
	@Autowired
	private Validator					validator;


	// Constructors -----------------------------------------------------------

	public SearchTemplateService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public SearchTemplate create(){
		
		SearchTemplate created;
		created = new SearchTemplate();
		Chorbi principal = chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() != 0);
		created.setChorbi(principal);
		
		Date now;
		now = new Date(System.currentTimeMillis()-1);
		created.setMoment(now);
		
		return created;
	}

	public Collection<SearchTemplate> findAll() {
		Collection<SearchTemplate> result;

		result = this.searchTemplateRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SearchTemplate findOne(final int searchTemplateId) {
		Assert.isTrue(searchTemplateId != 0);

		SearchTemplate result;

		result = this.searchTemplateRepository.findOne(searchTemplateId);
		Assert.notNull(result);

		return result;
	}

	public SearchTemplate save(final SearchTemplate searchTemplate) {
		
		SearchTemplate saved;
		Assert.notNull(searchTemplate);
		Assert.isTrue(checkPrincipal(searchTemplate));
		
		Collection<Chorbi> filtered;
		filtered = new ArrayList<Chorbi>();
		filtered.addAll(chorbiService.findAll());
		
		if(searchTemplate.getRelationshipType()!= ""){
			filtered.retainAll(chorbiService.findByRelationshipType(searchTemplate.getRelationshipType()));
		}
		
		if(searchTemplate.getAge() != null){
			filtered.retainAll(chorbiService.findByAge(searchTemplate.getAge()));
		}
		
		filtered.retainAll(chorbiService.findByGenre(searchTemplate.getGenre()));
		
		if(searchTemplate.getKeyword() != ""){
			filtered.retainAll(chorbiService.findByKeyword(searchTemplate.getKeyword()));
		}
		
		if(searchTemplate.getCountry() != ""){
			filtered.retainAll(chorbiService.findByCountry(searchTemplate.getCountry()));
		}
		
		if(searchTemplate.getState() != ""){
			filtered.retainAll(chorbiService.findByState(searchTemplate.getState()));
		}
		
		if(searchTemplate.getProvince() != ""){
			filtered.retainAll(chorbiService.findByProvince(searchTemplate.getProvince()));
		}
		
		filtered.retainAll(chorbiService.findByCity(searchTemplate.getCity()));
		
		searchTemplate.setCache(filtered);
		
		Date lastUpdate = new Date(System.currentTimeMillis() - 1);
		searchTemplate.setMoment(lastUpdate);
		
		saved = searchTemplateRepository.save(searchTemplate);
		return saved;
	}
	
	private boolean checkPrincipal(SearchTemplate searchTemplate) {
		
		Boolean result = false;
		UserAccount chorbi = searchTemplate.getChorbi().getUserAccount();
		UserAccount principal = LoginService.getPrincipal();
		if(chorbi.equals(principal)){
			result = true;
		}
		return result;
	}
	
	/**
	 * @param searchTemplate
	 * @param binding
	 * @return
	 */
	public SearchTemplate reconstruct(SearchTemplate searchTemplate, BindingResult binding) {
		SearchTemplate result;

		if (searchTemplate.getId() == 0) {
			result = searchTemplate;
		} else {
			result = searchTemplateRepository.findOne(searchTemplate.getId());

			result.setCache(searchTemplate.getCache());
			result.setAge(searchTemplate.getAge());
			result.setKeyword(searchTemplate.getKeyword());
			result.setChorbi(searchTemplate.getChorbi());
			result.setCity(searchTemplate.getCity());
			result.setCountry(searchTemplate.getCountry());
			result.setGenre(searchTemplate.getGenre());
			result.setMoment(searchTemplate.getMoment());
			result.setProvince(searchTemplate.getProvince());
			result.setRelationshipType(searchTemplate.getRelationshipType());
			result.setState(searchTemplate.getState());

			validator.validate(result, binding);
		}

		return result;
	}
	
	public Boolean checkCache(SearchTemplate searchTemplate) {

		Boolean res = true;

		Calendar cal = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		Date now;
		now = new Date(System.currentTimeMillis() - 3600 * 1000);
		cal.setTime(now);
		last.setTime(searchTemplate.getMoment());
		Date lastUpdateTime = last.getTime();
		cal.add(Calendar.HOUR, -12);
		Date dateOneHourBack = cal.getTime();
		Chorbi principal = chorbiService.findByPrincipal();
		SearchTemplate chorbiTemplate = chorbiService.findSearchTemplateByChorbi(principal);
		if (chorbiTemplate != null) {
			Boolean relationshipType;
			Boolean age;
			Boolean genre;
			Boolean keyword;
			Boolean country;
			Boolean state;
			Boolean province;
			Boolean city;

			relationshipType = true;
			age = true;
			genre = true;
			keyword = true;
			country = true;
			state = true;
			province = true;
			city = true;

			if (searchTemplate.getRelationshipType()  != "") {
				relationshipType = chorbiTemplate.getRelationshipType().equals(searchTemplate.getRelationshipType());
			}

			if (searchTemplate.getAge()  != null) {
				age = chorbiTemplate.getAge().equals(searchTemplate.getAge());
			}

			if (searchTemplate.getGenre()  != "") {
				genre = chorbiTemplate.getGenre().equals(searchTemplate.getGenre());
			}

			if (searchTemplate.getKeyword() != "") {
				keyword = chorbiTemplate.getKeyword().equals(searchTemplate.getKeyword());
			}
			
			if (searchTemplate.getCountry()  != "") {
				country = chorbiTemplate.getCountry().equals(searchTemplate.getCountry());
			}
			
			if (searchTemplate.getState()  != "") {
				state = chorbiTemplate.getState().equals(searchTemplate.getState());
			}
			
			if (searchTemplate.getProvince()  != "") {
				province = chorbiTemplate.getProvince().equals(searchTemplate.getProvince());
			}
			
			if (searchTemplate.getCity()  != "") {
				city = chorbiTemplate.getCity().equals(searchTemplate.getCity());
			}

			Boolean isEqual = relationshipType && age && genre && keyword && country && state && province && city;

			if (dateOneHourBack.getTime() - lastUpdateTime.getTime() <= 3600000*12 && isEqual) {
				res = true;
			} else {
				res = false;
			}
		} 
		else {
			res = false;
		}

		return res;
	}

	// Other business methods -------------------------------------------------

}
