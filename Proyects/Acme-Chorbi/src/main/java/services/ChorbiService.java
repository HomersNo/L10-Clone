
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import repositories.ChorbiRepository;
import security.LoginService;

import domain.Actor;
import domain.Chorbi;
import domain.SearchTemplate;

public class ChorbiService {

	
	@Autowired 
	ChorbiRepository chorbiRepository;
	
	
	public Actor save(final Chorbi actor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Chorbi findByPrincipal() {
		
		Chorbi principal = chorbiRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return principal;
	}

	public Collection<Chorbi> findAll() {
		
		return this.chorbiRepository.findAll();
	}
	
	public Collection<Chorbi> findByRelationshipType(String relationshipType){
		return chorbiRepository.findByRelationshipType(relationshipType);
	}
	
	public Collection<Chorbi> findByAge(Integer age){
		return chorbiRepository.findByAge(age);
	}
	
	public Collection<Chorbi> findByKeyword(String keyword){
		return chorbiRepository.findByKeyword(keyword);
	}
	
	public Collection<Chorbi> findByCountry(String country){
		return chorbiRepository.findByCountry(country);
	}
	
	public Collection<Chorbi> findByGenre(String genre){
		return chorbiRepository.findByGenre(genre);
	}
	
	public Collection<Chorbi> findByState(String state){
		return chorbiRepository.findByState(state);
	}
	
	public Collection<Chorbi> findByProvince(String province){
		return chorbiRepository.findByProvince(province);
	}
	
	public Collection<Chorbi> findByCity(String city){
		return chorbiRepository.findByCity(city);
	}
	
	public SearchTemplate findSearchTemplateByChorbi(Chorbi chorbi){
		return chorbiRepository.findSearchTemplateByChorbi(chorbi);
	}

}
