
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends CreditHolder {

	// Constructors -----------------------------------------------------------

	public Manager() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	companyName;
	private String	VATNumber;


	@NotBlank
	@SafeHtml
	public String getCompanyName() {
		return this.companyName;
	}
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}
	@NotBlank
	@SafeHtml
	public String getVATNumber() {
		return this.VATNumber;
	}
	public void setVATNumber(final String VATNumber) {
		this.VATNumber = VATNumber;
	}

	// Relationships ----------------------------------------------------------

}
