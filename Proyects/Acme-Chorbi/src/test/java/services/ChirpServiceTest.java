
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Chirp;
import domain.Chorbi;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChirpServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ChorbiService	chorbiService;


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	//An actor who is authenticated must be able to:
	//	o Exchange chirps with other actors.
	//	o Erase his or her chirps, which requires previous confirmation
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Chirp.
				"chorbi1", "correcto", "correcto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() - 100), null
			}, {	// Creación correcta de un Chirp.
				"admin", "incorrecto", "incorrecto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() - 100), null
			}, {	// Creación errónea de un Chirp: title vacío.
				"chorbi1", "", "incorrecto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() - 100), ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: text vacío.
				"chorbi1", "incorrecto", "", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() - 100), ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: moment vacío.
				"chorbi1", "incorrecto", "incorrecto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", null, ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: attachment que no es URL
				"chorbi1", "incorrecto", "incorrecto", "blae", new Date(System.currentTimeMillis() - 100), ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: moment futuro.
				"chorbi1", "incorrecto", "incorrecto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() + 10000000), ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	//- An actor who is authenticated must be able to:
	//	o List the chirps that he or she's got and reply to them.
	//	o List the chirps that he or she's got and forward them
	@Test
	public void driverFindAllByFolderId() {
		final Object testingData[][] = {
			{
				"admin", "Inbox", null
			}, {
				"admin", "Outbox", null
			}, {
				"chorbi1", "Inbox", null
			}, {
				"chorbi1", "Outbox", null
			}, {
				"chorbi2", "Inbox", null
			}, {
				"chorbi2", "Outbox", null
			}, {
				"chorbi3", "Inbox", null
			}, {
				"chorbi3", "Outbox", null
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindAllByFolderId((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String subject, final String text, final String attachment, final Date moment, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi cus = this.chorbiService.findOne(87);
			final Chirp m = this.chirpService.create();
			m.setMoment(moment);
			m.setAttachments(attachment);
			m.setText(text);
			m.setSubject(subject);
			m.setRecipient(cus);

			this.chirpService.send(m);

			final Actor sender = this.actorService.findByPrincipal();
			final Actor recipient = m.getRecipient();

			Assert.isTrue(m.getSender().equals(sender) && m.getRecipient().equals(recipient));

			final Folder recipientFolder = this.folderService.findSystemFolder(recipient, "Inbox");
			final Folder senderFolder = this.folderService.findSystemFolder(sender, "Outbox");

			final Collection<Chirp> recipientChirps = this.chirpService.findAllByFolderWithNoCheck(recipientFolder.getId());
			final Collection<Chirp> senderChirps = this.chirpService.findAllByFolderWithNoCheck(senderFolder.getId());

			for (final Chirp r : recipientChirps)
				for (final Chirp s : senderChirps)
					if (r.getSubject() == r.getSubject() && r.getMoment().equals(s.getMoment()) && r.getAttachments() == s.getAttachments() && r.getRecipient().equals(s.getRecipient()) && r.getText() == s.getText())
						Assert.isTrue(r.getSubject() == r.getSubject() && r.getMoment().equals(s.getMoment()) && r.getAttachments() == s.getAttachments() && r.getRecipient().equals(s.getRecipient()) && r.getText() == s.getText());
			this.chirpService.delete(m);

			final Collection<Chirp> all = this.chirpService.findAll();
			Assert.isTrue(!(all.contains(m)));

			this.chirpService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateFindAllByFolderId(final String username, final String folderName, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor actor = this.actorService.findByPrincipal();
			final Folder folder = this.folderService.findSystemFolder(actor, folderName);

			for (final Chirp m : this.chirpService.findAllByFolder(folder.getId()))
				Assert.isTrue(m.getSender().equals(actor) || m.getRecipient().equals(actor));

			this.unauthenticate();

			this.chirpService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
