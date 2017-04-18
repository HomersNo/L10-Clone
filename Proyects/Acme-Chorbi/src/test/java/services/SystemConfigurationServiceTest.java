
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Urrl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SystemConfigurationServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private SystemConfigurationService	sysConService;


	// Tests ---------------------------------------------------------------
	@SuppressWarnings("unchecked")
	@Test
	public void driverModifyingCache() {

		final Collection<Urrl> attachments = new ArrayList<Urrl>();
		final Urrl url = new Urrl();
		url.setLink("http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png"); //Mete las url de las imágenes
		attachments.add(url);
		final Collection<Urrl> attachmentsEmpty = new ArrayList<Urrl>();
		final Collection<Urrl> attachmentsFull = new ArrayList<Urrl>();
		final Collection<Urrl> attachmentWrong = new ArrayList<Urrl>();
		for (int i = 0; i < 20; i++)
			attachmentsFull.add(url);
		final Urrl urlWrong = new Urrl();
		urlWrong.setLink("Esto no es un link");
		attachmentWrong.add(urlWrong);

		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			final Date dateWrong = sdf.parse("10:20:56");
			final Date dateRight = sdf.parse("10:20:56");

			final Object testingData[][] = {

				{
					"username", attachments, dateRight, null
				}
			//modificación correcta de system.

			};
			for (int i = 0; i < testingData.length; i++)
				this.templateModifyingCache((String) testingData[i][0], (Collection<Urrl>) testingData[i][1], (Date) testingData[i][2], (Class<?>) testingData[i][3]);
		} catch (final ParseException e) {

			e.printStackTrace();
		}
	}
	// Templates ----------------------------------------------------------
	protected void templateModifyingCache(final String username, final Collection<Urrl> banners, final Date cacheTime, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			//			final Chorbi c = this.chorbiService.findOne(chorbiId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
