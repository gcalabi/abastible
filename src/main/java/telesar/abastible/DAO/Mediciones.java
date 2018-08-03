package telesar.abastible.DAO;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "mediciones")
public class Mediciones {



    private String iMei;

    private String idCliente;

    private double valor;

    private Date timestampMedicion;

    private Date timestampRegistro;


    public Date getTimestampRegistro() {
        return timestampRegistro;
    }

    public void setTimestampRegistro(Date timestampRegistro) {
        this.timestampRegistro = timestampRegistro;
    }

    public String getiMei() {
        return iMei;
    }

    public void setiMei(String iMei) {
        this.iMei = iMei;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
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

    public void setTimestampMedicion(Date timestampMedicion) {
        this.timestampMedicion = timestampMedicion;
    }
}
