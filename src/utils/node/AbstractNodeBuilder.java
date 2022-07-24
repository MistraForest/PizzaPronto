package utils.node;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
abstract class AbstractNodeBuilder<T extends JsonNode> implements JsonNodeBuilder<T> {

	/**
     * The source of values.
     */
	@NonNull
    protected JsonNodeFactory factory;
	
	/**
     * The value under construction.
     */
	@NonNull
    protected T node;
	
	protected AbstractNodeBuilder(@NonNull JsonNodeFactory factory, @NonNull T node) {
		this.factory = factory;
		this.node = node;
	}

	/**
     * Returns a valid JSON string, so long as {@code POJONode}s not used.
     */
    @Override
    public String toString() {
      return node.toString();
    }
}
