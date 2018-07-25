package telesar.abastible.socket;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import telesar.abastible.datasource.MongoConnection;
import telesar.abastible.utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class threadRequest implements Runnable{

    private Socket clientSocket;

    public threadRequest(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("llego mensaje");
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            isr = new InputStreamReader(clientSocket.getInputStream());
            reader = new BufferedReader(isr);
            String data = reader.readLine();
            String response;

            if (data != null && !data.isEmpty()) {
                System.out.println("IP :" + clientSocket.getInetAddress() + " Mensaje : " + data);

                // Msg example: 123456789123456789$180724185531&45

                String ipCliente = clientSocket.getInetAddress().toString();

                String iMei = data.split("\\$")[0];
                String idCliente = " ";
                String fechaHora = data.split("\\$")[1];
                String valorSensor = data.split("\\$")[2];

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CST"));

                calendar.set(Integer.valueOf("20"+fechaHora.substring(0, 2)),Integer.valueOf(fechaHora.substring(2, 4)),Integer.valueOf(fechaHora.substring(4, 6)),Integer.valueOf(fechaHora.substring(6, 8)),Integer.valueOf(fechaHora.substring(8, 10)),Integer.valueOf(fechaHora.substring(10, 12)));

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                MongoConnection mongoConenction = MongoConnection.getInstance();
                MongoClient mongoClient = mongoConenction.getMongoClient();
                MongoDatabase db = mongoClient.getDatabase("abastible");

                MongoCollection logCollection = db.getCollection("logs");

                Document logDocument = new Document();
                logDocument.put("encriptedData", data);
                logDocument.put("data", data);
                logDocument.put("timestamp", timestamp);


                logCollection.insertOne(logDocument);

                MongoCollection medicionesCollection = db.getCollection("mediciones");

                Document medicionesDocument = new Document();
                medicionesDocument.put("iMei", iMei);
                medicionesDocument.put("idCliente", idCliente);
                medicionesDocument.put("valor", valorSensor);
                medicionesDocument.put("timestamp", timestamp);


                medicionesCollection.insertOne(medicionesDocument);

                //mongoClient.close();


            } else {
                data = "";
            }
            Date today = new Date();
            response = today + " - IP " + clientSocket.getInetAddress() + " - " + data + "\n\r";
            System.out.println("Respondiendo : " + response);

            clientSocket.getOutputStream().write(response.getBytes());

            System.out.println("Respuesta enviada");
            reader.close();
            isr.close();
            clientSocket.close();

        } catch (Exception e) {
            System.out.println(e.toString());

            try {
                if (isr != null) {
                    isr.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (clientSocket != null) {
                    clientSocket.close();
                }

//                if (mongoClient != null) {
//                    mongoClient.close();
//                }

            } catch (IOException e1) {
                System.out.println(e.toString());
            }

        }
    }
}
