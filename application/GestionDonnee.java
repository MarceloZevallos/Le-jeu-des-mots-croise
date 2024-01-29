package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe GestionDonnee est responsable de la lecture des données à partir d'un fichier
 * et de stocker ces données sous forme d'objets Mot.
 */
public class GestionDonnee {
    private char[] sens; // Tableau pour stocker les sens des mots (X ou Y)
    private String[] questions; // Tableau pour stocker les questions
    private String[] reponses; // Tableau pour stocker les réponses
    private List<Mot> mots; // Liste pour stocker les objets Mot

    /**
     * Constructeur de la classe GestionDonnee.
     *
     * @param strNomFichier Le nom du fichier à partir duquel les données doivent être lues.
     * @throws IOException En cas d'erreur lors de la lecture du fichier.
     */
    public GestionDonnee(String strNomFichier) throws IOException {
        BufferedReader brFichier = null;
        int nbrQuestions = 0;
        this.mots = new ArrayList<>();

        try {
            // Initialisation d'un BufferedReader pour lire le fichier
            brFichier = new BufferedReader(new FileReader(strNomFichier));

            // Compte le nombre de lignes dans le fichier pour déterminer la taille des tableaux
            while ((brFichier.readLine()) != null) {
                nbrQuestions++;
            }

            // Initialisation des tableaux avec la taille déterminée
            sens = new char[nbrQuestions];
            questions = new String[nbrQuestions];
            reponses = new String[nbrQuestions];

            // Ferme le BufferedReader existant
            brFichier.close();

            // Crée un nouveau BufferedReader pour réinitialiser et relire le fichier
            brFichier = new BufferedReader(new FileReader(strNomFichier));
            
            // Lit la première ligne du fichier qui contient les tailles des tableaux
            String[] sizes = brFichier.readLine().trim().split(";");
            boolean continuer = true;
            int n = 1;
            sens[0] = sizes[0].charAt(0);
            questions[0] = sizes[1];
            reponses[0] = sizes[2];
            
            // Lit le reste du fichier et remplit les tableaux avec les données
            while (continuer) {
                String line = brFichier.readLine();

                if (line == null) {
                    continuer = false; // Sort de la boucle quand la fin du fichier est atteinte
                } else {
                    String[] words = line.split(";");

                    for (int i = 0; i <= 2; i++) {
                        switch (i) {
                            case 0:
                                sens[n] = words[i].charAt(0);
                                break;
                            case 1:
                                questions[n] = words[i].trim();
                                break;
                            case 2:
                                reponses[n] = words[i].trim();
                                break;
                        }
                    }
                    n++;
                }
            }

            // Crée des objets Mot à partir des données lues et les ajoute à la liste mots
            for (int i = 0; i < sens.length; i++) {
                mots.add(new Mot(sens[i], questions[i], reponses[i]));
            }
        } catch (Exception e) {
            // Gère toute exception pouvant survenir lors de la lecture du fichier
            System.out.println(e.toString());
            e.printStackTrace();
        } finally {
            // Ferme le BufferedReader même en cas d'exception
            if (brFichier != null) {
                try {
                    brFichier.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retourne la liste d'objets Mot créée à partir des données lues depuis le fichier.
     *
     * @return La liste d'objets Mot.
     */
    public List<Mot> getMots() {
        return mots;
    }
}



