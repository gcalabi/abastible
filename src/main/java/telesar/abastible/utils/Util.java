package telesar.abastible.utils;



import java.io.BufferedWriter;

import java.io.FileWriter;

import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.util.Base64;

import java.util.Date;



public class Util {



    public static String encriptar(String s) throws UnsupportedEncodingException {

        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));

    }



    public static String desencriptar(String s) throws UnsupportedEncodingException {

        byte[] decode = Base64.getDecoder().decode(s.getBytes());

        return new String(decode, "utf-8");

    }



    public static void writeToFile(String filename, String content) {

        BufferedWriter bw = null;

        FileWriter fw = null;



        try {





            fw = new FileWriter(filename, true);

            bw = new BufferedWriter(fw);

            bw.write(new Date().toString() + " - " + content);



            System.out.println("Done");



        } catch (IOException e) {



            e.printStackTrace();



        } finally {



            try {



                if (bw != null)

                    bw.close();



                if (fw != null)

                    fw.close();



            } catch (IOException ex) {



                ex.printStackTrace();



            }

        }

    }



    public static void cleanFile(String filename) {

        BufferedWriter bw = null;

        FileWriter fw = null;



        try {





            fw = new FileWriter(filename, false);

            bw = new BufferedWriter(fw);

            bw.write("");





        } catch (IOException e) {



            e.printStackTrace();



        } finally {



            try {



                if (bw != null)

                    bw.close();



                if (fw != null)

                    fw.close();



            } catch (IOException ex) {



                ex.printStackTrace();



            }

        }

    }

}