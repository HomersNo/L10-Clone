
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.SearchTemplate;
import forms.RegisterChorbi;

public class ChorbiService {

	@Autowired
	private ChorbiRepository		chorbiRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	public ChorbiService() {
		super();
	}

	public Chorbi create() {
		Chorbi result;

		result = new Chorbi();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		authority.setAuthority(Authority.CHORBI);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);

		result.setBanned(false);
		result.setUserAccount(userAccount);

		return result;
	}

	public Chorbi save(final Chorbi chorbi) {
		if (chorbi.getId() == 0) {
			final Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR - 18));
			Assert.isTrue(calendar.getTime().after(chorbi.getBirthDate()), "Dear user, you must be over 18 to register");
		}
		Chorbi result;

		result = this.chorbiRepository.save(chorbi);

		return result;
	}

	public Chorbi findOne(final int id) {
		Chorbi chorbi;
		chorbi = this.chorbiRepository.findOne(id);
		return chorbi;
	}

	public Chorbi findOneToEdit(final int chorbiId) {
		Assert.isTrue(this.checkPrincipal(chorbiId));
		Chorbi chorbi;
		chorbi = this.chorbiRepository.findOne(chorbiId);
		return chorbi;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findAll();
		return result;
	}

	public Collection<Chorbi> findAllNotBanned() {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findAllNotBanned();
		return result;
	}

	public Chorbi reconstruct(final RegisterChorbi registerChorbi, final BindingResult binding) {
		Chorbi result;
		Assert.isTrue(registerChorbi.isAccept());
		result = this.create();

		result.setBirthDate(registerChorbi.getBirthDate());
		result.setCity(registerChorbi.getCity());
		result.setCountry(registerChorbi.getCountry());
		result.setDescription(registerChorbi.getDescription());
		result.setEmail(registerChorbi.getEmail());
		result.setGenre(registerChorbi.getGenre());
		result.setName(registerChorbi.getName());
		result.setPhoneNumber(registerChorbi.getPhoneNumber());
		result.setPicture(registerChorbi.getPicture());
		result.setProvince(registerChorbi.getProvince());
		result.setRelationshipType(registerChorbi.getRelationshipType());
		result.setState(registerChorbi.getState());
		result.setSurname(registerChorbi.getSurname());

		result.getUserAccount().setUsername(registerChorbi.getUsername());
		result.getUserAccount().setPassword(registerChorbi.getPassword());

		return result;
	}

	public Chorbi reconstruct(final Chorbi chorbi, final BindingResult binding) {
		Chorbi result;

		if (chorbi.getId() == 0)
			result = chorbi;
		else {
			result = this.chorbiRepository.findOne(chorbi.getId());

			result.setBirthDate(chorbi.getBirthDate());
			result.setCity(chorbi.getCity());
			result.setCountry(chorbi.getCountry());
			result.setDescription(chorbi.getDescription());
			result.setEmail(chorbi.getEmail());
			result.setGenre(chorbi.getGenre());
			result.setName(chorbi.getName());
			result.setPhoneNumber(chorbi.getPhoneNumber());
			result.setPicture(chorbi.getPicture());
			result.setProvince(chorbi.getProvince());
			result.setRelationshipType(chorbi.getRelationshipType());
			result.setState(chorbi.getState());
			result.setSurname(chorbi.getSurname());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public Chorbi findByPrincipal() {
		Chorbi result;
		result = this.chorbiRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public boolean checkPrincipal(final int chorbiId) {
		Chorbi result;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.chorbiRepository.findByUserAccountId(userAccount.getId());
		return result.getId() == chorbiId;
	}

	public Chorbi register(final Chorbi chorbi) {
		Assert.isTrue(this.findByPrincipal().getId() == chorbi.getId());
		Chorbi result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		final String pass = encoder.encodePassword(chorbi.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		chorbi.getUserAccount().setPassword(pass);

		result = this.chorbiRepository.save(chorbi);

		return result;
	}

	public void banChorbi(final int chorbiId) {
		this.administratorService.checkAdministrator();
		Chorbi chorbi;
		chorbi = this.chorbiRepository.findOne(chorbiId);
		if (chorbi.getBanned()) {
			chorbi.setBanned(false);
			chorbi.getUserAccount().setEnabled(true);
		} else {
			chorbi.setBanned(true);
			chorbi.getUserAccount().setEnabled(false);
		}
		this.chorbiRepository.save(chorbi);
	}

	public Collection<Chorbi> findByRelationshipType(final String relationshipType) {
		return this.chorbiRepository.findByRelationshipType(relationshipType);
	}

	public Collection<Chorbi> findByAge(final Integer age) {
		return this.chorbiRepository.findByAge(age);
	}

	public Collection<Chorbi> findByKeyword(final String keyword) {
		return this.chorbiRepository.findByKeyword(keyword);
	}

	public Collection<Chorbi> findByCountry(final String country) {
		return this.chorbiRepository.findByCountry(country);
	}

	public Collection<Chorbi> findByGenre(final String genre) {
		return this.chorbiRepository.findByGenre(genre);
	}

	public Collection<Chorbi> findByState(final String state) {
		return this.chorbiRepository.findByState(state);
	}

	public Collection<Chorbi> findByProvince(final String province) {
		return this.chorbiRepository.findByProvince(province);
	}

	public Collection<Chorbi> findByCity(final String city) {
		return this.chorbiRepository.findByCity(city);
	}

	public SearchTemplate findSearchTemplateByChorbi(final Chorbi chorbi) {
		return this.chorbiRepository.findSearchTemplateByChorbi(chorbi);
	}

}
