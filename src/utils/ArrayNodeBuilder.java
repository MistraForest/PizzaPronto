package utils;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.NonNull;

public class ArrayNodeBuilder extends AbstractNodeBuilder<ArrayNode> {

	public ArrayNodeBuilder(@NonNull JsonNodeFactory factory) {
		super(factory, factory.arrayNode());
	}

	public ArrayNodeBuilder with(boolean value) {
		node.add(value);
		return this;
	}

	public ArrayNodeBuilder with(@NonNull boolean... values) {
		for (@NonNull boolean value : values)
			with(value);
		return this;
	}

	public ArrayNodeBuilder with(int value) {
		node.add(value);
		return this;
	}

	public ArrayNodeBuilder with(@NonNull int... values) {
		for (@NonNull int value : values)
			with(value);
		return this;
	}

	public ArrayNodeBuilder with(float value) {
		node.add(value);
		return this;
	}

	public ArrayNodeBuilder with(String value) {
		node.add(value);
		return this;
	}

	public ArrayNodeBuilder with(@NonNull String... values) {
		for (@NonNull String value : values)
			with(value);
		return this;
	}

	public ArrayNodeBuilder with(@NonNull Iterable<String> values) {
		for (String value : values)
			with(value);
		return this;
	}

	public ArrayNodeBuilder with(JsonNode value) {
		node.add(value);
		return this;
	}

	public ArrayNodeBuilder with(@NonNull JsonNode... values) {
		for (@NonNull JsonNode value : values)
			with(value);
		return this;
	}

	public ArrayNodeBuilder with(JsonNodeBuilder<?> value) {
		return with(value.end());
	}

	public ArrayNodeBuilder with(@NonNull JsonNodeBuilder<?>... builders) {
		for (@NonNull JsonNodeBuilder<?> builder : builders)
			with(builder);
		return this;
	}

	@Override
	public ArrayNode end() {
		return node;
	}

	public ArrayNodeBuilder with(@NonNull List<ObjectNode> values) {
		for (@NonNull ObjectNode value : values)
			with(value);
		return this;
	}
	
	public ArrayNodeBuilder with(ObjectNode value) {
		node.add(value);
		return this;
	}

}
