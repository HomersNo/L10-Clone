
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	//Constructor

	public SystemConfiguration() {
		super();
	}


	//Attributes

	private Collection<String>	banners;
	private Date				cacheTime;


	@ElementCollection
	@NotEmpty
	public Collection<String> getBanners() {
		return this.banners;
	}
	public void setBanners(final Collection<String> banners) {
		this.banners = banners;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCacheTime() {
		return this.cacheTime;
	}
	public void setCacheTime(final Date cacheTime) {
		this.cacheTime = cacheTime;
	}

}
