
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import repositories.ChorbiRepository;
import security.LoginService;

import domain.Actor;
import domain.Chorbi;

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

}
