package telesar.abastible.DAO;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;



@Document(collection = "logs")
public class Log {



    private String encriptedData;

    private String data;

    private Date timestamp;


    public String getEncriptedData() {
        return encriptedData;
    }

    public void setEncriptedData(String encriptedData) {
        this.encriptedData = encriptedData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}