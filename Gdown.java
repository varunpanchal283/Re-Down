import javafx.application.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import java.util.*;
import java.io.*;
import javafx.scene.control.cell.PropertyValueFactory;
public class Gdown extends Application{

	public static void main(String args[]){
		launch(args);
	}

	String title,size,type;
	TableView<Content> table;
	static String path = System.getProperty("user.dir");
	static String idpath=path+"\\id.txt";
	static String pypath = path+"\\Python38-32\\python.exe";
	static String foldpypath=path+"\\folder.py";
	static String filepypath=path+"\\file.py";
	static String execfoldpy = pypath+" "+foldpypath;
	static String execfilepy=pypath+" "+filepypath;

	GridPane layout;
	Button getdatabtn,adddatabtn,deldatabtn;
	Stage window;
	TextField foldlink,filelink;

	Label folder,file;
	RadioButton foldradio,fileradio;
	ToggleGroup tg;
	boolean fileinfo;
	
	HashMap<String, String> idtitle;
	HashMap<String, String> titleid;
	HashMap<String,String> idsize;
	HashMap<String, String> parentid;
	HashMap<String, TreeItem<String>> folderid;
	HashMap<String, String> fileid;
	HashMap<String,String> idtype;
	HashMap<String,ArrayList<String>> folderparent;
	TreeItem<String> root = new TreeItem<>("Shared Drive");
	TreeView<String> drive;
	boolean x=false;
	String tme;

	@Override public void start(Stage primarywindow){
		window=primarywindow;

		//Labels of folder and file
		folder = new Label("Folder Link");
		file=new Label("File Link");

		//Text field
		foldlink=new TextField();
		foldlink.setPromptText("Folder Link");
		filelink=new TextField();
		filelink.setPromptText("File Link");
		filelink.setDisable(true);

		//Hbox for folder
		HBox foldbox=new HBox(5);
		foldbox.setMaxWidth(450);
		foldbox.setPadding(new Insets(0,0,0,5));
		GridPane foldboxgrid = new GridPane();
		ColumnConstraints foldlabelcol=new ColumnConstraints(75);
		ColumnConstraints foldtextcol=new ColumnConstraints(375);
		RowConstraints rowfold=new RowConstraints(100);
		foldboxgrid.getColumnConstraints().addAll(foldlabelcol,foldtextcol);
		foldboxgrid.getRowConstraints().addAll(rowfold);
		foldboxgrid.setConstraints(folder,0,0);
		foldboxgrid.setConstraints(foldlink,1,0);
		foldboxgrid.getChildren().addAll(folder,foldlink);
		foldbox.getChildren().addAll(foldboxgrid);

		//Hbox for file
		HBox filebox=new HBox(10);
		filebox.setMaxWidth(450);
		filebox.setPadding(new Insets(0,0,5,5));
		GridPane fileboxgrid = new GridPane();
		ColumnConstraints filelabelcol=new ColumnConstraints(75);
		ColumnConstraints filetextcol=new ColumnConstraints(375);
		RowConstraints rowfile=new RowConstraints(100);
		fileboxgrid.getColumnConstraints().addAll(filelabelcol,filetextcol);
		fileboxgrid.getRowConstraints().addAll(rowfile);
		fileboxgrid.setConstraints(file,0,0);
		fileboxgrid.setConstraints(filelink,1,0);
		fileboxgrid.getChildren().addAll(file,filelink);
		filebox.getChildren().addAll(fileboxgrid);

		//radio buttons
		fileradio=new RadioButton("File");
		foldradio=new RadioButton("Folder");
		tg=new ToggleGroup();
		fileradio.setToggleGroup(tg);
		foldradio.setToggleGroup(tg);
		foldradio.setSelected(true);
		tg.selectedToggleProperty().addListener((v,oldi,newi)->radiofilefold());

		//Hbox for radio
		HBox foldradiobox = new HBox();
		foldradiobox.getChildren().add(foldradio);
		foldradiobox.setMaxWidth(450);
		foldradiobox.setMargin(foldradio, new Insets(2, 0, 0, 85));
		HBox fileradiobox = new HBox();
		fileradiobox.getChildren().add(fileradio);
		fileradiobox.setMaxWidth(450);
		fileradiobox.setMargin(fileradio, new Insets(2, 0, 0, 85));
		
		//Getdata Button
		HBox btn=new HBox();
		btn.setAlignment(Pos.CENTER);
		getdatabtn=new Button("GETD");
		getdatabtn.setOnAction(e-> getdata());
		btn.getChildren().add(getdatabtn);
		

		//add data item
		VBox bt=new VBox(20);
		bt.setAlignment(Pos.CENTER);
		adddatabtn=new Button("ADDD");
		adddatabtn.setOnAction(e->addsomething());
		bt.getChildren().add(adddatabtn);

		deldatabtn=new Button("DELD");
		deldatabtn.setOnAction(e->delsomething());
		bt.getChildren().add(deldatabtn);

		//Tree
		drive = new TreeView<>(root);
  		drive.setShowRoot(false);
  		drive.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->adddata(newvalue));

  		//table
  		TableColumn<Content,String> titlecolumn=new TableColumn<>("Title");
		titlecolumn.setMinWidth(250);
		titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Content,Double> typecolumn=new TableColumn<>("Type");
		typecolumn.setMinWidth(130);
		typecolumn.setCellValueFactory(new PropertyValueFactory<>("type"));

		TableColumn<Content,String> sizecolumn=new TableColumn<>("Size");
		sizecolumn.setMinWidth(80);
		sizecolumn.setCellValueFactory(new PropertyValueFactory<>("size"));

		table = new TableView<>();
		table.getColumns().addAll(titlecolumn,typecolumn,sizecolumn);


		//Whole layout
		layout = new GridPane();
		ColumnConstraints foldcol=new ColumnConstraints(460);
		ColumnConstraints getdata = new ColumnConstraints(100);
		ColumnConstraints filecol=new ColumnConstraints(460);
		RowConstraints linkrow=new RowConstraints(50);
		RowConstraints cbrow=new RowConstraints(25);
		RowConstraints treeview=new RowConstraints(400);
		layout.getColumnConstraints().addAll(foldcol,getdata,filecol);
		layout.getRowConstraints().addAll(linkrow,cbrow,treeview);
		layout.setConstraints(foldbox,0,0);
		layout.setConstraints(filebox,2,0);
		layout.setConstraints(btn,1,0);
		layout.setConstraints(foldradiobox,0,1);
		layout.setConstraints(fileradiobox,2,1);
		layout.setConstraints(drive,0,2);
		layout.setConstraints(bt,1,2);
		layout.setConstraints(table,2,2);
		layout.getChildren().addAll(bt,table,filebox,drive,foldbox,btn,foldradiobox,fileradiobox);	
		layout.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");

		Scene sc = new Scene(layout,1020,475);

		window.setScene(sc);
		window.show();


	}
//radio method
public void radiofilefold(){
	RadioButton s =(RadioButton)tg.getSelectedToggle();
	String st=s.getText();
	if(st=="File"){
		foldlink.setDisable(true);
		filelink.setDisable(false);
		adddatabtn.setDisable(true);
		fileinfo=true;
	}
	else{
		filelink.setDisable(true);
		foldlink.setDisable(false);
		adddatabtn.setDisable(false);
		fileinfo=false;
	}
}

//gets data from drive
public void getdata(){
	try{
		String sharedid;
		if(fileinfo)
		{
			sharedid=filelink.getText();
			File myObj = new File(idpath);
			FileWriter myWriter = new FileWriter(idpath);
			myWriter.write(sharedid);
	      	myWriter.close();	
	      	getfiledata();
		}
		else
		{
			sharedid=foldlink.getText();
			File myObj = new File(idpath);
			FileWriter myWriter = new FileWriter(idpath);
			myWriter.write(sharedid);
      		myWriter.close();	
      		getfolddata();
		}
    }
    catch(Exception e){
    }
}
	
	//this method has the selected data from tree view and stores it in title size and type
	public void adddata(TreeItem<String> newvalue){
		Content c= new Content();
		String val = newvalue.getValue();
		title=(newvalue.getValue());
		if(idtype.get(titleid.get(val)).contains("folder"))
			type="Folder";
		else
			type=idtype.get(titleid.get(val));
		size = idsize.get(titleid.get(val));
	}
	
	//adds data to table
	public void addsomething(){
		Content content=new Content();
		if(title!=null){
		content.setTitle(title);
		content.setType(type);
		content.setSize(size);
		table.getItems().add(content);
		}
		title=null;
		type=null;
		size=null;
	}

	//deletes data from table
	public void delsomething(){
		ObservableList<Content> contentSelected, allContents;
		allContents=table.getItems();
		contentSelected=table.getSelectionModel().getSelectedItems();
		contentSelected.forEach(allContents::remove);
	}

	public void getfolddata(){
	ArrayList<String> list = new ArrayList<>();
	ArrayList<String> temp=new ArrayList<>();
	idtitle = new HashMap<String, String>();
	folderparent=new HashMap<>();
	titleid=new HashMap<>();
	idtype = new HashMap<>();
	parentid = new HashMap<String, String>();
	folderid=new HashMap<String, TreeItem<String>>();
	fileid=new HashMap<String, String>();
	idsize=new HashMap<>();
		
	try{
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",execfoldpy);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
           line = r.readLine();
           list.add(line);
           if (line == null) { break; }
       	}
       p.waitFor();
	}
	catch(Exception e){
    	System.out.println(e);
    }
    
    int k =0;
    for(int i=0;i<list.size();i++)
    {
    	temp.add(list.get(i));
    	if(((i+1)%5)==0){
    		String id=temp.get(k+0);
    		String type=temp.get(k+1);
    		String parent = temp.get(k+2);
    		String title = temp.get(k+3);
    		String size = temp.get(k+4);
    		idsize.put(id,size);
    		idtitle.put(id,title);
    		parentid.put(id,parent);
    		idtype.put(id,type);
    		titleid.put(title,id);
    
    		if(type.contains("folder")){
    			folderid.put(idtitle.get(id),new TreeItem<>(idtitle.get(id)));
    		}
    		else{
    			fileid.put(id,idtitle.get(parent));
    		}
    
    		k=k+5;
		}
	}

	//adding leaf nodes of files to tree
	for(String j: fileid.keySet()){
    	TreeItem<String> item=new TreeItem<>(idtitle.get(j));
    	item.setExpanded(true);
    	folderid.get(fileid.get(j)).getChildren().add(item);
    }
    
    //contructing tree by subfolders
    for(String j: parentid.keySet()){
    	if(idtype.get(j).contains("folder")){
    		
    		if(idtitle.get(parentid.get(j))==null){
    			ArrayList<String> b = new ArrayList<>();
    			b.add(idtitle.get(j));
    			folderparent.put("main",b);
    			b=null;
    			root.getChildren().add(folderid.get(idtitle.get(j)));
    		}
    		
    		else{
    			if(folderparent.containsKey(idtitle.get(parentid.get(j)))){
    				ArrayList<String> b;
    				b=folderparent.get(idtitle.get(parentid.get(j)));
    				b.add(idtitle.get(j));
    				folderparent.put(idtitle.get(parentid.get(j)),b);
    				folderid.get(idtitle.get(parentid.get(j))).getChildren().add(folderid.get(idtitle.get(j)));
    			}
    			else{
    				ArrayList<String> b = new ArrayList<>();
    				b.add(idtitle.get(j));
    				folderparent.put(idtitle.get(parentid.get(j)),b);
    				folderid.get(idtitle.get(parentid.get(j))).getChildren().add(folderid.get(idtitle.get(j)));
    			}
    		}
    	}
	}

	drive.setRoot(root);
	}

	public void getfiledata(){
		ArrayList<String> list = new ArrayList<>();
		try{
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",execfilepy);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
           line = r.readLine();
           list.add(line);
           if (line == null) { break; }
       	}
       	p.waitFor();
		}
		catch(Exception e){
    		System.out.println(e);
    	}
    	title=list.get(0);
    	size=list.get(1);
    	type=list.get(2);
    	addsomething();
	}


}