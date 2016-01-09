import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Fonc {

	public static int multiply(int x, int y, int p) {
		int result = 0;
		while (y != 0) {
			if ((y & 1) == 1)
				result = (result + x) % p;
			y >>= 1;
			x = (2 * x) % p;
		}
		return result;
	}


	public static int puissance(int x, int k, int p) {

		int res = 1;

		for (int i = 0; i <= k - 1; i++) {
			res = multiply(res, x, p);
		}

		return res;

	}


	public static int pseudoprime(int p) {

		return puissance(2, p - 1, p);

	}


	public static int nextprime() {

		int rand = 2;

		while (pseudoprime(rand) != 1) {

			rand = (int)(Math.random() * 8388607) + 2;
		}
		return rand;

	}

	/* Transforme fichier en tableau de bytes */

	static byte[] readFile(String fn) {

		File f = new File(fn);

		int filesize = (int) f.length();
		byte data[] = new byte[filesize];

		try {
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			in.readFully(data);
			in.close();
		} catch (IOException e) {
		}
		return data;
	}

	/* Indispensable ! */

	public static int BytetoUnsignedInt(byte b) {

		return (b & 0xFF);

	}

	/*
	 * Parcourir le fichier
	 * 
	 * Dans une variable résultat, enregistrer le premier char ensuite, faire
	 * une boucle dans laquelle on prend le resultat qu'on multiplie par 256 et
	 * auquel on ajoute le nouveau char qu'on lit. Le tout avec les modulos p.
	 */
	public static int fingerprint(int p, String fn) {

		byte[] tab = readFile(fn);
		int res = 0;

		for (int i = 0; i <= tab.length - 1; i++) {

			res = (BytetoUnsignedInt(tab[i]) + res * 256) % p;
		
		}
		return res;

	}

	/* n : taille fichier, fp : fingerprint */

	public boolean find(int p, String F, int fp, int sizef) {
		
		byte[] Fcontent = readFile(F);
		int res = 0;

		for (int i = 0; i < sizef; i++) {
			res = ( BytetoUnsignedInt(Fcontent[i]) + res * 256) % p;
		}
		
		if (res == fp) {
			return true;
		}

		for (int i = sizef; i < Fcontent.length ; i++) {
		
			res = res - multiply (BytetoUnsignedInt(Fcontent[i - sizef]) , puissance(256, sizef -1, p),p); 
			if ( res < 0 ){//TODO: c'est chelou ce truc
				res += p;
			}
			
			res *= 256;
			res += BytetoUnsignedInt(Fcontent[i]);
			res %= p;
			
		
			
			if (res == fp) {
				return true;
			}
			
		}
		return false;
	}

	public int getTailleFichier(String fn) {

		byte[] tab = readFile(fn);
		return tab.length;
	}

	public static boolean compareFiles(String f, String F) {

		Fonc fonc = new Fonc();
		

		int prime = nextprime();
		int fp = fingerprint(prime, f);
		int sizef =  fonc.getTailleFichier(f);
		boolean bool = fonc.find(prime, F, fp, sizef);
		
		System.out.println(bool);
		return bool;
		
	}


}
