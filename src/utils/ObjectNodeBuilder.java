package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.NonNull;

public class ObjectNodeBuilder extends AbstractNodeBuilder<ObjectNode> {

	ObjectNodeBuilder(JsonNodeFactory factory) {
		super(factory, factory.objectNode());
	}

	public ObjectNodeBuilder with(@NonNull String field, @NonNull JsonNodeBuilder<?> builder) {
		return with(field, builder.build());
	}

	public ObjectNodeBuilder withPOJO(@NonNull String field, @NonNull Object pojo) {
		return with(field, factory.pojoNode(pojo));
	}

	public ObjectNodeBuilder with(@NonNull String field, float value) {
		return with(field, factory.numberNode(value));
	}

	public ObjectNodeBuilder with(@NonNull String field, boolean value) {
		return with(field, factory.booleanNode(value));
	}

	public ObjectNodeBuilder with(@NonNull String field, String value) {
		return with(field, factory.textNode(value));
	}

	public ObjectNodeBuilder with(@NonNull String field, JsonNode value) {
		node.set(field, value);
		return this;
	}

	public ObjectNodeBuilder with(@NonNull String field, ObjectNode value) {
		node.set(field, value);
		return this;
	}

	public ObjectNodeBuilder withNull(@NonNull String field) {
		return with(field, factory.nullNode());
	}

	public ObjectNodeBuilder with(@NonNull String field, int value) {
		return with(field, factory.numberNode(value));
	}

	@Override
	public ObjectNode build() {
		return node;
	}

}
