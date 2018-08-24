package telesar.abastible.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import telesar.abastible.DAO.ErrorLog;
import telesar.abastible.DAO.Log;
import telesar.abastible.DAO.Mediciones;
import telesar.abastible.Repository.ErrorsLogRepository;
import telesar.abastible.Repository.LogRepository;
import telesar.abastible.Repository.MedicionesRepository;
import telesar.abastible.model.Validations;
import telesar.abastible.utils.Util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


@RestController
@RequestMapping("/api/data")
public class RestAPI {

    @Autowired
    LogRepository logRepository;

    @Autowired
    MedicionesRepository medicionesRepository;

    @Autowired
    ErrorsLogRepository errorsLogRepository;

    @RequestMapping("/")
    public String index() {
        try {
            System.out.println(Util.encriptar("0000001$170920223249$36"));


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/getDataAll")
    public List<Mediciones> getDataAll() {


        return medicionesRepository.findAll(new Sort(Sort.Direction.DESC, "timestampRegistro"));
    }

    @RequestMapping("/getDataBySerial")
    public List<Mediciones> getDataBySerial(HttpServletRequest request, @RequestParam(value = "serial", required = false) String serial, @RequestParam(value = "desde", required = false) String desde, @RequestParam(value = "hasta", required = false) String hasta, Model model) {


        return medicionesRepository.findBySerial(serial, new Sort(Sort.Direction.DESC, "timestampRegistro"));
    }



    @RequestMapping("/getDataByImei")
    public List<Mediciones> getDataByImei(HttpServletRequest request, @RequestParam(value = "imei", required = true) String imei, Model model) {

        medicionesRepository.deleteAll();
        logRepository.deleteAll();
        errorsLogRepository.deleteAll();



        return medicionesRepository.findByImei(imei, new Sort(Sort.Direction.DESC, "timestampRegistro"));
    }

    @RequestMapping("/getData")
    public List<Mediciones> getData(HttpServletRequest request, @RequestParam(value = "startDate", required = true) String startDate, @RequestParam(value = "endDate", required = true) String endDate, @RequestParam(value = "serial", required = false) String serial, Model model) {


        return medicionesRepository.findAll(new Sort(Sort.Direction.DESC, "timestampRegistro"));
    }


    @RequestMapping("/getLog")
    public List<Log> getLog(HttpServletRequest request, @RequestParam(value = "fechaInicio", required = false) String fechaInicio, @RequestParam(value = "fechaFin", required = false) String fechaFin, Model model) {

        return logRepository.findAll();
    }

    @RequestMapping("/getErrorLog")
    public List<ErrorLog> getErrorLog(HttpServletRequest request, @RequestParam(value = "fechaInicio", required = false) String fechaInicio, @RequestParam(value = "fechaFin", required = false) String fechaFin, Model model) {


        return errorsLogRepository.findAll();
    }

//TODO: Agregar alertas ante algun error de recepcion de datos
    @RequestMapping("/data")
    @PostMapping
    public String submitData(HttpServletRequest request, @RequestParam(value = "data", required = true, defaultValue = "") String data, Model model) throws Exception {

        // Msg example: [IMEI]$[FECHA HORA]$[Valor Sensor]

        Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
        boolean grabeLog = false;

        Log log = new Log();
        ErrorLog errorLog = new ErrorLog();
        Validations validations = new Validations();
        String errors = "";

        try {

            log.setEncriptedData(data);
            log.setTimestamp(timestampActual);

            errorLog.setEncriptedData(data);
            errorLog.setTimestamp(timestampActual);

            try {
                data = Util.desencriptar(data);
            } catch (Exception e) {
                logRepository.save(log);
                grabeLog = true;
                errorLog.setErrorMsg("Error al desencriptar");
                errorLog.setErrorException(e.toString());
                errorsLogRepository.save(errorLog);
                return "Error al desencriptar. Mesaje: " + e.getMessage();
            }

            //Logre desencriptar. Guardo mensaje en Log.
            log.setData(data);
            logRepository.save(log);
            grabeLog = true;

            //Valido el mensaje en cuanto a formato.

            errors = validations.validateData(data);

            if (errors.length() > 0){
                errorLog.setErrorMsg("Error al Validar");
                errorLog.setErrorException(errors);
                errorsLogRepository.save(errorLog);

                return errors;
            }


            //Extraigo los valores de el mensaje(Parseo de el mensaje)

            String imei = "";
            String serialNumber = data.split("\\$")[0];
            String fechaHora = data.split("\\$")[1];
            String valorSensor = data.split("\\$")[2];

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CST"));

            try {
                calendar.set(Integer.valueOf("20" + fechaHora.substring(0, 2)), Integer.valueOf(fechaHora.substring(2, 4)), Integer.valueOf(fechaHora.substring(4, 6)), Integer.valueOf(fechaHora.substring(6, 8)), Integer.valueOf(fechaHora.substring(8, 10)), Integer.valueOf(fechaHora.substring(10, 12)));
            } catch (Exception e) {
                errorLog.setErrorMsg("Error, Formato fecha Hora incorrecto");
                errorLog.setErrorException("Me llego "+ fechaHora);
                errorsLogRepository.save(errorLog);
                return errors;
            }


            //Resultado ok. Guardamos la medicion en la Base
            Mediciones medi = new Mediciones();

            double medicion =Double.parseDouble(valorSensor);

            if (!validations.validatePercentageValue(medicion)) {
                errorLog.setErrorMsg("Error, Valor de medición fuera de rango(porcentaje permitido entre 0 y 100");
                errorLog.setErrorException("Me llego "+ valorSensor);
                errorsLogRepository.save(errorLog);

                return "Error, Valor de medición fuera de rango(porcentaje permitido entre 0 y 100";
            }

            medi.setSerial(serialNumber);
            medi.setImei(imei);
            medi.setValor(Double.parseDouble(valorSensor));
            medi.setTimestampMedicion(new Timestamp(calendar.getTimeInMillis()));
            medi.setTimestampRegistro(timestampActual);

            medicionesRepository.save(medi);



            //TODO: Definir con Daniel formato respuesta
            return serialNumber + " " + fechaHora + " "+ imei + " " + valorSensor ;


        } catch (Exception e) {
            if (!grabeLog) {
                logRepository.save(log);
            }

            errorLog.setErrorMsg("Error al desencriptar");
            errorLog.setErrorException(e.toString());
            errorsLogRepository.save(errorLog);
            return "Error Generico. Mesaje: " + e.getMessage();

        }

    }


}
