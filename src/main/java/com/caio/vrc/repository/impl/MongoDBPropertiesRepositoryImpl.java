package com.caio.vrc.repository.impl;

import static com.caio.vrc.VrcProperty.MONGODB_DATABASE;
import static com.caio.vrc.VrcProperty.MONGODB_HOST;
import static com.caio.vrc.VrcProperty.MONGODB_PORT;
import static com.mongodb.client.model.Filters.eq;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.bson.Document;

import com.caio.vrc.cache.KeepCached;
import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.caio.vrc.repository.PropertyRepository;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/**
 * MongoDB property repository implementation.
 * 
 * @author caio.silva
 *
 */
public class MongoDBPropertiesRepositoryImpl implements PropertyRepository {

	private static final String PROPERTIES_COLLECTION = "properties";
	private static AtomicLong sequence; // FIXME create a function on mongodb to so

	private final MongoCollection<Document> collection;
	private final Gson gson = new Gson();

	@SuppressWarnings("resource")
	public MongoDBPropertiesRepositoryImpl() {
		collection = new MongoClient(MONGODB_HOST.getStringValue(), MONGODB_PORT.getIntegerValue())
				.getDatabase(MONGODB_DATABASE.getStringValue()) //
				.getCollection(PROPERTIES_COLLECTION);
		sequence = getMaxId(collection);
	}

	public MongoDBPropertiesRepositoryImpl(MongoCollection<Document> collection, AtomicLong sequence) {
		this.collection = collection;
		MongoDBPropertiesRepositoryImpl.sequence = sequence;
	}

	@Deprecated
	private AtomicLong getMaxId(MongoCollection<Document> collection) {
		long init = 0;
		try {
			init = property(collection.find().sort(Filters.eq("id", -1)).first()).getId();
		} catch (Exception e) {
			// do nothing
		}
		return new AtomicLong(init);
	}

	@Override
	public Property add(Property property) {
		property.setId(sequence.incrementAndGet());
		collection.insertOne(Document.parse(gson.toJson(property)));
		return property;
	}

	@Override
	@KeepCached(expire = 30)
	public Property get(int id) {
		return property(collection.find(eq("id", id)).first());
	}

	@Override
	@KeepCached(expire = 30)
	public Collection<Property> find(PropertyFilter propertyFilter) {
		FindIterable<Document> result = collection.find(new Document() //
				.append("lat", new Document() //
						.append("$gte", propertyFilter.getAx()) //
						.append("$lte", propertyFilter.getBx())) //
				.append("long", new Document() //
						.append("$gte", propertyFilter.getBy()) //
						.append("$lte", propertyFilter.getAy())) //
		);
		return properties(result);
	}

	// Helpers

	private Collection<Property> properties(FindIterable<Document> result) {
		final Collection<Property> properties = new ArrayList<>();
		if (result != null) {
			for (Document doc : result) {
				properties.add(property(doc));
			}
		}
		return properties;
	}

	private Property property(Document document) {
		if (document == null) {
			return null;
		}
		return new Property( //
				Long.valueOf(document.getInteger("id")), //
				document.getString("title"), //
				new BigDecimal(document.getInteger("price")), //
				document.getString("description"), //
				document.getInteger("lat"), //
				document.getInteger("long"), //
				document.getInteger("beds"), //
				document.getInteger("baths"), //
				document.getInteger("squareMeters") //
		);
	}

}
