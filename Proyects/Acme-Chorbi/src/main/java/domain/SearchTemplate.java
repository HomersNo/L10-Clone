package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class SearchTemplate extends DomainEntity{

	// Constructors -----------------------------------------------------------

		public SearchTemplate() {
			super();
		}

		// Attributes -------------------------------------------------------------
		
		private String relationshipType;
		private Integer age;
		private String genre;
		private String keyWord;
		private Date moment;
		private String country;
		private String state;
		
		
		public String getRelationshipType() {
			return relationshipType;
		}
		public void setRelationshipType(String relationshipType) {
			this.relationshipType = relationshipType;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getGenre() {
			return genre;
		}
		public void setGenre(String genre) {
			this.genre = genre;
		}
		public String getKeyWord() {
			return keyWord;
		}
		public void setKeyWord(String keyWord) {
			this.keyWord = keyWord;
		}
		@Past
		public Date getMoment() {
			return moment;
		}
		public void setMoment(Date moment) {
			this.moment = moment;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}

		private String province;
		private String city;

		// Relationships ----------------------------------------------------------
		private Collection<Chorbi> chorbies;
		private Chorbi chorbi;

		@Valid
		@NotNull
		@ManyToMany(cascade = CascadeType.ALL)
		public Collection<Chorbi> getChorbies() {
			return chorbies;
		}
		public void setChorbies(Collection<Chorbi> chorbies) {
			this.chorbies = chorbies;
		}
		
		@Valid
		@OneToOne(optional = false)
		public Chorbi getChorbi() {
			return chorbi;
		}
		public void setChorbi(Chorbi chorbi) {
			this.chorbi = chorbi;
		}

}
