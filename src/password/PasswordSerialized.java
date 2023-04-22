package password;

import java.io.Serializable;

public class PasswordSerialized implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String body;
	private byte[] iv;
	
	public PasswordSerialized(String title, String body, byte[] iv) {
		this.title = title;
		this.body = body;
		this.iv = iv;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}
}
