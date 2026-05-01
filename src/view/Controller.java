package view;

import java.io.IOException;
import java.util.ArrayList;

import model.Colour;
import model.card.Card;
import model.card.standard.*;
import model.card.wild.Burner;
import model.card.wild.Wild;
import model.player.Marble;
import model.player.Player;
import engine.Game;
import engine.board.BoardListener;
import exception.CannotFieldException;
import exception.GameException;
import exception.IllegalDestroyException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import exception.SplitOutOfRangeException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;





public class Controller extends Application implements BoardListener{
		private int turn;
		private View view;
		private Game game;
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		private GridPane Track;
		double width = screenBounds.getWidth();
		double height = screenBounds.getHeight();
		private Stage primaryStage;
		private ArrayList<Card> playerCards;
		public void start(Stage primaryStage) {
			this.primaryStage=primaryStage;
			view = new View();
			Scene scene = new Scene(view.getRoot(),width,height);
			primaryStage.setScene(scene);
			primaryStage.show();
			turn=1;
			view.getStartgame().setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					String input = view.getNameField().getText();
						if (input.isEmpty()) {
							displayAlert("No name entered","please enter your name");
					     } 
					    else{
					    	startGame(input);
							 
					    }
					 }});
		}
		private void startGame(String input) {
					try {
						game=new Game(input);
						game.getBoard().setBoardListener(this);
					} catch (IOException e) {
						e.printStackTrace();
					}
					view.setGame(game);
					
					BorderPane gameroot= (BorderPane) view.getGameRoot();
					Scene scene2 = new Scene(gameroot,width,height);
					primaryStage.setScene(scene2);
					primaryStage.show();
					view.getField().setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent v) {
							if(turn==1){
								view.fieldMarble();
								
							}
					//	else if(view.getSelectedCard()!=null&&(game.getPlayers().get(0).getHand().get(view.getSelectedCardIndex()) instanceof Ace||game.getPlayers().get(0).getHand().get(view.getSelectedCardIndex()) instanceof King)){
								
							//}
						}
					});
					view.getPlay().setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							Card x =view.returncard();
							view.cardTofirepit();
							if(x instanceof Seven){
								if(view.getSelectedMarbles().size()==2){
									ArrayList<Integer> f=view.returnMarblesIndex();
									Marble marble1=game.getBoard().getTrack().get((int)f.get(0)).getMarble();
									Marble marble2=game.getBoard().getTrack().get((int)f.get(1)).getMarble();
									try {
										game.getPlayers().get(0).selectMarble(marble1);
									} catch (InvalidMarbleException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									try {
										game.getPlayers().get(0).selectMarble(marble2);
									} catch (InvalidMarbleException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									try {
										game.playPlayerTurn();
									} catch (GameException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								
								}
							}
							else if(x instanceof Five){
								Colour selectedMarbleColour= view.selectRandomColour();
								
								if(view.getSelectedMarbles().size()==1){
									ArrayList<Integer> f=view.returnMarblesIndex();
									Marble marble=game.getBoard().getTrack().get((int)f.get(0)).getMarble();
									try {
										game.getPlayers().get(0).selectMarble(marble);
									} catch (InvalidMarbleException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
					
									for(int i=0 ; i< 1 ; i++){
										int z=game.getBoard().getPositionInPathhelp(game.getBoard().getTrack(),game.getBoard().getTrack().get((int)f.get(i)).getMarble());
										if(game.getBoard().getTrack().get(z).isTrap() || game.getBoard().getTrack().get(z).getMarble()!=null){
											Marble marbleDestroyed= game.getBoard().getTrack().get(z).getMarble();
											Color marbleColour=view.convertToFxColor(marbleDestroyed.getColour());
											Circle c= view.getTrackIndex().get(z);
											c.setFill(Color.TRANSPARENT);
											for(int j=0; j<view.getAllHomeZones().size();j++){
												ArrayList<Circle> circles = new ArrayList<>();
												    HBox homeZone = view.getAllHomeZones().get(j);
												    for (Node node : homeZone.getChildren()) {
												        if (node instanceof Circle) {
												            Circle circle = (Circle) node;
												            if(circle.getFill()==Color.TRANSPARENT){
												            	circle.setFill(marbleColour);
												            	
												            	}
												        }
												    }
										}
											
										}
									}
								}
							}
							else if(x instanceof Wild){
								if(x instanceof Burner){
									Paint wantedMarble;
									Colour currentMarble = game.getActivePlayerColour();
									if(!view.getSelectedMarble().getFill().equals(currentMarble)){
										wantedMarble= view.getSelectedMarble().getFill();
									}
											for(int i=0 ; i< 1 ; i++){
											ArrayList<Integer> f=view.returnMarblesIndex();
											int z=game.getBoard().getPositionInPathhelp(game.getBoard().getTrack(),game.getBoard().getTrack().get((int)f.get(i)).getMarble());
											if(game.getBoard().getTrack().get(z).isTrap() || game.getBoard().getTrack().get(z).getMarble()!=null){
												Marble marbleDestroyed= game.getBoard().getTrack().get(z).getMarble();
												Color marbleColour=view.convertToFxColor(marbleDestroyed.getColour());
												Circle c= view.getTrackIndex().get(z);
												c.setFill(Color.TRANSPARENT);
												for(int j=0; j<view.getAllHomeZones().size();j++){
													ArrayList<Circle> circles = new ArrayList<>();
													    HBox homeZone = view.getAllHomeZones().get(j);
													    for (Node node : homeZone.getChildren()) {
													        if (node instanceof Circle) {
													            Circle circle = (Circle) node;
													            if(circle.getFill()==Color.TRANSPARENT){
													            	circle.setFill(marbleColour);
													            	
													            	}
													        }
													    }
											}
												
											}
										}
									}
						
		
								else{
									Paint wantedMarble;
									Colour currentMarble = game.getActivePlayerColour();
									if(!view.getSelectedMarble().getFill().equals(currentMarble)){
										wantedMarble= view.getSelectedMarble().getFill();
									}
									if(view.getSelectedMarbles().size()==1){
										ArrayList<Integer> f=view.returnMarblesIndex();
										Marble marble=game.getBoard().getTrack().get((int)f.get(0)).getMarble();
										try {
											game.getPlayers().get(0).selectMarble(marble);
										} catch (InvalidMarbleException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
						
										for(int i=0 ; i< 1 ; i++){
											int z=game.getBoard().getPositionInPathhelp(game.getBoard().getTrack(),game.getBoard().getTrack().get((int)f.get(i)).getMarble());
											if(game.getBoard().getTrack().get(z).isTrap() || game.getBoard().getTrack().get(z).getMarble()!=null){
												Marble marbleDestroyed= game.getBoard().getTrack().get(z).getMarble();
												Color marbleColour=view.convertToFxColor(marbleDestroyed.getColour());
												Circle c= view.getTrackIndex().get(z);
												c.setFill(Color.TRANSPARENT);
												for(int j=0; j<view.getAllHomeZones().size();j++){
													ArrayList<Circle> circles = new ArrayList<>();
													    HBox homeZone = view.getAllHomeZones().get(j);
													    for (Node node : homeZone.getChildren()) {
													        if (node instanceof Circle) {
													            Circle circle = (Circle) node;
													            if(circle.getFill()==Color.TRANSPARENT){
													            	circle.setFill(marbleColour);
													            	
													            	}
													        }
													    }
											}
												
											}
										}
									}
								}
							}
						else{
								playCard();
								view.setSelectedMarble(null);
								view.setSelectedCard(null);
						}
						game.endPlayerTurn();
						view.updateTurn(); 
						for(int i=1;i<game.getPlayers().size();i++){
							if(turn==1){
								try {
								game.fieldMarble();
								} catch (CannotFieldException e) {
									e.printStackTrace();
								} catch (IllegalDestroyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								view.fieldMarble();
							}
								try {
									game.playPlayerTurn();
								} catch (GameException e) {
									e.printStackTrace();
								}
								view.setCPUcards(game.getPlayers().get(i).getSelectedCard());
								
								makeCPUMovewithDelay();
							
							game.endPlayerTurn();
							view.updateTurn(); 
						}
						turn++;
						}});
					//set selected card to null after you end turn;
					}
		
		
		public void makeCPUMovewithDelay(){
			javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(10));
			pause.setOnFinished(e -> {
			});
			pause.play();
		
	}
		
		
		private void playCard() {
			int x=0;
			try {
				game.fieldMarble();
			} catch (CannotFieldException e3) {
				e3.printStackTrace();
			} catch (IllegalDestroyException e3) {
				
				e3.printStackTrace();
			}
			Marble marble=null;
			
			
				try {
					
					 marble=game.getBoard().getTrack().get(view.returnMarble()).getMarble();
					game.getPlayers().get(0).selectMarble(marble);
					game.getPlayers().get(0).selectCard(view.returncard());	
					 x= game.getBoard().getPositionInPathhelp(game.getBoard().getTrack(),marble);
					game.playPlayerTurn();
				} 
					catch (InvalidCardException e4) {
					displayAlert(e4.getClass().toString(), e4.getMessage());
				}
				catch(InvalidMarbleException e3){
					displayAlert(e3.getClass().toString(), e3.getMessage());
				} catch (GameException e) {
					e.printStackTrace();
				}
				
			
			
			int z=game.getBoard().getPositionInPathhelp(game.getBoard().getTrack(),marble);
			Marble marbleDestroyed=game.getBoard().getTrack().get(z).getMarble();
			Color marbleColour=view.convertToFxColor(marbleDestroyed.getColour());
			if(game.getBoard().getTrack().get(z).isTrap() || (marbleDestroyed!=null && marbleDestroyed.getColour()!=marble.getColour())){
				Circle c= view.getTrackIndex().get(z);
				c.setFill(Color.TRANSPARENT);
					    HBox homeZone = ((View) view).getHomeZoneBottom();
					    for (Node node : homeZone.getChildren()) {
					       if (node instanceof Circle) {
					            Circle circle = (Circle) node;
					            if(circle.getFill()==Color.TRANSPARENT){
					            	circle.setFill(marbleColour);
					            	
					            }
					        }
					    }
			}
		
			if(view.returncard().getName().equals("King")){
				for(int i=x;i<z;i++){
					if(game.getBoard().getTrack().get(i).getMarble()!=null && game.getBoard().getTrack().get(i).getMarble().getColour()!=marble.getColour()){
						Circle c= view.getTrackIndex().get(z);
						c.setFill(Color.TRANSPARENT);}}
					
						for(int j=0; j<view.getAllHomeZones().size();j++){
							    HBox homeZone = view.getHomeZoneBottom();
							    for (Node node : homeZone.getChildren()) {
							        if (node instanceof Circle) {
							            Circle circle = (Circle) node;
							            if(circle.getFill()==Color.TRANSPARENT){
							            	circle.setFill(marbleColour);
							            }
							        }
							    }
					}}
			
			view.moveMarble(z);
			}
		public void splitDistanceAction(Player player){
			Card c= view.returncard();
			String cardName=c.getName();
			if (cardName.equals("Seven")){
				view.cardTofirepit();
				view.updateBottomToPlayerZone();
				
				 if (!(view.getSplitDistanceInput().getText()).isEmpty()) {
			            try {
			                int distance = Integer.parseInt(view.getSplitDistanceInput().getText());
			                view.setSplitDistanceInput(view.getSplitDistanceInput());
			                game.editSplitDistance(distance);  
			                playCard();

			            } catch (SplitOutOfRangeException e) {
			                displayAlert("Out of Range", "Split distance must be between 1 and 6.");
			            }
				 }
				else{
					playCard();
				}
				}
				
			}
		 private void displayAlert(String title, String message) {
		        Stage alertStage = new Stage();
		        alertStage.setTitle(title);

		        Label label = new Label(message);
		        Button closeButton = new Button("okay");
		        closeButton.setOnAction(event -> alertStage.close());

		        BorderPane pane = new BorderPane();
		        pane.setTop(label);
		        pane.setCenter(closeButton);

		        Scene scene = new Scene(pane, 500, 100);
		        alertStage.setScene(scene);
		        alertStage.show();
		    }
		 public void changeTurn(Rectangle playedCard) {
			   view.updateTurn(); 
			}
		 public static void main(String[]args){
			 launch(args);
		 }
		@Override
		public void onMove(int x,int z) {
			view.removeCPU(x);
			view.addCPU(z);
			
		}
}