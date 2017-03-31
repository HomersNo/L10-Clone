package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Like extends DomainEntity{

	// Constructors -----------------------------------------------------------

		public Like() {
			super();
		}

		// Attributes -------------------------------------------------------------
		
		private Date moment;
		private String comment;
		
		@Past
		public Date getMoment() {
			return moment;
		}
		public void setMoment(Date moment) {
			this.moment = moment;
		}
		
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}

		// Relationships ----------------------------------------------------------
		private Chorbi chorbi;
		private Chorbi liked;

		@Valid
		@NotNull
		@ManyToOne(optional = false)
		public Chorbi getChorbi() {
			return chorbi;
		}
		public void setChorbi(Chorbi chorbi) {
			this.chorbi = chorbi;
		}
		
		@Valid
		@NotNull
		@ManyToOne(optional = false)
		public Chorbi getLiked() {
			return liked;
		}
		public void setLiked(Chorbi liked) {
			this.liked = liked;
		}
		
		

}
