
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SystemConfiguration;

@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Integer> {

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findMain();

	@Query("select count(cc)*1.0/(select count(c)*1.0 from Chorbi c) from CreditCard cc")
	Double findRatioChorbiesWithoutCreditCard();

}
