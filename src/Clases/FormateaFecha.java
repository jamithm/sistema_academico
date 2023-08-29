package Clases;

import java.util.Date;
import java.text.*;

public class FormateaFecha {
    public String devuelve;
    private String error;

    public FormateaFecha() {
        super();
    }

    public void fechaRecuperaBaseDato(String fechaM){
        fechaM=fechaM.replace('-','/');

        if (isDate(fechaM)){
            Date fechaDate=new Date(fechaM);
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            devuelve=sdf.format(fechaDate);
        } else {
            devuelve="";
        }
    }

    public void fechaGrabaBaseDato(String fechaN){
        try{
            if (isDate(fechaN)){
                SimpleDateFormat sdfD = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdfG = new SimpleDateFormat("yyyy-MM-dd");
                devuelve=sdfG.format(sdfD.parse(fechaN));
            }else{
                devuelve="";
            }
        } catch(Exception e) {
            error = e.getMessage();
            e.printStackTrace(); 
            System.out.println("Error : " + e);
        }
    }

    public boolean isDate(String fechax){
        try {
            Date fecha = new Date(fechax);
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    public String getDevuelve() {
        return devuelve;
    }
}