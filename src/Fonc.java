import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Fonc {

	public int multiply(int x, int y, int p) {
		int result = 0;
		while (y != 0) {
			if ((y & 1) == 1)
				result = (result + x) % p;
			y >>= 1;
			x = (2 * x) % p;
		}
		return result;
	}


	public int puissance(int x, int k, int p) {

		int res = 1;

		for (int i = 0; i <= k - 1; i++) {
			res = multiply(res, x, p);
		}

		return res;

	}


	public int pseudoprime(int p) {

		return puissance(2, p - 1, p);

	}


	public int nextprime() {

		int rand = 2;

		while (pseudoprime(rand) != 1) {

			rand = (int)(Math.random() * 83886) + 2;
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
	@SuppressWarnings("resource")
	public int fingerprint(int p, String fn) {

		byte[] tab = readFile(fn);
		int res = 0;

		for (int i = 0; i <= tab.length - 1; i++) {

			res = (BytetoUnsignedInt(tab[i]) + res * 256) % p;
		
		}
		return res;

	}

	/* n : taille fichier, fp : fingerprint */

	public boolean find(int p, String fn, int fp, int n) {
		byte[] tab = readFile(fn);
		int res = 0;

		for (int i = 0; i <= n-1; i++) {

			res = (BytetoUnsignedInt(tab[i]) + res * 256) % p;

		}
		
		if (res == fp) {
			return true;
		}

		for (int i = n; i <= tab.length - 1; i++) {
			
			res = res - multiply (BytetoUnsignedInt(tab[i - n]) , puissance(256, n -1, p),p);
			
			if ( res < 0 ){
				res += p;
			}
			
			res *= 256;
			res += BytetoUnsignedInt(tab[i]);
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

	public static void main(String args[]) {

		Fonc fonc = new Fonc();
		
		System.out.println("Chemin fichier f?");
		Scanner sc = new Scanner(System.in);
		String arg1 = sc.nextLine();
		System.out.println("Chemin fichier F?");
		String arg2 = sc.nextLine();
		System.out.println("Veuillez Patienter...");

		int a = fonc.nextprime();
		
		int fp = fonc.fingerprint(a, arg1);
		boolean bool = fonc.find(a, arg2, fp,fonc.getTailleFichier(arg1));
		
		System.out.println(fp);
		System.out.println();
		System.out.println(bool);

	}
}
