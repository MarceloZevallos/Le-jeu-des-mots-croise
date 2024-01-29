package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GestionMotCroise {
	
	private int[][] tabLettres;
    private int x;
    private int y;

    GestionMotCroise(String strNomFichier) {
        BufferedReader brFichier = null;
        try {
            brFichier = new BufferedReader(new FileReader(strNomFichier));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String[] sizes = brFichier.readLine().trim().split(",");
            x = Integer.parseInt(sizes[0].trim());//15
            y = Integer.parseInt(sizes[1].trim());//13
            this.tabLettres = new int[x][y];

            for (int i = 0; i < x; i++) {
                String line = brFichier.readLine();
                String[] words = line.split(",");
                for (int j = 0; j < y; j++) {
                    tabLettres[i][j] = Integer.parseInt(words[j].trim());
                }
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
    public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public int getGrille(int x, int y) {
    	return tabLettres[x][y];
    }

}
