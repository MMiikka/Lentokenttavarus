package lentolippuvaraus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
/**
 * 
 * @author jonmer
 *
 */
public class Ohjelma {
	
	
	/**
	 * Päävalikko-metodi on ensimmäinen metodi, joka aloittaa vuorovaikutuksen käyttäjän kanssa ohjelman käynnistyessä.
	 * Metodin aikana luojaan käyttäjästä asiakas-olio.
	 */
	public void paavalikko() {
		File f =  new File("varaus.txt");
		Scanner s = new Scanner(System.in);
		System.out.println("Hei, tervetuloa äkkilähtöjen varausjärjestelmään! \n Syötä etu- ja sukunimesi");
		try {
		Asiakas nimi = luoAsiakas(s);
	
		System.out.println("Hei, " + nimi.annaNimi()+ "! Asiakas ID:si on: " + nimi.annaID() + " \n Käytä tätä ID:tä ku haluat tarkastella varaustasi.");
		System.out.println();
		System.out.println("Haluatko tarkastella varauksiasi (0) vai luoda uuden varauksen? (1)");

		try {
		if(s.nextInt() == 0) {
			List<String> varaukset = tarkistaVaraus(f,nimi.annaID());
			if(!varaukset.isEmpty()) {
			System.out.println("Tässä on aikaisempi varauksesi: ID, nimi, paikka, koneen malli ja kohdemaa.");
			System.out.println(varaukset.toString());
			}else {System.out.println("Varauksia ei löytynyt!");}
			valikko(nimi);
			
		}else{ 
			kaynnista(nimi);
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		s.close();
		}catch(Exception e) {
			
			System.err.print("Kirjoita sekä etunimi ja sukunimi");
			paavalikko();
		}
		
	}
	/**
	 * Käynnistää varausjärjestelmäohjelman varauksen luonti osion.
	 * Ohjelma käynnistetään vasta kun asiakas-olio on luotu käyttäjän syötteestä.
	 * Tämän hetkisessä versiossa myös mahdolliset lentokoneet tuodaan ohjelmaan tässä vaiheessa.
	 * Metodin tarkoituksena, on mahdollistaa paikan varaus lentokoneista.
	 * @param nimi
	 */
	public void kaynnista(Asiakas nimi) {
		
		//luodaan koneet
		ArrayList<Paikka> paikat = new ArrayList<>();
		Random r = new Random();
		int random = r.nextInt(20);
		for(int i = 1; i<=20;i++) {
			Paikka p = new Paikka(r.nextBoolean(),i);
			paikat.add(p);

		}
		for(int i = 0;i<paikat.size();i++) {
			paikat.get(random).asetaVarattu(r.nextBoolean());
			
		}
		
		Lentokone ruotsi = new Lentokone(paikat,Maat.RUOTSI);
		ruotsi.asetaMalli("Boaeng A57");
		Lentokone norja = new Lentokone(paikat, Maat.NORJA);
		norja.asetaMalli("SAAP B45");
		Lentokone espanja = new Lentokone(paikat, Maat.ESPANJA);
		espanja.asetaMalli("VOLGSFLYGEN WG99");
		
		
		System.out.println(" Hei, tervetuloa varaamaan lentosi! ");
		Scanner s = new Scanner(System.in);
		
		System.out.println("Mikä on määränpääsi?  \n Vaihtoehtosi ovat tässä");
		System.out.println(" 1 = Ruotsi, 2 = Norja, 3 = Espanja");
		System.out.println("Vapaita paikkoja on koneissa, jotka lentävät: ");

		String numero = s.nextLine();
		
		Maat kohdemaa = null;
		
		switch(numero) {
		case "1":
			System.out.println("Valitsit Ruotsin");
			kohdemaa = Maat.RUOTSI;
			kohdeMaa(ruotsi, nimi);
	
			break;
		case "2":
			System.out.println("Valitsit Norjan");
			kohdemaa = Maat.NORJA;
			kohdeMaa(norja, nimi);
	
			break;
		case "3": 
			System.out.println("Valitsit Espanjan");
			kohdemaa = Maat.ESPANJA;
			kohdeMaa(espanja, nimi);
			break;
		default: 
			System.out.println("Et valinnut oikeaa numeroa!");
			break;
		}
		s.close();
	}
		/**
		 * KohdeMaa-metodi aloittaa varausprosessin sille lentokoneelle, joka lentää valittuun kohdemaahan. Metodi tarkistaa onko koneessa vapaita paikkoja ja tulostaa ne käyttäjälle.
		 * Tämän jälkeen käyttäjä valitsee paikkansa koneesta.
		 * @param kone,	kone johon varaus tehdään
		 * @param nimi, asiakas joka tekee varauksen
		 */
	public void kohdeMaa(Lentokone kone, Asiakas nimi) {
		
			Scanner s = new Scanner(System.in);
				System.out.println("Tässä kyseisen lennon vapaat paikat");
				
					for(int i = 0; i< kone.annaPaikat().size();i++) {
						if(i == 2 || i == 6 || i == 10 || i == 14 || i== 18) {
							System.out.print("  ");
						}
						if(i == 4 || i == 8 || i == 12 || i == 16) {
							System.out.println(" ");
						}
					int vapaa =	kone.annaPaikat().get(i).annaVapaatPaikat();
					System.out.print(vapaa);
				if(i != kone.annaPaikat().size() -1) {
					System.out.print(",");
				}
					}
					System.out.println();
				
				
				System.out.println("Valitse vapaa paikka kirjoittamalla paikan numero. \n Paikka, jossa on 0 on varattu ");
				
			
				do {
					try {
						
				int paikka = s.nextInt() - 1 ;
			
				
					if(kone.annaPaikat().get(paikka).vapaa()) {
							kone.annaPaikat().get(paikka).asetaAsiakas(nimi);
							nimi.asetaPaikka(kone.annaPaikat().get(paikka));
							Varaus v = new Varaus(nimi, kone.annaPaikat().get(paikka), kone,kone.annaMaa());
							v.asetaVarauksenID(nimi.annaID());
							System.out.println();
							System.out.println(nimi.toString());
				
							
							try {
							
							kirjoitaTiedosto(v);
							System.out.println("Tallennus Onnistui");
							
							} catch(Exception e) {
								System.out.println("Tallennus tiedostoon ei onnistunut! ");
							}
							s.close();
							
							System.out.println("Varasit paikan " + (paikka + 1) + " koneeseen, joka lähtee tänään kohteeseen: " + kone.annaMaa());
				
					}else {
						System.out.println("Paikka ei ole vapaana, valitse uudelleen!");
					}
					
				
				}catch(Exception e) {
					System.out.println("Kirjoita sallittu arvo!");
					s.nextLine();
				}

			
				}while(nimi.onkoPaikka());
					
				
				System.out.println("Tässä vielä vapaat paikat");
				for(int i = 0; i< kone.annaPaikat().size();i++) {
					if(i == 2 || i == 6 || i == 10 || i == 14 || i== 18) {
						System.out.print("  ");
					}
					if(i == 4 || i == 8 || i == 12 || i == 16) {
						System.out.println(" ");
					}
				int vapaa =	kone.annaPaikat().get(i).annaVapaatPaikat();
				
					System.out.print(vapaa);
				if(i != kone.annaPaikat().size() -1) {
					System.out.print(",");
				}
				
				}				
				
				

				
		}
/**
 * Valikko-metodilla tehdään valinta uuden varauksen teosta ko. asiakkaalle.
 * @param asiakas
 */
	public void valikko(Asiakas asiakas) {
			System.out.println("Haluatko jatkaa varauksen tekoon? 1: kyllä, 0: Ei(ohjelma päättyy)");
			Scanner s = new Scanner(System.in);
			String valinta = s.nextLine();
			
			switch(valinta) {
			case "1":
				kaynnista(asiakas);
				break;
			case "0":
				System.out.println("Kiitos, ja hei!");
				break;
				default:
					System.out.println("Et valinnut kunnon arvoa!");
					paavalikko();
			}
		}
	
/**
 * luoAsiakas() metodissa luodaan asiakas käyttäjän antamasta nimi-syötteestä.
 * Metodin aikana myös annetaan asiakkaalle ID, sen perusteella onko asiakkaalla aijempaa varausta.
 * Jos varaus löytyy annetaan asiakkaalle se ID, joka varauksessa on. Muutoin asiakkaalle luodaan uusi ID.
 * @param s	parametrina on käyttäjän syöte.
 * @return Palauttaa asiakas olion.
 * @throws IOException
 */
	public Asiakas luoAsiakas(Scanner s) throws IOException {
		File f = new File("varaus.txt");
		List<String> lista = new ArrayList<>();
		lista = Files.readAllLines(f.toPath());
		
		String[] arvo = new String[lista.size()];
		for(String sana : lista) {
		arvo = sana.split(",");
		
		}
		if(!lista.isEmpty()) {
		lista.add(arvo[0]);
		}
		String nimi = s.nextLine();
		String[] kokoNimi = nimi.split(" ");
		Asiakas asiakas = new Asiakas(kokoNimi[0], kokoNimi[1]);
		if(lista.isEmpty()) {
		asiakas.asetaID(asiakas);
		}else {
			asiakas.asetaID(lista.get(lista.size()-1));
		}
		
		
	return asiakas;
}
	/**
	 * Metodi tarkistaa varauksen ohjelman alussa.
	 * @param f
	 * @param id
	 * @return Palauttaa listan varauksen sisältämistä asioista merkkijonoina.
	 * @throws IOException
	 */
	public List<String> tarkistaVaraus(File f,String id) throws IOException {

		List<String> rivit = new ArrayList<>();
		rivit = Files.readAllLines(f.toPath());
		String[] arvo = new String[rivit.size()];
		for(String s : rivit) {
		arvo = s.split(",");
		
		}
		
		return rivit;
	}
	/**
	 * Kirjoittaa uuden tekstitiedoston, joka sisältää asiakkaan varauksen. Metodin parametriksi annetaan luotu varaus-olio.
	 * @param varaus
	 * @throws IOException
	 */
	public void kirjoitaTiedosto(Varaus varaus) throws IOException {
		 File f = new File("varaus.txt");
		FileWriter fw = new FileWriter(f);
		 fw.write(varaus.toString());
		 fw.flush();
		 fw.close();
		}

}

