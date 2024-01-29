package application;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * La classe InterfaceGraphique est responsable de l'interface graphique du jeu de mots croisés.
 */
public class InterfaceGraphique {
	private Label menuTexte = new Label(
			"Bienvenue! Pour jouer, cliquez sur un numéro dans la grille, écrivez le mot et "
					+ "cliquez sur OK ou appuyez sur la touche Entrée (Enter) pour continuer");
	private HBox texte = new HBox();;
	private static final InterfaceGraphique instance = new InterfaceGraphique();
	private Scene scene;
	private int cellValue;
	public InterfaceGrille InterfaceGrille;
	private Text textS;
	private Text textM;
	private int Num;
	private Main main;
	private SimpleIntegerProperty numProperty = new SimpleIntegerProperty();
	private BorderPane root = new BorderPane();
	private Label titre = new Label("Sport");
	private VBox titleContainer = new VBox();

	/**
     * Retourne l'instance unique de l'InterfaceGraphique.
     *
     * L'instance unique de l'InterfaceGraphique.
     */
	public static InterfaceGraphique getInstance() {

		return instance;
	}

	/**
    * Crée et retourne la scène principale du jeu de mots croisés.
    *
    * La scène principale du jeu de mots croisés.
    * IOException En cas d'erreur lors de la création de la scène.
    */
	public Scene resoudre() throws IOException {

		Label input = new Label(" ");
		Label lScore = new Label("VOTRE SCORE");
		Label lMax = new Label("MAXIMUM");
		Button lettres = new Button("LETTRES");
		Button solution = new Button("SOLUTION");
		Button ok = new Button("OK");
		Button aide = new Button("AIDE");

		Rectangle score = new Rectangle(140, 50, Color.YELLOW);
		Rectangle numScore = new Rectangle(120, 25, Color.WHITE);
		Rectangle max = new Rectangle(140, 50, Color.YELLOW);
		Rectangle numMax = new Rectangle(120, 25, Color.WHITE);
		int nScore = 0;
		int nMax = 0;

		textS = new Text(Integer.toString(nScore));
		StackPane nuScore = new StackPane(numScore, textS);
		textM = new Text(Integer.toString(nMax));
		StackPane nuMax = new StackPane(numMax, textM);
		StackPane spScore = new StackPane(score, lScore, nuScore);
		StackPane spMax = new StackPane(max, lMax, nuMax);
		
		VBox menuItemsContainer = new VBox();
		HBox hBox1 = new HBox(lettres, solution);
		HBox hBox2 = new HBox(spScore, spMax);
		menuItemsContainer.getChildren().addAll(input, hBox1, ok, hBox2, aide);
		menuItemsContainer.setAlignment(Pos.CENTER);
		hBox1.setAlignment(Pos.CENTER);
		hBox2.setAlignment(Pos.CENTER);
		hBox1.setSpacing(10);
		hBox2.setSpacing(10);
		menuItemsContainer.setSpacing(10);
		
		Rectangle menuBox = new Rectangle(300, 300, Color.WHITE);
		Pane menu = new Pane();
		StackPane menuSlide = new StackPane(menuBox, texte, menuItemsContainer);
		menuSlide.setAlignment(Pos.CENTER);

		// questionnaire

		input.setPadding(new Insets(5, 105, 5, 105));
		input.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;"
				+ "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
		input.setLayoutX(105);
		input.setLayoutY(300);
		input.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

		ok.setPrefSize(50, 40);
		ok.setLayoutX(220);
		ok.setLayoutY(410);
		ok.setStyle("-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;");
		ok.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		ok.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
					try {
						InterfaceGrille.verifierReponse();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		lettres.setPrefSize(140, 50);
		lettres.setLayoutX(100);
		lettres.setLayoutY(350);
		lettres.setStyle("-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;");
		lettres.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		lettres.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getEventType() == MouseEvent.MOUSE_PRESSED)
					input.setText(InterfaceGrille.ecrireLettre(cellValue));
			}

		});

		solution.setPrefSize(140, 50);
		solution.setLayoutX(250);
		solution.setLayoutY(350);
		solution.setStyle("-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;");
		solution.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		solution.setOnAction(event -> {
			InterfaceGrille.ecrireSolution(cellValue);
		});

		nuScore.setPadding(new Insets(20, 0, 0, 0));
		lScore.setPadding(new Insets(0, 0, 25, 0));
		lScore.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		spScore.setLayoutX(100);
		spScore.setLayoutY(460);

		nuMax.setPadding(new Insets(20, 0, 0, 0));
		lMax.setPadding(new Insets(0, 0, 25, 0));
		lMax.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		spMax.setLayoutX(250);
		spMax.setLayoutY(460);

		aide.setPrefSize(100, 50);
		aide.setLayoutX(200);
		aide.setLayoutY(520);
		aide.setStyle("-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;");
		aide.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		aide.setOnAction(event -> {
			aideScene();
		});
		
		StackPane.setAlignment(menu, javafx.geometry.Pos.BOTTOM_RIGHT);
		menuSlide.setPadding(new Insets(0, 40, 140, 0));

		StackPane.setAlignment(texte, javafx.geometry.Pos.BOTTOM_RIGHT);
		texte.setHgrow(menuTexte, javafx.scene.layout.Priority.ALWAYS);
		texte.setPadding(new Insets(0, 10, 0, 0));
		texte.getChildren().add(menuTexte);
		texte.setMaxWidth(300);
		menuTexte.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
		menuBox.setStroke(Color.BLACK);
		root.setRight(menuSlide);

		menuTexte.setWrapText(true);

		titre.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
		titre.setPadding(new Insets(0, 0, 50, 100));
		root.setTop(titleContainer);

		scene = new Scene(root, 1020, 720);
		
		/// GRILLE
		try {
			final GridPane[] gridPane = { new GridPane() };
			gridPane[0] = InterfaceGrille.resoudre();
			root.setLeft(gridPane[0]);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

	/**
     * Affiche une nouvelle fenêtre d'aide expliquant les règles du jeu.
     */
	public void aideScene() {
		Stage aideStage = new Stage();
		Button aideOk = new Button("OK");
		Label titreRegle = new Label("Règles du jeu\n");
		Label descRegle = new Label("Cliquez sur un numéro pour sélectionner le mot\r\n"
				+ "correspondant et obtenir l'indice du mot recherché. Vous\r\n"
				+ "pouvez avoir la solution en cliquant sur le bouton\r\n"
				+ "correspondant. Le bouton Lettres vous fournit, en désordre,\r\n"
				+ "totes les lettres du mot recherché. Pour répondre, tapez les\r\n"
				+ "lettres du mot et validez à l'aide du bouton OK ou la touche\r\n"
				+ "Entrée du clavier. Dans le cas d'une mauvaise réponse, la\r\n"
				+ "solution vous est donnée. Vous pouvez supprimer les\r\n"
				+ "caractères entrés à l'aide de la touche retour arrière de votre\r\n" + "clavier.\r\n");
		Label titreScore = new Label("Calcul du score\n");
		Label descScore = new Label("Un mot exact obtenu seulement à l'aide de l'indice vous\r\n"
				+ "donne un nombre de points égal à deux fois le nombre de\r\n"
				+ "lettres du mot. Le recours aux lettres en désordre vous donne\r\n" + "une fois ce nombre.\r\n");
		Label titreAuteur = new Label("Auteur\n");
		Label descAuteur = new Label("Marcelo Zevallos");

		aideOk.setPadding(new Insets(5, 20, 5, 20));
		titreRegle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
		titreScore.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
		titreAuteur.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

		HBox.setMargin(aideOk, new Insets(0, 10, 0, 0));
		HBox hbox = new HBox(10, aideOk);
		hbox.setAlignment(Pos.BOTTOM_RIGHT);

		VBox vbox = new VBox(titreRegle, descRegle, titreScore, descScore, titreAuteur, descAuteur, hbox);
		VBox.setMargin(titreRegle, new Insets(0, 0, 0, 10));
		VBox.setMargin(descRegle, new Insets(0, 0, 0, 10));
		VBox.setMargin(titreScore, new Insets(0, 0, 0, 10));
		VBox.setMargin(descScore, new Insets(0, 0, 0, 10));
		VBox.setMargin(titreAuteur, new Insets(0, 0, 0, 10));
		VBox.setMargin(descAuteur, new Insets(0, 0, 0, 10));

		aideOk.setOnAction(event -> {
			aideStage.close();
		});

		Scene aideScene = new Scene(vbox, 400, 400);

		aideStage.setScene(aideScene);
		aideStage.setTitle("AIDE");
		aideStage.show();
	}

	public void setBaseTheme(GestionMotCroise gestionMotCroise, GestionDonnee gestionDonnee) {
		this.InterfaceGrille = new InterfaceGrille(gestionMotCroise, gestionDonnee);
	}

	public void setTheme(String titreTheme) {
		try {
			final GridPane[] gridPane = { new GridPane() };
			gridPane[0] = InterfaceGrille.resoudre();
			root.setLeft(gridPane[0]);
			titre.setText(titreTheme);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void setMenu(MenuBar menuOutil) {
		titleContainer.getChildren().addAll(menuOutil, titre);
	}

	public Scene getScene() {
		return scene;
	}

	public void setMenuTexte(String texte) {
		menuTexte.setText(texte);
	}

	public void setMot(List<Mot> mots, int cellValue) {
		this.cellValue = cellValue;

	}

	public void setCase(InterfaceGrille case1) {
		InterfaceGrille = case1;
	}
	
	public void setTextS(String string) {
		this.textS.setText(string);
	}

	public void setTextM(String string) {
		this.textM.setText(string);
	}
}
