package org.sebi;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

@ApplicationScoped
public class InteractiveQueries {
    
    @Inject
    KafkaStreams streams;

    public List<ScoreData> listData() {
        List<ScoreData> scoreDataList = new ArrayList<>(); 
        try (KeyValueIterator<String, ScoreAggregate> all = getScoreEventScore().all()) {
            all.forEachRemaining(keyValue -> scoreDataList.add(ScoreData.from(keyValue.value)));
          }
        return scoreDataList;
    }

    private ReadOnlyKeyValueStore<String, ScoreAggregate> getScoreEventScore() {
        while (true) {
            try {
                return streams.store(TopologyProducer.PLAYERS_STORE, QueryableStoreTypes.keyValueStore());
            } catch (InvalidStateStoreException e) {
                // ignore, store not ready yet
            }
        }
    }
}

