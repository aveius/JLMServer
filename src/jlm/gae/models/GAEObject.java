package jlm.gae.models;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GAEObject {

	protected String kind;
	
	protected Key key;
	
	public GAEObject(String kind) {
		this.kind = kind;
	}
	
	protected void createKey(String name) {
		key = KeyFactory.createKey(kind, name);
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
}
