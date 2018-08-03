package telesar.abastible.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telesar.abastible.DAO.Log;

import telesar.abastible.DAO.Mediciones;
import telesar.abastible.Repository.LogRepository;
import telesar.abastible.Repository.MedicionesRepository;
import telesar.abastible.utils.Util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


@RestController
public class DataController {

    @Autowired
    LogRepository logRepository;

    @Autowired
    MedicionesRepository medicionesRepository;
    
    @RequestMapping("/")
    public String index() {
        try {
            System.out.println(Util.encriptar("001001G0001$170920223249&S01TA01=22.3"));


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/getData")
    public List<Mediciones> getData() {

        return medicionesRepository.findAll();
    }

    @RequestMapping("/getLog")
    public List<Log> getLog(HttpServletRequest request, @RequestParam(value = "fechaInicio", required = false) String fechaInicio, @RequestParam(value = "fechaFin", required = false) String fechaFin, @RequestParam(value = "idSensor", required = false) String idSensor, Model model) {


        return logRepository.findAll();
    }

    @RequestMapping("/data")
    public String submitData(HttpServletRequest request, @RequestParam(value = "data", required = true, defaultValue = "") String data, Model model) throws Exception {

        try {
            Log log = new Log();
            log.setEncriptedData(data);




        // Msg example: [IMEI]$[FECHA HORA]$[Valor Sensor]

            data = Util.desencriptar(data);

            String errors = validateData(data);

            if (errors.length() > 0){
                return errors;
            }

            log.setData(data);




            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());

            log.setTimestamp(timestampActual);


            logRepository.save(log);

            String iMei = data.split("\\$")[0];
            String serialNumber = " ";
            String fechaHora = data.split("\\$")[1];
            String valorSensor = data.split("\\$")[2];

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CST"));

            calendar.set(Integer.valueOf("20"+fechaHora.substring(0, 2)),Integer.valueOf(fechaHora.substring(2, 4)),Integer.valueOf(fechaHora.substring(4, 6)),Integer.valueOf(fechaHora.substring(6, 8)),Integer.valueOf(fechaHora.substring(8, 10)),Integer.valueOf(fechaHora.substring(10, 12)));


            Mediciones medi = new Mediciones();

            medi.setIdCliente(serialNumber);
            medi.setiMei(iMei);
            medi.setValor(Double.parseDouble(valorSensor));
            medi.setTimestampMedicion(new Timestamp(calendar.getTimeInMillis()));
            medi.setTimestampRegistro(timestampActual);

            medicionesRepository.save(medi);




            return serialNumber + " " + fechaHora + " "+ iMei + " " + valorSensor ;


        } catch (Exception e) {
            return e.getMessage();

        }

       // return "";
    }

    private String validateData(String data) {

        if (data == null || data.equals("")) {
            return "001|ERROR|Mensaje Vacio|";
        }

        //Verify that all required section are present in the String
        if (!data.matches("^(.*)\\$20[0-9]{12}\\$[0-9]{1,2}[0]{0,1}$")) {
            return "002|ERROR|Mensaje mal formateado|"+data;
        }
       // String iMei

        return "";
    }

    

}
