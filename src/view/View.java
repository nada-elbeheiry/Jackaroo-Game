package view;

import javafx.application.Application;
import javafx.scene.input.MouseEvent; 
import javafx.stage.Screen;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.event.EventHandler;
import engine.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Colour;
import model.card.Card;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.card.wild.Wild;
import model.player.Marble;
import model.player.Player;

public class View extends Application implements EventHandler <ActionEvent>{
	private Label currentplayer;
	private Label nextplayer;
	private Colour current;
	private Colour next;
		private Game game;
		private Label label;
		private TextField nameField;
		private Button okay;
		private Button cancel;
		private Button startgame;
		private Slider slider;
		private RadioButton radio1;
		private RadioButton radio2;
		private Label error1;
		private Label cpu1;
		private Label cpu2;
		private Label cpu3;
		private Label player;
		private String input;
		private AnchorPane root;
		private GridPane Track;
		private BorderPane gameroot;
		private StackPane center;
		private ArrayList<Circle> TrackIndex ;
		
		private VBox cpu1Zone;
		private VBox cpu2Zone;
		private VBox cpu3Zone;
		private VBox playerZone;
		private StackPane firepit;
		private Button play;
		private StackPane selectedCard;
		private ArrayList<Player> players;
		private int selectedCardIndex;
		private HBox cards;
		private ArrayList<Card> playerCards;
		private Circle selectedMarble;
		private int indexOfSelectedMarble;
		private Button field;
		private ArrayList<Circle> selectedMarbles;
		private VBox playerZone1;
		private TextField splitDistanceInput;
		private ArrayList <HBox> allHomeZones;
		HBox homeZoneBottom;
		private HBox homeZoneLeft ;
		HBox homeZoneTop ;
		HBox homeZoneRight ;
		int homeZoneCount;
		private ArrayList<Circle> cpu1SafeZone;
		private ArrayList<Circle> cpu2SafeZone;
		private ArrayList<Circle> cpu3SafeZone;
		private ArrayList<Circle> playerSafeZone;
		private int cpu1Base;
		private int cpu2Base;
		private int cpu3Base;
		private int playerBase;
		public ArrayList<Circle> getSelectedMarbles() {
			return selectedMarbles;
		}
		public void setSelectedMarbles(ArrayList<Circle> selectedMarbles) {
			this.selectedMarbles = selectedMarbles;
		}
		public void setField(Button field) {
			this.field = field;
		}
		public Button getField(){
			return field;
		}
		public Player activePlayer(){
			for(int i=0;i<players.size();i++){
				if(game.getActivePlayerColour()==players.get(i).getColour()){
					return players.get(i);
				}
			}
			return null;
		}
		public void fieldMarble(){
			int z=0;
			if(players.indexOf(activePlayer())!=0){
				z=25*players.indexOf(activePlayer())-1;
			}
			else{
				z=0;
			}
			TrackIndex.get(z).setFill(convertToFxColor(activePlayer().getColour()));
			//setSelectedMarble(null);
			clearHomeCell();
		    
		}
		public void clearHomeCell(){
			ArrayList<Circle> circles = new ArrayList<>();
		    HBox homeZone = getAllHomeZones().get(players.indexOf(activePlayer()));
			 for (Node node : homeZone.getChildren()) {
			        if (node instanceof Circle) {
			            Circle circle = (Circle) node;
			            circles.add(circle);
			        }
			    }
			 for(int i=0;i<circles.size();i++){
			   if( circles.get(i).getFill()!=Color.TRANSPARENT){
				   circles.get(i).setFill(Color.TRANSPARENT);
				   break;
			   }
			 }
		}
		public ArrayList<Circle> getTrackIndex() {
			return TrackIndex;
		}
		public Button getPlay() {
			return play;
		}

		public void setPlay(Button play) {
			this.play = play;
		}
		public void updateBottomToPlayerZone() {
		    gameroot.setBottom(playerZone1); 
		}
		public TextField getSplitDistanceInput() {
			return splitDistanceInput;
		}
		public void setSplitDistanceInput(TextField splitDistanceInput) {
			this.splitDistanceInput = splitDistanceInput;
		}
		public void setPlayerZone1(VBox playerZone1) {
			this.playerZone1 = playerZone1;
		}
		public  View(){
			playerBase=0;
			cpu1Base=playerBase+25-1;
			cpu2Base=cpu1Base+25-1;
			cpu3Base=cpu2Base+25-1;
			root = new AnchorPane();
			label = new Label("Player Name:");
			label.setLayoutX(700);
			label.setLayoutY(400);
			label.setPrefSize(200,50);
			root.getChildren().add(label);
			nameField =new TextField();
			nameField.setLayoutX(900);
			nameField.setLayoutY(400);
			nameField.setPrefSize(200,50);
			root.getChildren().add(nameField);
			startgame= new Button ("Start game");
			startgame.setLayoutX(800);
			startgame.setLayoutY(550);
			startgame.setPrefSize(150,30);
			startgame.setDefaultButton(true);
			root.getChildren().add(startgame);
			currentplayer= new Label();
			currentplayer.setFont(Font.font("Arial", FontWeight.BOLD, 24));
			currentplayer.setTextFill(Color.PURPLE);
			currentplayer.setPrefWidth(300);
			currentplayer.setPrefHeight(40);
			currentplayer.setAlignment(Pos.CENTER);
			currentplayer.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: purple; -fx-border-width: 2;"); 
			nextplayer = new Label();
			nextplayer.setFont(Font.font("Arial", FontWeight.BOLD, 24));
			nextplayer.setTextFill(Color.PURPLE);
			nextplayer.setPrefWidth(300);
			nextplayer.setPrefHeight(40);
			nextplayer.setAlignment(Pos.CENTER);
			nextplayer.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: purple; -fx-border-width: 2;");
			if (game != null) {
			    Colour current = game.getActivePlayerColour();
			    next = game.getNextPlayerColour();
			    currentplayer.setText("Current Player: " + current.toString());
			    nextplayer.setText("Next Player: " + next.toString());
			}
			
			allHomeZones= new ArrayList <HBox> ();

	
		}
		public ArrayList<HBox> getAllHomeZones() {
			return allHomeZones;
		}
		public void setAllHomeZones(ArrayList<HBox> allHomeZones) {
			this.allHomeZones = allHomeZones;
		}
	public Card returncard(){
		return playerCards.get(getSelectedCardIndex());
	}
	public Parent getGameRoot(){
		updateTurn();
		playerCards=game.getPlayers().get(0).getHand();;
		setGame(game);
		gameroot=new BorderPane();
		playerBase=0;
		cpu1Base=playerBase+25-1;
		cpu2Base=cpu1Base+25-1;
		cpu3Base=cpu2Base+25-1;
		cpu1SafeZone= new ArrayList <Circle> ();
		cpu2SafeZone = new ArrayList <Circle> ();
		cpu3SafeZone = new ArrayList <Circle> ();
		playerSafeZone =  new ArrayList <Circle> ();
        cpu1= new Label("CPU 1");
       // VBox leftBox=new VBox(cpu1);
        //leftBox.setPadding(new Insets(50));
	//	gameroot.setLeft(cpu1);
		//gameroot.setAlignment(cpu1, Pos.CENTER);
		cpu2= new Label("CPU 2");
		//HBox topBox=new HBox(cpu2);
		//topBox.setPadding(new Insets(50));
		//gameroot.setAlignment(cpu2, Pos.CENTER);
		//gameroot.setTop(cpu2);
		cpu3= new Label("CPU 3");
	//	VBox rightBox=new VBox(cpu3);
		//rightBox.setPadding(new Insets(50));
		//gameroot.setAlignment(cpu3, Pos.CENTER);
		//gameroot.setRight(cpu3);
		player= new Label();
		player.setText(nameField.getText());
		center= new StackPane();
		center.setPrefSize(200,200);
		firepit=new StackPane();
		firepit.setMouseTransparent(true);
		center.getChildren().addAll(setTrack(),firepit);
		gameroot.setCenter(center);
		players=game.getPlayers();
		BorderPane.setAlignment(Track, Pos.CENTER);
		homeZoneBottom = createHomeZone(players.get(0));
		homeZoneLeft = createHomeZone(players.get(1));
		homeZoneTop = createHomeZone(players.get(2));
		homeZoneRight = createHomeZone(players.get(3));
		cpu1Zone= new VBox();
		cpu2Zone= new VBox();
		cpu3Zone= new VBox();
		playerZone= new VBox();
		
		VBox left=new VBox(200);
		
        homeZoneLeft.setAlignment(Pos.CENTER);
		cpu1Zone.setAlignment(Pos.CENTER);
		cpu1Zone.getChildren().addAll(cpu1, homeZoneLeft);
		HBox cpu1Cards=cpuCards();
		cpu1Cards.setRotate(90);
		left.getChildren().addAll(cpu1Zone,cpu1Cards);
		gameroot.setLeft(left);
		
		VBox right=new VBox(200);
		
        homeZoneRight.setAlignment(Pos.CENTER);
		cpu3Zone.setAlignment(Pos.CENTER);
		cpu3Zone.getChildren().addAll(cpu3, homeZoneRight);
		HBox cpu3Cards=cpuCards();
		cpu3Cards.setRotate(90);
		right.getChildren().addAll(cpu3Zone,cpu3Cards);
		gameroot.setRight(right);
		HBox top= new HBox(200);
		
		homeZoneTop.setAlignment(Pos.CENTER);
		cpu2Zone.setAlignment(Pos.CENTER);
		cpu2Zone.getChildren().addAll(cpu2, homeZoneTop);
		top.getChildren().addAll(cpu2Zone,cpuCards());
		gameroot.setTop(top);
		BorderPane.setAlignment(top,Pos.CENTER);
		play=new Button("Play Card");
		HBox bottomBox=new HBox(200);
		homeZoneCount=0;
		
        homeZoneBottom.setAlignment(Pos.CENTER);
		playerZone.setAlignment(Pos.CENTER);
		playerZone.getChildren().addAll(player, homeZoneBottom);
		field=new Button("Field");
		startgame.setDefaultButton(false);
		field.setDefaultButton(true);
		bottomBox.getChildren().addAll(playerZone,addHand(),play,field);
		gameroot.setBottom(bottomBox);
		
		VBox labelBox = new VBox(10); 
		labelBox.setAlignment(Pos.CENTER); 
		labelBox.getChildren().addAll(currentplayer, nextplayer);
		right.getChildren().addAll(labelBox);
		return gameroot;
	}
	public void updateTurn() {
	    if (game != null) {
	        Colour current = game.getActivePlayerColour();
	        Colour next = game.getNextPlayerColour();
	        currentplayer.setText("Current Player: " + current);
	        nextplayer.setText("Next Player: " + next);
	    }
	}
	public HBox createHomeZone(Player player) {
		HBox homeZone = new HBox ();
		
		 for(int i=0;i<4;i++){
				Circle c= new Circle();
				c.setRadius(15);
				c.setStrokeWidth(2);
				Color playerColour=convertToFxColor(player.getColour());
				c.setFill(playerColour);
				c.setStroke(playerColour);
				homeZone.getChildren().add(c);       
		 }
		 allHomeZones.add(homeZone);
		 return homeZone;
	}
	public HBox getHomeZoneBottom() {
		return homeZoneBottom;
	}
	public void setHomeZoneBottom(HBox homeZoneBottom) {
		this.homeZoneBottom = homeZoneBottom;
	}
	public Colour selectRandomColour(){
		Random rand = new Random();
		Colour[] colors = {Colour.BLUE,Colour.GREEN, Colour.RED, Colour.YELLOW};
        Colour randomColor = colors[rand.nextInt(colors.length)];
        return randomColor;
	}
	
	public Color convertToFxColor(Colour colour) {
	    switch (colour) {
	        case RED: return Color.RED;
	        case GREEN: return Color.LIMEGREEN;
	        case BLUE: return Color.ROYALBLUE;
	        default: return Color.GOLD; 
	    }
	}
	public void addCPU(int m){
		if(m>=(returnbase(m) - 2+ 100) % 100){
			
		}
		TrackIndex.get(m).setFill(convertToFxColor(activePlayer().getColour()));
	}
	public void removeCPU(int m){
		TrackIndex.get(m).setFill(Color.TRANSPARENT);
	}
	public int returnbase(int x){
		if(x==0){
			return playerBase;
		}
		else if(x==1){
			return cpu1Base;
		}
		else if(x==2){
			return cpu2Base;
		}
		else{
			return cpu3Base;
		}
	}
	
	
public HBox addHand(){
		cards=new HBox(20);
		for(int i=0;i<4;i++){
			StackPane cell= new StackPane();
			Rectangle card = new Rectangle();
			card.setStrokeWidth(2);
			card.setWidth(50);
			card.setHeight(75);
			card.setStroke(Color.DARKOLIVEGREEN);
			card.setFill(Color.BEIGE);
			if(playerCards.get(i) instanceof Wild){
				Wild cardx=(Wild) playerCards.get(i);
				Label name=new Label(playerCards.get(i).getName());
				name.setStyle("-fx-font-size: 10px;");
				name.setTextFill(Color.DARKORANGE);
				cell.getChildren().addAll(card,name);
				StackPane.setAlignment(name,Pos.CENTER);
				cards.getChildren().add(cell);	
			}
			else{
				Standard cardx=(Standard) playerCards.get(i);
				Label rank=new Label();
				rank.setText(String.valueOf(cardx.getRank()));
				rank.setTextFill(Color.BLACK);
				Label suit=new Label(cardx.getSuit().toString());
				suit.setStyle("-fx-font-size: 10px;");
				cell.getChildren().addAll(card,rank,suit);
				StackPane.setAlignment(rank,Pos.TOP_LEFT);
				StackPane.setAlignment(suit,Pos.CENTER);
				cards.getChildren().add(cell);
			}
			final StackPane fcell=cell;
			final Rectangle fcard=card;
			cell.setOnMouseClicked(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent event) {
					if (getSelectedCard()!=null){
						 final StackPane s = getSelectedCard();
						final Rectangle x=(Rectangle) s.getChildren().get(0);
						 x.setStroke(Color.DARKOLIVEGREEN);
						 s.setTranslateY(0);
						}
						if(getSelectedCard()==fcell){
							setSelectedCard(null);
						}
						else {
							fcard.setStroke(Color.RED);
							fcell.setTranslateY(-10);
							setSelectedCard(fcell);
						}
					
				}
				
			});
		}
		
		return cards;
		
	}
public void setCPUcards(Card c){
	StackPane cell= new StackPane();
	Rectangle card = new Rectangle();
	card.setStrokeWidth(2);
	card.setWidth(50);
	card.setHeight(75);
	card.setStroke(Color.DARKOLIVEGREEN);
	card.setFill(Color.BEIGE);
	if(c instanceof Wild){
		Wild cardx=(Wild) c;
		Label name=new Label(cardx.getName());
		name.setStyle("-fx-font-size: 10px;");
		cell.getChildren().addAll(card,name);
		StackPane.setAlignment(name,Pos.CENTER);
	}
	else{
		Standard cardx=(Standard) c;
		Label rank=new Label();
		rank.setText(String.valueOf(cardx.getRank()));
		rank.setTextFill(Color.BLACK);
		Label suit=new Label(cardx.getSuit().toString());
		suit.setStyle("-fx-font-size: 10px;");
		cell.getChildren().addAll(card,rank,suit);
		StackPane.setAlignment(rank,Pos.TOP_LEFT);
		StackPane.setAlignment(suit,Pos.CENTER);
	}
	cardTofirepit(cell);
}
public void cardTofirepit(StackPane s){
	if(!firepit.getChildren().isEmpty()){
		firepit.getChildren().removeAll();
	}
	firepit.getChildren().add(s);
}
	public StackPane getSelectedCard() {
	return selectedCard;
	}
	public void setSelectedCard(StackPane selectedCard) {
		this.selectedCard = selectedCard;
		if(selectedCard!=null){
		setSelectedCardIndex(cards.getChildren().indexOf(selectedCard));
		}
	}

	public int getSelectedCardIndex(){
	return selectedCardIndex;
}
 	public void setSelectedCardIndex(int x){
 		selectedCardIndex =x;
 	}
    public HBox cpuCards(){
    	HBox cards=new HBox(20);
    	for(int i=0;i<4;i++){
			Rectangle card = new Rectangle();
			card.setStrokeWidth(2);
			card.setWidth(50);
			card.setHeight(75);
			card.setStroke(Color.DARKOLIVEGREEN);
			card.setFill(Color.BEIGE);
			cards.getChildren().add(card);
    	}
    	return cards;
    }
    public void cardTofirepit(){
    	if(!firepit.getChildren().isEmpty()){
    		firepit.getChildren().removeAll();
    	}
    	firepit.getChildren().add(getSelectedCard());
    }
    public GridPane setTrack(){
		Track= new GridPane();
		Track.setPrefSize(600,600);
		for (int i = 0; i < 25; i++) {
			
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(15);  
            Track.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(15);
            Track.getRowConstraints().add(rowConst);
            
        }
		//two columns of circles for right and left
		for(int i=0 ;i<25;i+=24){
			for(int j=0;j<25;j++){
				if(j==12){
					for(int k=1;k<5;k++){
					Circle c=new Circle();
					c.setRadius(15);
					
					c.setStroke(Color.DARKTURQUOISE);
					c.setStrokeWidth(2);
					c.setFill(Color.TRANSPARENT);
					if(i==0){
					Track.add(c,i+k,j);
					cpu1SafeZone.add(c);
					}
					else{
						Track.add(c,i-k,j);
						cpu3SafeZone.add(c);
					}
					final Circle fc=c;
					c.setOnMouseClicked(new EventHandler<MouseEvent>(){
						public void handle(MouseEvent x) {
							selectCell(fc);
							}	});
				}
					
				}
				Circle c=new Circle();
				c.setRadius(15);
				
				c.setStroke(Color.DARKTURQUOISE);
				c.setStrokeWidth(2);
				c.setFill(Color.TRANSPARENT);
				Track.add(c, i, j);
				final Circle fc=c;
				c.setOnMouseClicked(new EventHandler<MouseEvent>(){
					public void handle(MouseEvent x) {
						selectCell(fc);
						}	});
			}
			
		}
		
		//two rows of circles for up and down
				for(int i=0 ;i<25;i+=24){
					for(int j=0;j<25;j++){
						if(j==12){
							for(int k=1;k<5;k++){
							Circle c=new Circle();
							c.setRadius(15);
					
							c.setStroke(Color.DARKTURQUOISE);
							c.setStrokeWidth(2);
							c.setFill(Color.TRANSPARENT);
							if(i==0){
								Track.add(c,j,i+k);
								cpu3SafeZone.add(c);
								}
								else{
									Track.add(c,j,i-k);
									playerSafeZone.add(c);
								}
							final Circle fc=c;
							c.setOnMouseClicked(new EventHandler<MouseEvent>(){
								public void handle(MouseEvent x) {
									selectCell(fc);
									}	});
							}
						}
						Circle c=new Circle();
						c.setRadius(15);
						c.setStroke(Color.DARKTURQUOISE);
						c.setFill(Color.TRANSPARENT);
						c.setStrokeWidth(2);
						Track.add(c, j, i);
						final Circle fc=c;
						c.setOnMouseClicked(new EventHandler<MouseEvent>(){
							public void handle(MouseEvent x) {
								selectCell(fc);
								}	});
					}
				}
				this.TrackIndex=setTrackIndex();
				return Track;
	}
    public void selectCell(Circle fc){
		if(fc.getFill()==Color.TRANSPARENT){
			displayAlert("Selected an empty cell ","Please Select a marble");
		}
		else if(playerCards.get(selectedCardIndex) instanceof Seven){
			if(selectedMarbles.get(0)!=null&&selectedMarbles.get(1)!=null){
				selectedMarbles.get(0).setStroke(Color.DARKTURQUOISE);
				selectedMarbles.get(1).setStroke(Color.DARKTURQUOISE);
				selectedMarbles.clear();
			}
			if(!selectedMarbles.contains(fc)){
				selectedMarbles.add(fc);
				fc.setStroke(Color.MAROON);
			}
			
		}
		
		else{
		if (getSelectedMarble()!=null){
			 final Circle s = getSelectedMarble();
			 s.setStroke(Color.DARKTURQUOISE);
			 setSelectedMarble(null);
			}
			if(getSelectedMarble()==fc){
				final Circle s = getSelectedMarble();
				setSelectedMarble(null);
				s.setStroke(Color.DARKTURQUOISE);
			}
			else {
				fc.setStroke(Color.MAROON);
				setSelectedMarble(fc);
			}	
	}
	}
	public void setSelectedMarble(Circle c) {
		this.selectedMarble = c;
		if(c!=null){
		setSelectedMarbleIndex((TrackIndex).indexOf(c));
		}
	}
	public void selecedHomeMarbel(){
		
	}
	private void setSelectedMarbleIndex(int x) {
		 indexOfSelectedMarble = x;
		
	}
	public Circle getSelectedMarble(){
		return this.selectedMarble;
	}
	public int returnMarble(){
		return indexOfSelectedMarble;
	}
	public void moveMarble(int z){
		TrackIndex.get(indexOfSelectedMarble).setFill(Color.TRANSPARENT);
		selectedMarble.setStroke(Color.TURQUOISE);
		TrackIndex.get(z).setFill(convertToFxColor(players.get(0).getColour()));
	}
	public ArrayList<Integer> returnMarblesIndex(){
		ArrayList<Integer> x=new ArrayList<>();
		x.add(TrackIndex.indexOf(selectedMarbles.get(0)));
		x.add(TrackIndex.indexOf(selectedMarbles.get(1)));
		return x;
	}
	public ArrayList<Circle> setTrackIndex(){
		//column
		//row
		int i;
		int j=24;
		ArrayList<Circle> TrackIndex=new ArrayList<>();
	
			for(i=10;i>=0;i--){
				TrackIndex.add((Circle) getCircle(i,j));
			}
			i=0;
			for(j=24;j>=0;j--){
				TrackIndex.add((Circle) getCircle(i,j));
			}
			j=0;
			for(i=0;i<25;i++){
				TrackIndex.add((Circle) getCircle(i,j));
			}
			i=24;
			for(j=0;j<25;j++){
				TrackIndex.add((Circle) getCircle(i,j));
			}
			j=24;
			for(i=24;i>10;i--){
	TrackIndex.add((Circle) getCircle(i,j));
			}
			return TrackIndex;
	}
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Node getCircle(int c, int r){
		List<Node> x=Track.getChildren();
		for(int i=0;i<x.size();i++){
			if(c==GridPane.getColumnIndex(x.get(i))&&r==GridPane.getRowIndex(x.get(i))){
				return x.get(i);
			}
		}
		return null;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public TextField getNameField() {
		return nameField;
	}

	public void setNameField(TextField field) {
		this.nameField = field;
	}

	public Button getOkay() {
		return okay;
	}

	public void setOkay(Button okay) {
		this.okay = okay;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}

	public Button getStartgame() {
		return startgame;
	}

	public void setStartgame(Button startgame) {
		this.startgame = startgame;
	}

	public Slider getSlider() {
		return slider;
	}

	public void setSlider(Slider slider) {
		this.slider = slider;
	}

	public RadioButton getRadio1() {
		return radio1;
	}

	public void setRadio1(RadioButton radio1) {
		this.radio1 = radio1;
	}

	public RadioButton getRadio2() {
		return radio2;
	}

	public void setRadio2(RadioButton radio2) {
		this.radio2 = radio2;
	}

	public Label getError1() {
		return error1;
	}

	public void setError1(Label error1) {
		this.error1 = error1;
	}

	public Label getCpu1() {
		return cpu1;
	}

	public void setCpu1(Label cpu1) {
		this.cpu1 = cpu1;
	}

	public Label getCpu2() {
		return cpu2;
	}

	public void setCpu2(Label cpu2) {
		this.cpu2 = cpu2;
	}

	public Label getCpu3() {
		return cpu3;
	}

	public void setCpu3(Label cpu3) {
		this.cpu3 = cpu3;
	}

	public Label getPlayer() {
		return player;
	}

	public void setPlayer(Label player) {
		this.player = player;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public void setRoot(AnchorPane root) {
		this.root = root;
	}

	public Parent getRoot(){
		return root;
	}
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void handle(ActionEvent event){
		
		   
		}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
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
		
		
	}

