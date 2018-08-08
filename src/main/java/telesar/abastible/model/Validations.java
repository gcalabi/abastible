package telesar.abastible.model;

public class Validations {

    public String validateData(String data) {

        if (data == null || data.equals("")) {
            return "001|ERROR|Mensaje Vacio|";
        }

        //Verify that all required section are present in the String
        if (!data.matches("^(.*)\\$[0-9]{12}\\$[0-9]{1,2}[0]{0,1}$")) {
            return "002|ERROR|Mensaje mal formateado|"+data;
        }
        // String iMei

        return "";
    }

    public boolean validatePercentageValue(double value) {
        if (value < 0 || value > 100) {
            return false;
        }

        return true;
    }
}
