package utils.node;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Convenience {@link JsonNode} builder.
 */
@NoArgsConstructor(access = PRIVATE)
public class JsonBuilder {

	public static ObjectNodeBuilder object() {
		return object(JsonNodeFactory.instance);
	}

	private static ObjectNodeBuilder object(JsonNodeFactory factory) {
		return new ObjectNodeBuilder(factory);
	}

	public static ObjectNodeBuilder object(@NonNull String k1, boolean v1) {
		return object().with(k1, v1);
	}

	public static ObjectNodeBuilder object(@NonNull String k1, int v1) {
		return object().with(k1, v1);
	}

	public static ObjectNodeBuilder object(@NonNull String k1, float v1) {
		return object().with(k1, v1);
	}

	public static ObjectNodeBuilder object(@NonNull String k1, String v1) {
		return object().with(k1, v1);
	}

	public static ObjectNodeBuilder object(@NonNull String k1, String v1, @NonNull String k2, String v2) {
		return object(k1, v1).with(k2, v2);
	}

	public static ObjectNodeBuilder object(
			@NonNull String k1, String v1, 
			@NonNull String k2, String v2,
			@NonNull String k3, String v3
			) {
		return object(k1, v1, k2, v2).with(k3, v3);
	}

	public static ObjectNodeBuilder object(@NonNull String k1, JsonNodeBuilder<?> builder) {
		return object().with(k1, builder);
	}

	/**
	 * Factory methods for an {@link ArrayNode} builder.
	 */

	public static ArrayNodeBuilder array() {
		return array(JsonNodeFactory.instance);
	}

	public static ArrayNodeBuilder array(@NonNull boolean... values) {
		return array().with(values);
	}

	public static ArrayNodeBuilder array(@NonNull int... values) {
		return array().with(values);
	}

	public static ArrayNodeBuilder array(@NonNull String... values) {
		return array().with(values);
	}
	public static ArrayNodeBuilder array(@NonNull ObjectNode... values) {
		return array().with(values);
	}
	public static ArrayNodeBuilder array(@NonNull List<ObjectNode> values) {
		return array().with(values);
	}
	public static ArrayNodeBuilder array(@NonNull JsonNodeBuilder<?>... builders) {
		return array().with(builders);
	}

	public static ArrayNodeBuilder array(JsonNodeFactory factory) {
		return new ArrayNodeBuilder(factory);
	}
}
