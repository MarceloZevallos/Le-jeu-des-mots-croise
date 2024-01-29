package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * La classe Main est responsable de l'initialisation et du lancement de
 * l'application du jeu de mots croisés.
 */
public class Main extends Application {
	private int Num = 0;
	private InterfaceGraphique iG;
	private MenuBar menuOutil = new MenuBar();
	private Menu mTheme = new Menu("Thèmes");
	private MenuItem mSport = new MenuItem("Sport");
	private MenuItem mAnimaux = new MenuItem("Animaux d'Afrique");
	private MenuItem mInfo = new MenuItem("Metier");
	private Menu mAide = new Menu("Aide");
	private MenuItem mAPropos = new MenuItem("À propos...");

	/**
	 * La méthode start initialise l'interface utilisateur et lance l'application.
	 * 
	 * @throws IOException
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		menuOutil.getMenus().addAll(mTheme, mAide);
		mAide.getItems().add(mAPropos);
		mTheme.getItems().addAll(mSport, mAnimaux, mInfo);
		setIGWithNum(Num);
		iG.setMenu(menuOutil);

		// Gestion des événements pour les menus
		mAPropos.setOnAction(event -> {
			iG.aideScene();
		});
		mSport.setOnAction(event -> {
			try {
				setIGWithNum(1);
				setNum(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			iG.setTextS("0");
			iG.setMenuTexte("Bienvenue ! Pour jouer, cliquez sur un numéro dans la grille, écrivez le mot et "
					+ "cliquez sur OK ou appuyez sur la touche Entrée (Enter) pour continuer");
			iG.setTheme("Sport");
		});
		mAnimaux.setOnAction(event -> {
			try {
				setIGWithNum(2);
				setNum(2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			iG.setTextS("0");
			iG.setMenuTexte("Bienvenue ! Pour jouer, cliquez sur un numéro dans la grille, écrivez le mot et "
					+ "cliquez sur OK ou appuyez sur la touche Entrée (Enter) pour continuer");
			iG.setTheme("Animaux d'Afrique");
		});
		mInfo.setOnAction(event -> {
			try {
				setIGWithNum(3);
				setNum(3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			iG.setTextS("0");
			iG.setMenuTexte("Bienvenue ! Pour jouer, cliquez sur un numéro dans la grille, écrivez le mot et "
					+ "cliquez sur OK ou appuyez sur la touche Entrée (Enter) pour continuer");
			iG.setTheme("Metier");
		});

		primaryStage.setTitle("Mot Croisé");
		primaryStage.setScene(iG.resoudre());
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Initialise l'interface graphique avec le numéro de thème spécifié.
	 *
	 * @param num Le numéro du thème.
	 * @throws IOException En cas d'erreur lors de l'initialisation de l'interface
	 *                     graphique.
	 */
	private void setIGWithNum(int num) throws IOException {
		this.Num = num;
		System.out.println(Num);
		if (Num == 0) {
			GestionMotCroise gestionMotCroiseSport = new GestionMotCroise("grilleSport.txt");
			GestionDonnee gestionDonneeSport = new GestionDonnee("donneesSport.txt");
			this.iG = InterfaceGraphique.getInstance();
			this.iG.setBaseTheme(gestionMotCroiseSport, gestionDonneeSport);
		} else if (Num == 1) {
			GestionMotCroise gestionMotCroiseSport = new GestionMotCroise("grilleSport.txt");
			GestionDonnee gestionDonneeSport = new GestionDonnee("donneesSport.txt");
			this.iG = InterfaceGraphique.getInstance();
			this.iG.setBaseTheme(gestionMotCroiseSport, gestionDonneeSport);
		} else if (Num == 2) {
			GestionMotCroise gestionMotCroiseAnimaux = new GestionMotCroise("grilleAnimaux.txt");
			GestionDonnee gestionDonneeAnimaux = new GestionDonnee("donneesAnimaux.txt");
			this.iG = InterfaceGraphique.getInstance();
			this.iG.setBaseTheme(gestionMotCroiseAnimaux, gestionDonneeAnimaux);
		} else if (Num == 3) {
			GestionMotCroise gestionMotCroiseInfo = new GestionMotCroise("grilleMetier.txt");
			GestionDonnee gestionDonneeInfo = new GestionDonnee("donneesMetier.txt");
			this.iG = InterfaceGraphique.getInstance();
			this.iG.setBaseTheme(gestionMotCroiseInfo, gestionDonneeInfo);
		}
	}

	/**
	 * Définit le numéro du thème.
	 *
	 * num Le numéro du thème à définir.
	 */
	public void setNum(int num) {
		Num = num;
	}

	/**
	 * La méthode principale qui lance l'application.
	 *
	 * @param args Les arguments de la ligne de commande.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}