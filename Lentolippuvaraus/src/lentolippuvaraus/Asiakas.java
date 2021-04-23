package lentolippuvaraus;

import java.io.File;
import java.util.Random;

public class Asiakas {

	private String id;
	private String etunimi;
	private String sukunimi;
	private Paikka paikka;
	
	public Asiakas(String etunimi, String sukunimi) {
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		
	}
	
	public String annaNimi() {
		
		return  etunimi + " " + sukunimi; 
	}
	
	public void asetaPaikka(Paikka paikka) {
	  this.paikka = paikka;
  }
  	
	public Paikka annaPaikka() {
	  return paikka;
  }
  
  	public boolean onkoPaikka() {
	  if(this.paikka != null) {
		  return false;
	  }else {return true;
	  }
  }
  
  	public void asetaID(Asiakas asiakas){
	  
	  Random r = new Random();
	  int rdn = r.nextInt(100);
	  this.id = "" + rdn;
  }
  	
  	public void asetaID(String id) {
	  this.id = id;
  }
 
	public String annaID() {
	  return id;
  }
	
	public String toString() {
		
		return "Asiakas: " + annaNimi() + " Paikan numero " + annaPaikka().annaNumero() + " ID: " + annaID();
	}
}
