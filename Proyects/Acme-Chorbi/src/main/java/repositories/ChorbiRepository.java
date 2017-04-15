/*
 * ActorRepository.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("Select c from Chorbi c where c.userAccount.id = ?1")
	Chorbi findByUserAccountId(int userAccountId);

	@Query("Select c from Chorbi c where c.relationshipType = ?1")
	Collection<Chorbi> findByRelationshipType(String relationshipType);

	@Query("Select c from Chorbi c where YEAR(CURRENT_DATE) - YEAR(c.birthDate) = ?1")
	Collection<Chorbi> findByAge(Integer age);

	@Query("Select c from Chorbi c where c.name like %?1% OR c.surname like %?1%")
	Collection<Chorbi> findByKeyword(String keyword);

	@Query("Select c from Chorbi c where c.country = ?1")
	Collection<Chorbi> findByCountry(String country);

	@Query("Select c from Chorbi c where c.genre = ?1")
	Collection<Chorbi> findByGenre(String genre);

	@Query("Select c from Chorbi c where c.state = ?1")
	Collection<Chorbi> findByState(String state);

	@Query("Select c from Chorbi c where c.province = ?1")
	Collection<Chorbi> findByProvince(String province);

	@Query("Select c from Chorbi c where c.city = ?1")
	Collection<Chorbi> findByCity(String city);

	@Query("Select c from Chorbi c where c.banned = false")
	Collection<Chorbi> findAllNotBanned();

}
