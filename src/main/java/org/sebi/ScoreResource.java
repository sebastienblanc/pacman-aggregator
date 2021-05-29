package org.sebi;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/score")
public class ScoreResource {

    @Inject
    InteractiveQueries queries; 

    @GET
    @Path("/allscore")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ScoreData> allScore() {
        List<ScoreData> scores = queries.listData();
        Collections.sort(scores, Collections.reverseOrder()); 
        return scores;
    }
}