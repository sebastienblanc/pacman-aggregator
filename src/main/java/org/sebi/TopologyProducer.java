package org.sebi;
import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;

import io.quarkus.kafka.client.serialization.JsonbSerde;

@ApplicationScoped
public class TopologyProducer {

    static final String PLAYERS_STORE = "players-store";

    private static final String PLAYERS_TOPIC = "players";
    private static final String SCORE_EVENT_TOPIC = "score-event";
    private static final String SCORES_AGGREGATED_TOPIC = "scores-aggregated";

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        JsonbSerde<Player> playerSerde = new JsonbSerde<>(
            Player.class);
        JsonbSerde<ScoreAggregate> aggregationSerde = new JsonbSerde<>(ScoreAggregate.class);

        KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(
            PLAYERS_STORE);

        GlobalKTable<String, Player> players = builder.globalTable( 
            PLAYERS_TOPIC,
                Consumed.with(Serdes.String(), playerSerde));

        builder.stream(                                                       
            SCORE_EVENT_TOPIC,
                        Consumed.with(Serdes.String(), Serdes.String())
                )
                .join(                                                        
                        players,
                        (playerId, ghost) -> playerId,
                        (ghost, player) -> {
                            
                            return new ScoreEvent(player.getId(), player.getName(), ghost);
                        }
                )
                .groupByKey()                                                 
                .aggregate(                                                   
                        ScoreAggregate::new,
                        (playerId, value, aggregation) -> aggregation.updateFrom(value),
                        Materialized.<String, ScoreAggregate> as(storeSupplier)
                            .withKeySerde(Serdes.String())
                            .withValueSerde(aggregationSerde)
                )
                .toStream()
                .to(                                                          
                        SCORES_AGGREGATED_TOPIC,
                        Produced.with(Serdes.String(), aggregationSerde)
                );

        return builder.build();
    }
}
