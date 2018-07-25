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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@RestController
public class HelloController {

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
    public List<Log> getLog() {

        return logRepository.findAll();
    }

    @RequestMapping("/data")
    public String submitData(HttpServletRequest request, @RequestParam(value = "data", required = true, defaultValue = "") String data, Model model) throws Exception {


        Log log = new Log();
        log.setEncriptedData(data);




        // Msg example: 001001G0001$170920223249&S01TA01=22.3

        data = Util.desencriptar(data);

        log.setData(data);

        try {
            String idCliente = data.split("\\$")[0];
            String fechaHora = (data.split("\\$")[1]).split("&")[0];
            String iMei = ((data.split("\\$")[1]).split("&")[1]).split("=")[0];
            String valorSensor = ((data.split("\\$")[1]).split("&")[1]).split("=")[1];

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CST"));

            calendar.set(Integer.valueOf("20"+fechaHora.substring(0, 2)),Integer.valueOf(fechaHora.substring(2, 4)),Integer.valueOf(fechaHora.substring(4, 6)),Integer.valueOf(fechaHora.substring(6, 8)),Integer.valueOf(fechaHora.substring(8, 10)),Integer.valueOf(fechaHora.substring(10, 12)));

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            log.setTimestamp(timestamp);

            logRepository.save(log);

            Mediciones medi = new Mediciones();

            medi.setIdCliente(idCliente);
            medi.setiMei(iMei);
            medi.setValor(Double.parseDouble(valorSensor));
            medi.setTimestamp(new Date());

            medicionesRepository.save(medi);




            return idCliente + " " + fechaHora + " "+ iMei + " " + valorSensor ;


        } catch (Exception e) {
            return e.getMessage();

        }

       // return "";
    }

    

}
