
package services;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SystemConfigurationServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private SystemConfigurationService	sysConService;

	@Autowired
	private AdministratorService		adminService;

	// Tests ---------------------------------------------------------------

	// Templates ----------------------------------------------------------

}
