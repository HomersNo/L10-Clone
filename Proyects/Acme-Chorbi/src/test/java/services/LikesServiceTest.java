
package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Likes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LikesServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private LikesService	likesService;

	@Autowired
	private ChorbiService	chorbiService;


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	//- An actor who is authenticated must be able to:
	//	o Post a comment on another actor, on an offer, or a request
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Likes.
				"customer1", "correcto", "correcto", 3, new Date(System.currentTimeMillis() - 100), false, null
			}, {	// Creación errónea de un Likes: title vacío.
				"customer1", "", "correcto", 3, new Date(System.currentTimeMillis() - 100), false, ConstraintViolationException.class
			}, {	// Creación errónea de un Likes: text vacío.
				"customer1", "correcto", "", 3, new Date(System.currentTimeMillis() - 100), true, ConstraintViolationException.class
			}, {	// Creación errónea de un Likes: stars vacío.
				"customer1", "correcto", "correcto", null, new Date(System.currentTimeMillis() - 100), true, ConstraintViolationException.class
			}, {	// Creación errónea de un Likes: moment vacío.
				"customer1", "correcto", "correcto", 3, null, true, ConstraintViolationException.class
			}, {	// Creación errónea de un Likes: stars de 6.
				"customer1", "correcto", "correo", 6, new Date(System.currentTimeMillis() - 100), true, ConstraintViolationException.class
			}, {	// Creación errónea de un Likes: moment futuro.
				"customer1", "correcto", "correo", 3, new Date(System.currentTimeMillis() + 10000000), true, ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Date) testingData[i][4], (Boolean) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	//An actor who is authenticated as an administrator must be able to:
	//	o Ban a comment that he or she finds inappropriate. Such comments must not be
	//	displayed to a general audience, only to the administrators and the actor who posted
	//	it.

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String title, final String text, final Integer stars, final Date moment, final Boolean banned, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi chorbi = this.chorbiService.findOne(this.extract("chorbi2"));
			final Likes c = this.likesService.create(chorbi);
			c.setMoment(moment);
			c.setComment(text);

			this.likesService.save(c);
			this.likesService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateUnlike(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(username);
			final Chorbi chorbi = this.chorbiService.findOne(this.extract("chorbi2"));
			final Likes comment = this.likesService.create(chorbi);
			final Likes result = this.likesService.save(comment);
			this.likesService.unlike(result);
			this.unauthenticate();

			this.likesService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
