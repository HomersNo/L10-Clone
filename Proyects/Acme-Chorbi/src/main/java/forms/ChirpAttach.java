
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class ChirpAttach {

	public ChirpAttach() {
		super();
	}


	private int		chirpId;
	private String	attachment;


	public int getChirpId() {
		return this.chirpId;
	}

	public void setChirpId(final int chirpId) {
		this.chirpId = chirpId;
	}

	@URL
	@NotBlank
	public String getAttachment() {

		return this.attachment;
	}
	public void setAttachment(final String attachment) {

		this.attachment = attachment;
	}

}
