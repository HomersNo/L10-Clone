package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity{

	// Constructors -----------------------------------------------------------

		public Chirp() {
			super();
		}

		// Attributes -------------------------------------------------------------
		
		private String subject;
		private String text;
		private String attachments;
		
		
		@NotBlank
		public String getSubject() {
			return this.subject;
		}
		public void setSubject(final String subject) {
			this.subject = subject;
		}
		@NotBlank
		public String getText() {
			return this.text;
		}
		public void setText(final String text) {
			this.text = text;
		}

		@URL
		public String getAttachments() {
			return this.attachments;
		}
		public void setAttachments(final String attachments) {
			this.attachments = attachments;
		}


		// Relationships ----------------------------------------------------------
		
		private Folder	folder;
		private Actor	sender;
		private Actor	recipient;


		@Valid
		@NotNull
		@ManyToOne(optional = false)
		public Folder getFolder() {
			return this.folder;
		}
		public void setFolder(final Folder folder) {
			this.folder = folder;
		}

		@Valid
		@NotNull
		@ManyToOne(optional = false)
		//	@NotFound(action = NotFoundAction.IGNORE)
		public Actor getSender() {
			return this.sender;
		}
		public void setSender(final Actor sender) {
			this.sender = sender;
		}

		@Valid
		@NotNull
		@ManyToOne(optional = false)
		//	@NotFound(action = NotFoundAction.IGNORE)
		public Actor getRecipient() {
			return this.recipient;
		}
		public void setRecipient(final Actor recipient) {
			this.recipient = recipient;
		}

}
