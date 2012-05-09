package jlm.gae.models;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public abstract class GAEObject {

	protected Entity entity;

	protected GAEObject(String kind) {
		entity = new Entity(kind);
	}

	protected GAEObject(Entity e) {
		entity = e;
	}

	public void put(String key, Object value) {
		entity.setProperty(key, value);
	}

	public Object get(String key) {
		return entity.getProperty(key);
	}
	
	public abstract Answer save();

	public Answer delete() {
		if (entity.getKey() != null) {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			datastore.delete(entity.getKey());
			return Answer.ALL_IS_FINE;
		}
		return Answer.DATA_NOT_IN_DATABASE;
	}
}