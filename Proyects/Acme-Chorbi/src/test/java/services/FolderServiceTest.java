
package services;

import org.junit.Test;
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
public class FolderServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private FolderService	folderService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {

	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
