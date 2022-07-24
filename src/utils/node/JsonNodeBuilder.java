package utils.node;

import com.fasterxml.jackson.databind.JsonNode;


public interface JsonNodeBuilder<T extends JsonNode> {
	
	/**
     * Construct and return the {@link JsonNode} instance.
     */
    T build();

}
