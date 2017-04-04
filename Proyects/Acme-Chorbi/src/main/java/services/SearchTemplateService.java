package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
			//query 
		}
		
		if(searchTemplate.getAge() != null){
			//query
		}
		
		//query genre
		
		if(searchTemplate.getKeyWord() != ""){
			//query KeyWord
		}
		
		if(searchTemplate.getCountry() != ""){
			//query country
		}
		
		if(searchTemplate.getState() != ""){
			//query state
		}
		
		if(searchTemplate.getProvince() != ""){
			//query province
		}
		
		//query city
		
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

	// Other business methods -------------------------------------------------

}
