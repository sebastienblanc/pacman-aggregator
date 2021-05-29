package org.sebi;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ScoreEventDeserializer extends JsonbDeserializer<ScoreEvent> {
    public ScoreEventDeserializer(){
        // pass the class to the parent.
        super(ScoreEvent.class);
    }
}
