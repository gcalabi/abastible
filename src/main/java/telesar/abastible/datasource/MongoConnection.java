package telesar.abastible.datasource;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    private static MongoConnection ourInstance = new MongoConnection();

    public static MongoConnection getInstance() {
        return ourInstance;
    }

    private static MongoClient mongoClient;

    private MongoConnection() {
        MongoClientOptions.Builder options = MongoClientOptions.builder()
                .connectionsPerHost(5)
                .maxConnectionIdleTime((60 * 1_000))
                .maxConnectionLifeTime((120 * 1_000));

        mongoClient = new MongoClient(new MongoClientURI("mongodb://telesar:Telesar2018!@localhost/abastible", options));
       // MongoDatabase db = mongoClient.getDatabase("abastible");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
