
package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.SearchTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SearchTemplateServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private SearchTemplateService	searchTemplateService;


	// Tests
	/*
	 * A chorbi who is authenticated must be able to
	 * 
	 * o Change his or her search template.
	 * o Browse the results of his or her search template as long as he or she's registered a valid credit card.
	 * Note that the validity of the credit card must be checked every
	 * time the results of the search template are displayed.
	 * The results of search templates must be cached for at least 12 hours.
	 */

	@Test
	public void driverEdition() {
		final Object testingData[][] = {

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEdition((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (Class<?>) testingData[i][6]);
	}

	protected void templateEdition(final String username, final String searchTemplate, final Date previousMoment, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final SearchTemplate search = this.searchTemplateService.findOne(this.extract(searchTemplate));
			search.setMoment(previousMoment);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

}
