package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.stream.EventFilter;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Cette classe représente l'interface graphique de la grille du jeu de mots
 * croisés.
 */

public class InterfaceGrille {
	
	private GestionMotCroise gestionMotCroise;
	private List<Mot> mots = new ArrayList<>();;
	private GridPane gridPane;
	private InterfaceGraphique iG = InterfaceGraphique.getInstance();
	private int posX = 0;
	private int posY = 0;
	private String grilleMot = " ";
	private int score = 0;
	private int maxScore = 0;
	private int colonne;
	private int rangee;
	private int indexValue;
	private int numPrecedent = -1;
	private int aire = 0;
	EventHandler ecrire;
	private HashMap<Integer, Boolean> executedMethods = new HashMap<>();
	private HashMap<Integer, Boolean> estSemi = new HashMap<>();
	private HashMap<Integer, Boolean> estFini = new HashMap<>();

	/**
	 * Constructeur de l'interface de la grille.
	 *
	 * gestionMotCroise Instance de GestionMotCroise contenant les données de la
	 * grille. gestionDonnee Instance de GestionDonnee contenant les mots et leurs
	 * réponses.
	 */

	public InterfaceGrille(GestionMotCroise gestionMotCroise, GestionDonnee gestionDonnee) {
		this.gestionMotCroise = gestionMotCroise;
		this.mots = gestionDonnee.getMots();
		colonne = gestionMotCroise.getX();
		rangee = gestionMotCroise.getY();
		aire = colonne*rangee;
		System.out.println("Colonne: " + colonne + " rangee: " + rangee + "Aire: " + (colonne*rangee));
	}

	/**
	 * Résout la grille du jeu de mots croisés.
	 *
	 * La grille du jeu de mots croisés sous forme d'un objet GridPane. IOException
	 * Si une erreur d'entrée/sortie se produit.
	 */

	public GridPane resoudre() throws IOException {
		this.gridPane = new GridPane();
		gridPane.setPadding(new Insets(0, 0, 400, 0));

		for (int i = 0; i < gestionMotCroise.getY(); i++) {
			for (int j = 0; j < gestionMotCroise.getX(); j++) {
				int cellValue = gestionMotCroise.getGrille(j, i);
				if (cellValue == 0) {
					StackPane rectanglePane = (StackPane) createEmptyRectangle();
					gridPane.add(rectanglePane, i, j);

				} else if (cellValue != -1) {
					StackPane circlePane = (StackPane) createNumberedCircle(cellValue);
					gridPane.add(circlePane, i, j);
					executedMethods.put(cellValue, false);
					
					circlePane.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
						public void handle(MouseEvent e) {
							for (int k = 1; k <= mots.size(); k++) {
								if (!executedMethods.get(cellValue) && cellValue == k
										&& e.getEventType() == MouseEvent.MOUSE_PRESSED) {
									int y = GridPane.getColumnIndex(circlePane);
									int x = GridPane.getRowIndex(circlePane);
									posX = x;
									posY = y;
									indexValue = cellValue;
									iG.setMenuTexte("Mot " + cellValue + "\n\n" + mots.get(cellValue - 1).getQuestions());

									estFini.put( cellValue, false);
									executedMethods.put(cellValue, true);
									System.out.println(estSemi.get(cellValue));
									if(estSemi.get(cellValue)==null) {
										estSemi.put(cellValue, false);
									}
									circlePane.removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
									circlePane.addEventHandler(MouseEvent.MOUSE_PRESSED, this);

									verifierSens(mots.get(cellValue - 1).getSens(), gridPane, iG.getScene(),
											mots.get(cellValue - 1).getReponses().length());
									iG.setMot(mots, cellValue);
									return;
								}
							}
							/*for(int i = 0; i<mots.size();i++) {
								int j = 0;
								if(executedMethods.get(i)!=null || executedMethods.get(i)==true ) {
									j++;
								}
								if(j>1) {
									System.out.println("STOP");
								}
								System.out.println(j);
							}*/
							Boolean value = estFini.get(numPrecedent);
							System.out.println(numPrecedent + " " + value + " " + executedMethods.get(numPrecedent)+" "+indexValue);
							if (value != null && value.equals(false)&&executedMethods.get(numPrecedent)==true&&e.getEventType()==MouseEvent.MOUSE_MOVED) {
								iG.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, ecrire);
							    System.out.println("X: " + getPosX() + " Y: " + getPosY());
								if(mots.get(numPrecedent-1).getSens()=='H') {
									for (int x = 0; x < grilleMot.length(); x++) {
										int columnIndex = getPosX();
										int rowIndex = getPosY();
										int index = (rowIndex - x) * colonne + columnIndex;
										StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
										System.out.println(index);
										existingPane.getChildren().clear();
										Rectangle rectangle = new Rectangle(25, 25, Color.WHITE);
										Text texte = new Text(" ");
										rectangle.setStroke(Color.BLACK);
										texte.setFill(Color.BLACK);
										existingPane.getChildren().addAll(rectangle,texte);
										executedMethods.put(indexValue, false);
										System.out.println("Classe applé");
									}
								} else if(mots.get(numPrecedent-1).getSens()=='V') {
									for (int x = 0; x < grilleMot.length(); x++) {
										int columnIndex = getPosX();
										int rowIndex = getPosY();
										int index = (rowIndex) * colonne + columnIndex - x;
										StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
										System.out.println(index);
										existingPane.getChildren().clear();
										Rectangle rectangle = new Rectangle(25, 25, Color.WHITE);
										Text texte = new Text(" ");
										rectangle.setStroke(Color.BLACK);
										texte.setFill(Color.BLACK);
										existingPane.getChildren().addAll(rectangle,texte);
										executedMethods.put(indexValue, false);
									}
								}
							}
						}
					});
					
					
				} else if (cellValue == -1) {
					Label label = (Label) createEmptyLabel(cellValue);
					StackPane sp = new StackPane(label);
					gridPane.add(sp, i, j);
				}
//System.out.println("Nbr questions: " + mots.size() + " Y: " + gestionMotCroise.getY());
			}
		}

		setMaxScore();
		gridPane.setHgap(gestionMotCroise.getY() - 1);
		gridPane.setVgap(gestionMotCroise.getX() - 1);

		gridPane.setAlignment(Pos.TOP_LEFT);
		return gridPane;

	}

	private Node createEmptyLabel(int number) {
		Label label = new Label(" ");
		return label;
	}

	private Node createNumberedCircle(int number) {
		Circle circle = new Circle(12, Color.BLACK);
		Text text = new Text(Integer.toString(number));
		text.setFill(Color.WHITE);

		StackPane stack = new StackPane(circle, text);
		return stack;
	}

	private Node createEmptyRectangle() {
		Rectangle rectangle = new Rectangle(25, 25, Color.WHITE);
		Text texte = new Text(" ");

		rectangle.setStroke(Color.BLACK);
		rectangle.setStyle("-fx-border-radius: 10px;-fx-background-radius: 10px;");
		StackPane stack = new StackPane(rectangle, texte);
		return stack;
	}

	/**
	 * Vérifie la réponse de l'utilisateur.
	 *
	 * IOException Si une erreur d'entrée/sortie se produit.
	 */

	public void verifierReponse() throws IOException {
		Platform.runLater(() -> {
			System.out.println("GrilleMot: " + grilleMot);
			for (int i = 0; i < gestionMotCroise.getY(); i++) {
				for (int j = 0; j < gestionMotCroise.getX(); j++) {
					int cellValue = indexValue;
					System.out.println(
							"cellValue: " + cellValue + " (x,y): " + j + ", " + i + " " + getPosX() + " " + getPosY());
					if (cellValue != -1) {
						String reponse = mots.get(cellValue - 1).getReponses().toLowerCase();
						boolean sens = verifierSensR(mots.get(cellValue - 1).getSens());
						if (grilleMot.toLowerCase().equals(reponse) && estSemi.get(cellValue).equals(false)) {
							if (sens == true) {
								ecrireReponseY();
								return;
							} else if (sens == false) {
								ecrireReponseX();
								return;
							}

						} else if (grilleMot.toLowerCase().equals(reponse) && estSemi.get(cellValue).equals(true)) {
							if (sens == true) {
								ecrireSemiReponseY();
								return;
							} else if (sens == false) {
								ecrireSemiReponseX();
								return;
							}

						} else if (!grilleMot.toLowerCase().equals(reponse)) {
							if (sens == true) {
								ecrireMauvaiseReponseY();
								return;
							} else if (sens == false) {
								ecrireMauvaiseReponseX();
								return;
							}

						}
					}
				}
			}
		});

	}

	/*
	 * Création d'un mot ayant des lettres aléatoires
	 */
	public String ecrireLettre(int valeur) {
		try {
			grilleMot = mots.get(valeur - 1).getReponses();
			char[] characters = grilleMot.toCharArray();
			Random random = new Random();
			for (int i = characters.length - 1; i > 0; i--) {
				int index = random.nextInt(i + 1);
				char temp = characters[i];
				characters[i] = characters[index];
				characters[index] = temp;
			}
			System.out.println("valeur: " + valeur);
			String motShuffle = new String(characters);
			estSemi.put(valeur, true);
			System.out.println(valeur + " " + estSemi.get(valeur));
			executedMethods.put(valeur, false);
			iG.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, ecrire);
			return motShuffle.toUpperCase();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return"";
	}

	/**
	 * Écriture de la réponse dans le sens horizontal (X).
	 */
	public void ecrireX(GridPane grille, Scene scene, int longueur) {
		ArrayList<String> mot = new ArrayList<String>();
		grilleMot = "";
		int x = getPosX();
		int y = getPosY();
		if (executedMethods.get(indexValue) == false) {
			System.out.println("false");
			return;
		}
		ecrire = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println("Touche pressée : " + event.getCode());
				// Vérifier s'il y a une position sélectionnée sur la grille
				if (getPosX() != -1 && getPosY() != -1) {
					int columnIndex = getPosX(); // Obtenir la coordonnée X de la position sélectionnée
					int rowIndex = getPosY(); // Obtenir la coordonnée Y de la position sélectionnée

					int index = (rowIndex + 1) * colonne + columnIndex;
					System.out.println("index : " + index + " Ligne : " + rowIndex + " Colonne : " + columnIndex);

					StackPane existingPane = (StackPane) grille.getChildren().get(index);

					if (event.getCode() == KeyCode.BACK_SPACE) {
						// Supprimer le dernier caractère de l'ArrayList
						if (!mot.isEmpty()) {
							mot.remove(mot.size() - 1);
						}
						existingPane = (StackPane) grille.getChildren().get(index - colonne);
						if (!existingPane.getChildren().isEmpty()) {
							existingPane.getChildren().remove(existingPane.getChildren().size() - 1);
						}

						// Passer à la case précédente
						setPosY(rowIndex - 1);
						// System.out.println("PosY : " + getPosY());

						event.consume();

					} else {
						numPrecedent = indexValue;
						// Gérer les autres touches
						String keyText = event.getText().toUpperCase();
						if (!keyText.isEmpty() && mot.size() < longueur) {
							// Créer un nouveau nœud Text avec la touche pressée
							Text newText = new Text(keyText);
							newText.setFill(Color.BLACK);

							// Ajouter le nouveau nœud Text à l'StackPane existant
							existingPane.getChildren().add(newText);
							mot.add(keyText);

							grilleMot = String.join("", mot);

							// Passer à la case suivante
							setPosY(rowIndex + 1);
							for (int i = 0; i < longueur; i++) {
								try {
									System.out.print(mot.get(i));
								} catch (Exception e) {

								}

							}
							System.out.println();
							// System.out.println("Dans ecrireX : " + grilleMot);
							setGrilleMot(grilleMot);
							event.consume();
						}
					}
				}
				if (event.getCode() == KeyCode.ENTER) {
					try {
						scene.removeEventFilter(KeyEvent.KEY_PRESSED, this);
						verifierReponse();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		};
		scene.addEventFilter(KeyEvent.KEY_PRESSED, ecrire);

		setPosX(x);
		setPosY(y);
	}

	/**
	 * Écriture de la réponse dans le sens vertical (Y).
	 */
	public void ecrireY(GridPane grille, Scene scene, int longueur) {
		ArrayList<String> mot = new ArrayList<String>();
		grilleMot = "";
		ecrire = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println("Touche pressée : " + event.getCode());

				// Vérifier s'il y a une position sélectionnée sur la grille
				if (getPosX() != -1 && getPosY() != -1) {
					int columnIndex = getPosY(); // Obtenir la coordonnée X de la position sélectionnée
					int rowIndex = getPosX(); // Obtenir la coordonnée Y de la position sélectionnée
					int index = columnIndex * colonne + rowIndex + 1;
					System.out.println("index : " + index + " Ligne : " + rowIndex + " Colonne : " + columnIndex);
					System.out.println("Aire: " + (colonne*rangee) + " index et rangee: " + indexValue + " " + rangee + " airereal: "+aire);
					StackPane existingPane = (StackPane) grille.getChildren().get(index);

					if (event.getCode() == KeyCode.BACK_SPACE) {
						// Supprimer le dernier caractère de l'ArrayList
						if (!mot.isEmpty()) {
							mot.remove(mot.size() - 1);
						}
						existingPane = (StackPane) grille.getChildren().get(index - 1);
						if (!existingPane.getChildren().isEmpty()) {
							existingPane.getChildren().remove(existingPane.getChildren().size() - 1);
						}

						// Passer à la case précédente
						setPosX(rowIndex - 1);
						System.out.println("PosY : " + getPosY());

						event.consume();

					} else {
						numPrecedent = indexValue;
						// Gérer les autres touches
						String keyText = event.getText().toUpperCase();
						if (!keyText.isEmpty() && mot.size() < longueur) {
							// Créer un nouveau nœud Text avec la touche pressée
							Text newText = new Text(keyText);
							newText.setFill(Color.BLACK);

							// Ajouter le nouveau nœud Text à l'StackPane existant
							existingPane.getChildren().add(newText);
							mot.add(keyText);

							grilleMot = String.join("", mot);

							// Passer à la case suivante
							setPosX(rowIndex + 1);
							for (int i = 0; i < longueur; i++) {
								try {
									System.out.print(mot.get(i));
								} catch (Exception e) {

								}

							}
							System.out.println();
							// System.out.println("Dans ecrireX : " + grilleMot);
							setGrilleMot(grilleMot);
							event.consume();
						}
					}
				}

				if (event.getCode() == KeyCode.ENTER) {
					try {
						verifierReponse();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		scene.addEventFilter(KeyEvent.KEY_PRESSED, ecrire);
	}

	/*
	 * Vérification du sens de deux manière différente
	 */

	public void verifierSens(char sens, GridPane grille, Scene scene, int length) {
		if (sens == 'V') {
			ecrireY(grille, scene, length);
			return;
		} else if (sens == 'H') {
			ecrireX(grille, scene, length);
			return;
		}
	}

	public boolean verifierSensR(char sens) {
		System.out.println("EstSemi "+ estSemi.get(indexValue));
		if (sens == 'V') {
			return true;
		} else if (sens == 'H') {
			return false;
		}
		return false;
	}

	/**
	 * Écriture de la bonne réponse dans le sens horizontal (X).
	 */
	public void ecrireReponseX() {
		iG.setMenuTexte("			Bravo! - " + grilleMot.length() + "/" + grilleMot.length() + "\n			"
				+ grilleMot.toUpperCase()
				+ "\nBonne réponse sans l'aide des lettres. \n\nCliquez sur un numéro dans la grille pour continuer.");
		for (int x = 0; x < grilleMot.length() + 1; x++) {
			int columnIndex = getPosX();
			int rowIndex = getPosY();
			int index = (rowIndex - x) * colonne + columnIndex;
			StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
			Rectangle rectangle = new Rectangle(25, 25, Color.GREEN);
			rectangle.setStroke(Color.BLACK);
			try {
				Text texte = new Text(Character.toString(grilleMot.charAt(grilleMot.length() - x - 1)).toUpperCase());
				texte.setFill(Color.BLACK);
				existingPane.getChildren().clear();
				existingPane.getChildren().addAll(rectangle, texte);
				estFini.put( indexValue, true);
			} catch (Exception e) {

			}

		}
		score = grilleMot.length() * 2 + score;
		iG.setTextS(Integer.toString(score));
	}

	/**
	 * Écriture de la réponse semi-complétée dans le sens horizontal (X).
	 */
	public void ecrireSemiReponseX() {
		grilleMot = mots.get(indexValue - 1).getReponses();
		iG.setMenuTexte("			Bravo! - " + (grilleMot.length() / 2) + "/" + grilleMot.length() + "\n			"
				+ grilleMot.toUpperCase()
				+ "\nBonne réponse sans l'aide des lettres. \n\nCliquez sur un numéro dans la grille pour continuer.");
		for (int x = 0; x < grilleMot.length(); x++) {
			int columnIndex = getPosX();
			int rowIndex = getPosY();
			int index = (rowIndex - x) * colonne + columnIndex;
			StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
			Rectangle rectangle = new Rectangle(25, 25, Color.YELLOW);
			rectangle.setStroke(Color.BLACK);
			try {
				Text texte = new Text(Character.toString(grilleMot.charAt(grilleMot.length() - x - 1)).toUpperCase());
				texte.setFill(Color.BLACK);
				existingPane.getChildren().clear();
				existingPane.getChildren().addAll(rectangle, texte);
				estFini.put( indexValue, true);
			} catch (Exception e) {

			}

		}
		score = grilleMot.length()+score;
		iG.setTextS(Integer.toString(score));
	}

	/**
	 * Écriture de la mauvaise réponse dans le sens horizontal (X).
	 */
	public void ecrireMauvaiseReponseX() {
		String Mot = mots.get(indexValue - 1).getReponses();
		if (grilleMot.length() != mots.get(indexValue - 1).getReponses().length()) {
			return;
		}
		iG.setMenuTexte("Mauvaise Réponse! - 0/8\n" + Mot + "\nVous avez écrit: " + grilleMot
				+ " \nCliquez sur un numéro dans la grille pour continuer.");
		for (int x = 0; x < Mot.length(); x++) {
			int columnIndex = getPosX();
			int rowIndex = getPosY();
			int index = (rowIndex - x) * colonne + columnIndex;
			StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
			Rectangle rectangle = new Rectangle(25, 25, Color.RED);
			Text texte = new Text(Character.toString(Mot.charAt((Mot.length() - 1) - x)).toUpperCase());
			texte.setFill(Color.BLACK);
			rectangle.setStroke(Color.BLACK);
			existingPane.getChildren().clear();
			existingPane.getChildren().addAll(rectangle, texte);
			estFini.put( indexValue, true);
		}
	}

	/**
	 * Écriture de la bonne réponse dans le sens vertical (Y).
	 */
	public void ecrireReponseY() {
		iG.setMenuTexte("			Bravo! - " + grilleMot.length() + "/" + grilleMot.length() + "\n			"
				+ grilleMot.toUpperCase()
				+ "\nBonne réponse sans l'aide des lettres. \n\nCliquez sur un numéro dans la grille pour continuer.");
		for (int x = 0; x < grilleMot.length() + 1; x++) {
			int columnIndex = getPosX();
			int rowIndex = getPosY();
			int index = (rowIndex) * colonne + columnIndex - x;

			System.out.println("PosX: " + getPosX() + " PosY: " + getPosY() + " Index: " + index);
			StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
			Rectangle rectangle = new Rectangle(25, 25, Color.GREEN);

			try {
				Text texte = new Text(Character.toString(grilleMot.charAt(grilleMot.length() - x - 1)).toUpperCase());
				texte.setFill(Color.BLACK);
				rectangle.setStroke(Color.BLACK);
				existingPane.getChildren().clear();
				existingPane.getChildren().addAll(rectangle, texte);
				estFini.put( indexValue, true);
			} catch (Exception e) {

			}

		}
		score = grilleMot.length() * 2 + score;
		iG.setTextS(Integer.toString(score));
	}

	/**
	 * Écriture de la réponse semi-complétée dans le sens vertical (Y).
	 */
	public void ecrireSemiReponseY() {
		grilleMot = mots.get(indexValue - 1).getReponses();
		iG.setMenuTexte("			Bravo! - " + (grilleMot.length() / 2) + "/" + grilleMot.length() + "\n			"
				+ grilleMot.toUpperCase()
				+ "\nBonne réponse sans l'aide des lettres. \n\nCliquez sur un numéro dans la grille pour continuer.");
		for (int x = 0; x < grilleMot.length(); x++) {
			int columnIndex = getPosX();
			int rowIndex = getPosY();
			int index = (rowIndex) * colonne + columnIndex - x;
			System.out.println("PosX: " + getPosX() + " PosY: " + getPosY() + " Index: " + index);
			StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
			Rectangle rectangle = new Rectangle(25, 25, Color.YELLOW);

			try {
				Text texte = new Text(Character.toString(grilleMot.charAt(grilleMot.length() - x - 1)).toUpperCase());
				texte.setFill(Color.BLACK);
				rectangle.setStroke(Color.BLACK);
				existingPane.getChildren().clear();
				existingPane.getChildren().addAll(rectangle, texte);
				estFini.put( indexValue, true);
			} catch (Exception e) {

			}
		}
		score = grilleMot.length() + score;
		iG.setTextS(Integer.toString(score));

	}

	/**
	 * Écriture de la mauvaise réponse dans le sens vertical (Y).
	 */
	public void ecrireMauvaiseReponseY() {
		String Mot = mots.get(indexValue - 1).getReponses();
		if (grilleMot.length() != mots.get(indexValue - 1).getReponses().length()) {
			return;
		}
		iG.setMenuTexte("Mauvaise Réponse! - 0/8\n" + Mot + "\nVous avez écrit: " + grilleMot
				+ " \nCliquez sur un numéro dans la grille pour continuer.");
		for (int x = 0; x < Mot.length(); x++) {
			int columnIndex = getPosX();
			int rowIndex = getPosY();
			int index = (rowIndex) * colonne + columnIndex - x;

			System.out.println("PosX: " + getPosX() + " PosY: " + getPosY() + " Index: " + index);
			StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
			Rectangle rectangle = new Rectangle(25, 25, Color.RED);
			Text texte = new Text(Character.toString(Mot.charAt((Mot.length() - 1) - x)).toUpperCase());
			texte.setFill(Color.BLACK);
			rectangle.setStroke(Color.BLACK);
			existingPane.getChildren().clear();
			existingPane.getChildren().addAll(rectangle, texte);
			estFini.put( indexValue, true);
		}
	}

	/*
	 * Écriture des solution pour X et Y
	 */
	public void ecrireSolution(int cellValue) {
		boolean sens = verifierSensR(mots.get(cellValue - 1).getSens());
		if (sens == true) {
			String Mot = mots.get(cellValue - 1).getReponses();
			iG.setMenuTexte("Mauvaise Réponse! - 0/8\n" + Mot + "\nVous avez demandez la solution."
					+ " \nCliquez sur un numéro dans la grille pour continuer.");
			for (int x = 0; x < Mot.length(); x++) {
				int columnIndex = getPosX();
				int rowIndex = getPosY();
				int index = (rowIndex) * colonne + columnIndex + 1 + x;
				System.out.println("PosX: " + getPosX() + " PosY: " + getPosY() + " Index: " + index);
				StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
				Rectangle rectangle = new Rectangle(25, 25, Color.RED);
				Text texte = new Text(Character.toString(Mot.charAt(x)).toUpperCase());
				texte.setFill(Color.BLACK);
				rectangle.setStroke(Color.BLACK);
				existingPane.getChildren().clear();
				existingPane.getChildren().addAll(rectangle, texte);
				estFini.put( indexValue, true);
			}
			return;
		} else if (sens == false) {
			String Mot = mots.get(indexValue - 1).getReponses();
			iG.setMenuTexte("Mauvaise Réponse! - 0/8\n" + Mot + "\nVous avez demandez la solution."
					+ " \nCliquez sur un numéro dans la grille pour continuer.");
			for (int x = 0; x < Mot.length(); x++) {
				int columnIndex = getPosX();
				int rowIndex = getPosY();
				int index = (rowIndex + x + 1) * colonne + columnIndex;
				StackPane existingPane = (StackPane) gridPane.getChildren().get(index);
				Rectangle rectangle = new Rectangle(25, 25, Color.RED);
				Text texte = new Text(Character.toString(Mot.charAt(x)).toUpperCase());
				texte.setFill(Color.BLACK);
				rectangle.setStroke(Color.BLACK);
				existingPane.getChildren().clear();
				existingPane.getChildren().addAll(rectangle, texte);
				estFini.put( indexValue, true);
			}
			return;
		}
	}

//Inscrit le Score Maximale
	public void setMaxScore() {
		for (int i = 0; i < mots.size(); i++) {
			maxScore = mots.get(i).getReponses().length() * 2 + maxScore;
		}
		iG.setTextM(Integer.toString(maxScore));
	}

	public List<Mot> getMot() {
		return mots;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setGrilleMot(String grilleMot) {
		this.grilleMot = grilleMot;
	}
}
