package jlm.gae.models;

import java.util.HashMap;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public abstract class GAEObject {

	protected String kind;

	protected Key key;

	protected String id;

	protected HashMap<String, Object> data;

	protected GAEObject(String kind, String id) {
		this.kind = kind;
		this.id = id;
		key = KeyFactory.createKey(kind, id);
		data = new HashMap<String, Object>();
	}

	protected GAEObject(String kind, Entity e) {
		this.kind = kind;
		key = e.getKey();
		id = (String) e.getProperty("id");
	}

	public boolean save() {
		Entity en = new Entity(kind, key);
		en.setProperty("id", id);
		for (String _key : data.keySet()) {
			Object _value = data.get(_key);
			en.setProperty(_key, _value);
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		try {
			datastore.get(key);
		} catch (EntityNotFoundException e) {
			datastore.put(en);
			return true;
		}
		return false;
	}

	public static boolean delete(String kind, String courseName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query(kind).setKeysOnly();
		q.addFilter("id", Query.FilterOperator.EQUAL, courseName);

		boolean b = true;
		PreparedQuery pq = datastore.prepare(q);
		for (Entity en : pq.asIterable()) {
			try {
				datastore.get(en.getKey());
				datastore.delete(en.getKey());
			} catch (EntityNotFoundException e) {
				b = false;
			}
		}
		return b;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void putData(String key, Object value) {
		data.put(key, value);
	}

	public void removeData(String key) {
		data.remove(key);
	}

	public Object getData(String key) {
		return data.get(key);
	}
}