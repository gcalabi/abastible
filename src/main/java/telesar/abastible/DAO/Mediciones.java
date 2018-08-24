package telesar.abastible.DAO;

import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "mediciones")
public class Mediciones {



    private String imei;

    private String serial;

    private double valor;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timestampMedicion;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Indexed
    private Date timestampRegistro;


    public String getTimestampRegistro() {
        if (timestampRegistro != null)
            return timestampRegistro.toString();
        else
            return null;
    }

    public void setTimestampRegistro(Date timestampRegistro) {
        this.timestampRegistro = timestampRegistro;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getTimestampMedicion() {

            return timestampMedicion;

    }

    public String getTimestampMedicionFormatted() {
        if (timestampMedicion != null)
            return timestampMedicion.toString();
        else
            return null;
    }

    public void setTimestampMedicion(Date timestampMedicion) {
        this.timestampMedicion = timestampMedicion;
    }
}
