package elasticsearch.action.search;

import co.elastic.clients.elasticsearch._types.RequestBase;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.JsonpSerializable;
import jakarta.json.stream.JsonGenerator;

/**
 * @program: Middleware
 * @description:
 * @author: Mr.Carl
 **/
public class SearchRequest extends RequestBase implements JsonpSerializable {

    @Override
    public void serialize(JsonGenerator generator, JsonpMapper mapper) {
        generator.writeStartObject();
        serializeInternal(generator, mapper);
        generator.writeEnd();
    }

    private void serializeInternal(JsonGenerator generator, JsonpMapper mapper) {
    }
}
