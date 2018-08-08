package telesar.abastible.DAO;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Document(collection = "errorsLog")
public class ErrorLog {



    private String encriptedData;

    private String data;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timestamp;

    private String errorMsg;

    private String errorException;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorException() {
        return errorException;
    }

    public void setErrorException(String errorException) {
        this.errorException = errorException;
    }

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

    public String getTimestamp() {
        if (timestamp != null)
            return timestamp.toString();
        else
            return null;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}