package data_access;

import com.mongodb.*;
import com.mongodb.client.*;
import entity.chat.Message;
import entity.people.User;
import entity.people.UserFactory;
import org.bson.Document;
//import use_case.login.LoginUserDataAccessInterface;
import org.jetbrains.annotations.NotNull;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;


public class ConvoDAOImpl {
    private final List<Message> messages = new ArrayList<>();

    @NotNull
    private static MongoClientSettings getMongoClientSettings() {
        Dotenv dotenv = Dotenv.configure().load();

        String connectionString = dotenv.get("CONNECTION_STRING");

        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();

        assert connectionString != null;
        return MongoClientSettings.builder().applyConnectionString(
                new ConnectionString(connectionString)).serverApi(serverApi).build();
    }

    public static void main(String[] args) {
        new ConvoDAOImpl();
    }

    public ConvoDAOImpl() throws MongoException {
        MongoClientSettings settings = getMongoClientSettings();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoCollection coll = mongoClient.getDatabase("entities").getCollection("message");
            // Find all documents in the collection
            MongoCursor<Document> cursor = coll.find().iterator();
            System.out.println("=============");

            // Iterate through the results
            while (cursor.hasNext()) {
                Document document = cursor.next();
                // Access and process document data as needed
                System.out.println(document.toJson());
                System.out.println("=============");
            }

            // Close resources
            cursor.close();
        }
    }

    public void save(Message message) {
        messages.add(message);
        saveRemote(message);
    }


    private void saveRemote(Message message) {
        MongoClientSettings settings = getMongoClientSettings();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("entities");
            MongoCollection<Document> patients = database.getCollection("message");
            Document messagedoc = new Document("message", message);
            patients.insertOne(messagedoc);
            System.out.println("Added patient to database");
        }
    }
}