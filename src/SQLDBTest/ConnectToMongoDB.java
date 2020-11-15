package SQLDBTest;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ConnectToMongoDB {
    public static MongoDatabase mongoDatabase = null;
    public static MongoDatabase connectToMongoDB(String dataBaseName){
        MongoClient mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase(dataBaseName);
        System.out.println("Mongo database is connected to ");
        return mongoDatabase;
    }

    public static List<Student> readStudentObject(String dataBaseName, String collectionName) {
        List<Student> list = new ArrayList<>();
        Student student = new Student();
        MongoDatabase mongoDatabase = connectToMongoDB(dataBaseName);
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        BasicDBObject basicDBObject = new BasicDBObject();
        FindIterable<Document> iterable = collection.find(basicDBObject);
        for (Document document : iterable) {
            String stName = (String) document.get("stName");
            String stID = (String) document.get("stID");
            String stDOB = (String) document.get("stDOB");

            student = new Student(stName, stID, stDOB);
            list.add(student);
            student = new Student();
        }
        return list;
    }

    public static void main(String[] args) {
        List<Student> list = readStudentObject("students", "MoDBTest");
        for(Student student:list){
            System.out.println(student.getStName() + " " + student.getStID() + " " + student.getStDOB());
    }

    }


}
