package password;

import java.util.Objects;
import javax.crypto.spec.IvParameterSpec;

public class Password {
	private String title;
	private String body;
	private IvParameterSpec iv;
	
	public Password() {};
	
	public Password(String title, String body, IvParameterSpec iv) {
		this.title = title;
		this.body = body;
		this.iv = iv;
	}

	public Password(String title, String body, byte[] b) {
		this.title = title;
		this.body = body;
		this.iv = new IvParameterSpec(b);
	}
	
	public Password(String title, String body) {
		this.title = title;
		this.body = body;
	}
	
	public IvParameterSpec getIv() {
		return iv;
	}

	public void setIv(IvParameterSpec iv) {
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
	
	@Override
	public int hashCode() {
		return Objects.hash(body, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Password other = (Password) obj;
		return Objects.equals(body, other.body) && Objects.equals(title, other.title);
	}
	
}
