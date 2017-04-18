
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ChorbiService		chorbiService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de una tarjeta de crédito.
				"chorbi1", "holderName", "brandName", "1111333355557777", "04", "19", "321", "chorbi1", null
			}, {	// Creación errónea de una tarjeta de crédito: nombre vacío.
				"chorbi1", "", "brandName", "1111333355557777", "04", "19", "321", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: marca vacía.
				"chorbi1", "holderName", "", "1111333355557777", "04", "19", "321", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: número vacío.
				"chorbi1", "holderName", "brandName", "", "04", "19", "321", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: número con formato incorrecto.
				"chorbi1", "holderName", "brandName", "111333355557777", "04", "19", "321", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: mes vacío.
				"chorbi1", "holderName", "brandName", "1111333355557777", "", "19", "321", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: mes incorrecto.
				"chorbi1", "holderName", "brandName", "1111333355557777", "15", "19", "321", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: año vacío.
				"chorbi1", "holderName", "brandName", "1111333355557777", "04", "", "321", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: año incorrecto.
				"chorbi1", "holderName", "brandName", "1111333355557777", "04", "234", "321", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: cvv vacío.
				"chorbi1", "holderName", "brandName", "1111333355557777", "04", "19", "", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: cvv incorrecto.
				"chorbi1", "holderName", "brandName", "1111333355557777", "04", "19", "3251", "chorbi1", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: chorbi vacío.
				"chorbi1", "holderName", "brandName", "1111333355557777", "04", "19", "321", "", ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: chorbi incorrecto.
				"chorbi1", "holderName", "brandName", "1111333355557777", "04", "19", "321", "chorbi41", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}

	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de una tarjeta de crédito.
				"chorbi1", 51, IllegalArgumentException.class
			}, {	// Display erróneo de una tarjeta de crédito.
				"chorbi1", 100, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String holderName, final String brandName, final String creditCardNumber, final int expirationMonth, final int expirationYear, final int cvv, final String chorbi, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final CreditCard cc = this.creditCardService.create();
			cc.setHolderName(holderName);
			cc.setBrandName(brandName);
			cc.setCreditCardNumber(creditCardNumber);
			cc.setExpirationMonth(expirationMonth);
			cc.setExpirationYear(expirationYear);
			cc.setCVV(cvv);
			final Chorbi c = (Chorbi) this.actorService.findByPrincipal();
			cc.setChorbi(c);
			this.creditCardService.save(cc);
			this.creditCardService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final int creditCardId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final CreditCard cc = this.creditCardService.findOne(creditCardId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
