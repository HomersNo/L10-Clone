
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private FolderService	folderService;

	@Autowired
	private ChorbiService	chorbiService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creacion correcta de un Folder.
				"chorbi1", "Sent", NumberFormatException.class
			}, {	// Creacion erronea de un Folder: sin Chorbi asociado.
				"", "Sent", IllegalArgumentException.class
			}, {	// Creacion erronea de un Folder: Folder sin nombre.
				"chorbi1", "", IllegalArgumentException.class
			}, {	// Creacion erronea de un Folder: Folder sin nombre establecido.
				"chorbi1", "Lalala", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String name, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi chorbi = this.chorbiService.findOne(this.extract("chorbi1"));
			final Folder f = this.folderService.create(chorbi);
			f.setName(name);
			this.folderService.save(f);
			this.folderService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
