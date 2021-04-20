/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gdown;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;

import java.io.*;
import java.nio.file.Paths;

import javafx.scene.control.cell.PropertyValueFactory;

public class Gdown extends Application {

    public static void main(String args[]) {
        launch(args);
    }
    StackPane finallayout;
    String title, size, type, id, parent;
    TableView<Content> table;
    int z = 1;
    int k = 0;
    ArrayList<String> list, temp;
    String path = System.getProperty("user.dir");
    String downdir = path + "\\Download\\";
    String idpath = path + "\\id.txt";
    String downpy = path + "\\download.py";
    String pypath = path + "\\Python38-32\\python.exe";
    String foldpypath = path + "\\folder.py";
    String filepypath = path + "\\file.py";
    String datapath = path + "\\data.txt";
    String execfoldpy = pypath + " " + foldpypath;
    String execfilepy = pypath + " " + filepypath;
    String downpath = pypath + " " + downpy;
    String connpypath = path + "\\connect.py";
    String connpath = pypath + " " + connpypath;
    HashMap<String, String> finalpath = new HashMap<>();
    GridPane layout;
    Button getdatabtn, adddatabtn, deldatabtn, downbtn, choosedir, connect, closebtn;
    Stage window;
    TextField foldlink, filelink;
    Label folder, file, dirlabel;
    RadioButton foldradio, fileradio;
    ToggleGroup tg;
    boolean fileinfo;
    HashMap<String, Integer> idsize = new HashMap<>();
    HashSet<TreeItem<String>> tempo = new HashSet<>();
    HashMap<String, String> filepath = new HashMap<>();
    HashMap<String, String> foldpath = new HashMap<>();
    HashMap<String, String> idtitle = new HashMap<>();
    HashMap<String, TreeItem<String>> filetree = new HashMap<>();
    HashMap<String, String> titleid = new HashMap<>();
    HashMap<String, String> idsize1 = new HashMap<>();
    HashMap<String, String> parentid = new HashMap<>();
    HashMap<String, TreeItem<String>> folderid = new HashMap<>();
    HashMap<String, String> fileid = new HashMap<>();
    HashMap<String, String> idtype = new HashMap<>();
    HashMap<String, String> filetitleid = new HashMap<>();
    HashMap<String, ArrayList<String>> folderparent = new HashMap<>();
    TreeItem<String> root = new TreeItem<>("Shared D");
    TreeItem<String> root2;
    TreeView<String> drive;
    ProgressBar pb;
    boolean x = false;
    Scene sc;
    String tme;
    TextArea textArea = new TextArea();
    ImageView imageView;

    @Override
    public void start(Stage primarywindow) {

        window = primarywindow;
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);
        //Labels of folder and file
        folder = new Label("Folder Link");
        file = new Label("File Link");
        //Text field
        foldlink = new TextField();
        foldlink.setPromptText("Folder Link");
        filelink = new TextField();
        filelink.setPromptText("File Link");
        filelink.setDisable(true);

        //Hbox for folder
        HBox foldbox = new HBox(5);
        foldbox.setMaxWidth(450);
        foldbox.setPadding(new Insets(0, 0, 0, 25));
        GridPane foldboxgrid = new GridPane();
        ColumnConstraints foldlabelcol = new ColumnConstraints(100);
        ColumnConstraints foldtextcol = new ColumnConstraints(350);
        RowConstraints rowfold = new RowConstraints(100);
        foldboxgrid.getColumnConstraints().addAll(foldlabelcol, foldtextcol);
        foldboxgrid.getRowConstraints().addAll(rowfold);
        foldboxgrid.setConstraints(folder, 0, 0);
        foldboxgrid.setConstraints(foldlink, 1, 0);
        foldboxgrid.getChildren().addAll(folder, foldlink);
        foldbox.getChildren().addAll(foldboxgrid);

        //Hbox for file
        HBox filebox = new HBox(10);
        filebox.setMaxWidth(450);
        filebox.setPadding(new Insets(0, 0, 0, 12));
        GridPane fileboxgrid = new GridPane();
        ColumnConstraints filelabelcol = new ColumnConstraints(75);
        ColumnConstraints filetextcol = new ColumnConstraints(375);
        RowConstraints rowfile = new RowConstraints(100);
        fileboxgrid.getColumnConstraints().addAll(filelabelcol, filetextcol);
        fileboxgrid.getRowConstraints().addAll(rowfile);
        fileboxgrid.setConstraints(file, 0, 0);
        fileboxgrid.setConstraints(filelink, 1, 0);
        fileboxgrid.getChildren().addAll(file, filelink);
        filebox.getChildren().addAll(fileboxgrid);

        //radio buttons
        fileradio = new RadioButton("File");
        foldradio = new RadioButton("Folder");
        tg = new ToggleGroup();
        fileradio.setToggleGroup(tg);
        foldradio.setToggleGroup(tg);
        foldradio.setSelected(true);
        tg.selectedToggleProperty().addListener((v, oldi, newi) -> radiofilefold());

        //Hbox for radio
        HBox foldradiobox = new HBox();
        foldradiobox.getChildren().add(foldradio);
        foldradiobox.setMaxWidth(450);
        foldradiobox.setAlignment(Pos.CENTER);
        HBox fileradiobox = new HBox();
        fileradiobox.getChildren().add(fileradio);
        fileradiobox.setMaxWidth(450);
        fileradiobox.setAlignment(Pos.CENTER);

        //Getdata Button-
        VBox btn = new VBox();
        btn.setPrefWidth(80);
        btn.setAlignment(Pos.CENTER);
        getdatabtn = new Button("Get Data");
        getdatabtn.setMaxWidth(btn.getPrefWidth());
        getdatabtn.setOnAction(e -> getdata());
        btn.getChildren().add(getdatabtn);

        //add data item
        VBox bt = new VBox(20);
        bt.setPrefWidth(80);
        bt.setPadding(new Insets(0, 0, 25, 0));
        bt.setAlignment(Pos.BOTTOM_CENTER);
        adddatabtn = new Button("Add Data");
        adddatabtn.setMaxWidth(bt.getPrefWidth());
        adddatabtn.setOnAction(e -> addsomething());
        bt.getChildren().add(adddatabtn);

        deldatabtn = new Button("Del Data");
        deldatabtn.setMaxWidth(bt.getPrefWidth());
        deldatabtn.setOnAction(e -> delsomething());
        bt.getChildren().add(deldatabtn);

        //Tree
        drive = new TreeView<>(root);
        drive.setShowRoot(false);
        drive.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> adddata(newvalue));

        //Tree layout
        VBox treelay = new VBox();
        treelay.getChildren().add(drive);
        treelay.setPadding(new Insets(10, 10, 10, 20));

        //table
        TableColumn<Content, String> titlecolumn = new TableColumn<>("Title");
        titlecolumn.setMinWidth(250);
        titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Content, Double> typecolumn = new TableColumn<>("Type");
        typecolumn.setMinWidth(130);
        typecolumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Content, String> sizecolumn = new TableColumn<>("Size");
        sizecolumn.setMinWidth(60);
        sizecolumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        table = new TableView<>();
        table.getColumns().addAll(titlecolumn, typecolumn, sizecolumn);

        //TABLE LAYOUT
        HBox tlayout = new HBox();
        tlayout.getChildren().add(table);
        tlayout.setAlignment(Pos.CENTER);
        tlayout.setPadding(new Insets(10, 15, 10, 0));

        downbtn = new Button("Download");
        downbtn.setOnAction(e -> datadown());
        downbtn.setMaxWidth(bt.getPrefWidth());
        bt.getChildren().add(downbtn);

        DirectoryChooser dir_chooser = new DirectoryChooser();
        dir_chooser.setTitle("Select Dowload Directory");
        dir_chooser.setInitialDirectory(new File(downdir));

        //Label
        HBox cdir = new HBox(10);
        cdir.setPadding(new Insets(5, 0, 5, 20));
        choosedir = new Button("Choose Directory");
        choosedir.setOnAction(e -> {
            File file = dir_chooser.showDialog(window);
            if (file != null) {
                downdir = file.getAbsolutePath()+"\\";
                dirlabel.setText(downdir);
            }
        });
        dirlabel = new Label(downdir);
        dirlabel.setId("dir");
        dirlabel.setPadding(new Insets(10, 0, 0, 0));
        cdir.getChildren().addAll(choosedir, dirlabel);

        //Connect
        //connlable=new Label("hello");
        VBox conv = new VBox(5);
        conv.setPrefWidth(80);
        conv.setAlignment(Pos.CENTER);
        connect = new Button("Connect");
        connect.setOnAction(e -> conn());
        connect.setMaxWidth(conv.getPrefWidth());
        connect.setMaxHeight(20);
        conv.getChildren().addAll(connect);

        VBox pbdr = new VBox();
        pbdr.setAlignment(Pos.CENTER);
        pbdr.setPadding(new Insets(5, 0, 5, 0));
        pb = new ProgressBar();
        pb.setMaxWidth(Double.MAX_VALUE);
        pbdr.getChildren().addAll(pb);

        GridPane hehe = new GridPane();
        ColumnConstraints dir = new ColumnConstraints(530);
        ColumnConstraints pro = new ColumnConstraints(530);
        RowConstraints r = new RowConstraints(40);
        hehe.getColumnConstraints().addAll(dir, pro);
        hehe.getRowConstraints().addAll(r);
        hehe.setConstraints(cdir, 0, 0);
        hehe.setConstraints(pbdr, 1, 0);
        hehe.getChildren().addAll(cdir, pbdr);
        //Label
        VBox vbox = new VBox(textArea);
        textArea.setEditable(false);
        textArea.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00; -fx-opacity: 0.7; ");
        vbox.setPadding(new Insets(5, 20, 5, 20));

        connect.setId("conn");
        getdatabtn.setId("getd");
        choosedir.setId("choosedir");

        HBox close = new HBox();
        closebtn = new Button("X");
        close.setAlignment(Pos.BASELINE_RIGHT);
        close.getChildren().add(closebtn);
        closebtn.setId("close");
        closebtn.setOnAction(e -> {
            window.close();
        });

        //Whole layout
        finallayout = new StackPane();
        layout = new GridPane();
        ColumnConstraints foldcol = new ColumnConstraints(490);
        ColumnConstraints getdata = new ColumnConstraints(100);
        ColumnConstraints filecol = new ColumnConstraints(490);
        RowConstraints win = new RowConstraints(20);
        RowConstraints linkrow = new RowConstraints(50);
        RowConstraints cbrow = new RowConstraints(25);
        RowConstraints treeview = new RowConstraints(400);
        RowConstraints outlabel = new RowConstraints(120);
        RowConstraints outpb = new RowConstraints(40);
        RowConstraints nthng = new RowConstraints(10);
        layout.getColumnConstraints().addAll(foldcol, getdata, filecol);
        layout.getRowConstraints().addAll(win, linkrow, cbrow, treeview, outlabel, outpb, nthng);
        layout.setConstraints(foldbox, 0, 1);
        layout.setConstraints(conv, 1, 1);
        layout.setConstraints(filebox, 2, 1);
        layout.setConstraints(close, 0, 0, 3, 1);
        layout.setConstraints(btn, 1, 2);
        layout.setConstraints(foldradiobox, 0, 2);
        layout.setConstraints(fileradiobox, 2, 2);
        layout.setConstraints(treelay, 0, 3);
        layout.setConstraints(bt, 1, 3);
        layout.setConstraints(tlayout, 2, 3);
        layout.setConstraints(vbox, 0, 4, 3, 1);
        layout.setConstraints(hehe, 0, 5, 3, 1);
        layout.getChildren().addAll(close, hehe, bt, vbox, tlayout, filebox, treelay, foldbox, btn, foldradiobox, fileradiobox, conv);
        imageView = new ImageView();
        imageView.setId("im");
        finallayout.getChildren().addAll(layout);
        
        try{
            File f=new File(path+"\\mycreds.txt");
            if(f.exists())
                connect.setDisable(true);
        }
        catch(Exception e){}
        //layout.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
        sc = new Scene(finallayout, 1080, 665);
        sc.getStylesheets().add(Gdown.class.getResource("gdown.css").toExternalForm());
        window.setScene(sc);
        window.show();

    }

    public void conn() {
        Runnable taskcon = new Runnable() {
            public void run() {
                runTaskcon();
            }
        };
        Thread backgroundThread = new Thread(taskcon);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    public void runTaskcon() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sc.setCursor(Cursor.WAIT);
            }
        });

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", connpath);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true) {
                final String line = r.readLine();
                if (line == null) {
                    break;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (line.equals("connecting..") || line.equals("connected")) {
                            connect.setText(line);
                        }
                    }
                });

            }
            p.waitFor();
        } catch (Exception e) {
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (connect.getText().equals("connected")) {
                    connect.setDisable(true);
                }
                sc.setCursor(Cursor.DEFAULT);
            }
        });
    }
//radio method

    public void radiofilefold() {
        RadioButton s = (RadioButton) tg.getSelectedToggle();
        String st = s.getText();
        if (st == "File") {
            foldlink.setDisable(true);
            filelink.setDisable(false);
            adddatabtn.setDisable(true);
            fileinfo = true;
        } else {
            filelink.setDisable(true);
            foldlink.setDisable(false);
            adddatabtn.setDisable(false);
            fileinfo = false;
        }
    }

//gets data from drive
    public void getdata() {
        try {
            String sharedid;
            if (fileinfo) {
                sharedid = filelink.getText();
                File myObj = new File(idpath);
                FileWriter myWriter = new FileWriter(idpath);
                myWriter.write(sharedid);
                myWriter.close();
                getfiledata();
            } else {
                sharedid = foldlink.getText();
                File myObj = new File(idpath);
                FileWriter myWriter = new FileWriter(idpath);
                myWriter.write(sharedid);
                myWriter.close();
                getfolddata();
            }
        } catch (Exception e) {
        }
    }

    //this method has the selected data from tree view and stores it in title size and type
    public void adddata(TreeItem<String> newvalue) {
        if (newvalue.getValue() != "Null") {
            Content c = new Content();
            String val = newvalue.getValue();
            title = (newvalue.getValue());
            if (idtype.get(getKey(idtitle, val)).contains("folder")) {
                type = "Folder";
            } else {
                type = idtype.get(getKey(idtitle, val));
            }
            size = idsize1.get(getKey(idtitle, val));
        }
    }

    //adds data to table
    public void addsomething() {
        Content content = new Content();
        if (title != null) {
            content.setTitle(title);
            content.setType(type);
            content.setSize(size);
            table.getItems().add(content);
        }
        title = null;
        type = null;
        size = null;
    }

    //deletes data from table
    public void delsomething() {
        Content selected = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selected);
    }

    public void datadown() {
        textArea.appendText("Started downloading..." + "\n");
        pb.setProgress(0);
        filepath = new HashMap<>();
        if (filetree != null) {
            for (String j : filetree.keySet()) {
                StringBuilder pathBuilder = new StringBuilder();
                for (TreeItem<String> item = filetree.get(j); item != null; item = item.getParent()) {
                    if (getKey(idtitle, item.getValue()) != null) {
                        String t = getKey(idtitle, item.getValue());
                        pathBuilder.insert(0, item.getValue().substring(0, item.getValue().length() - t.length() - 3));
                    } else {
                        pathBuilder.insert(0, item.getValue());
                    }
                    pathBuilder.insert(0, "\\");
                }
                String path = pathBuilder.toString();
                filepath.put(j, downdir.substring(0, downdir.length() - 1) + path.substring(0, path.length()));
            }
        }

        foldpath = new HashMap<>();
        if (folderid != null) {
            for (String j : folderid.keySet()) {
                StringBuilder pathBuilder = new StringBuilder();
                for (TreeItem<String> item = folderid.get(j); item != null; item = item.getParent()) {
                    pathBuilder.insert(0, item.getValue());
                    pathBuilder.insert(0, "\\");
                }
                String path = pathBuilder.toString();
                foldpath.put(j, downdir.substring(0, downdir.length() - 1) + path);
            }
        }

        ObservableList<Content> data_d;
        data_d = table.getItems();
        for (Content d : data_d) {
            //System.out.println(d.getTitle());
            writeidpath(d.getTitle().toString());
        }
        textenter();
        //for(String j: finalpath.keySet())
        //System.out.println(j+" "+idtitle.get(j).substring(0,idtitle.get(j).length()-getKey(idtitle,idtitle.get(j)).length()-3));

    }

    public void writeidpath(String title) {
        if (folderid.size() != 0) {
            if (folderid.containsKey(title)) {
                ObservableList<TreeItem<String>> ar = folderid.get(title).getChildren();
                for (TreeItem<String> j : ar) {
                    if (folderid.containsKey(j.getValue())) {
                        writeidpath(j.getValue());
                    } else {
                        finalpath.put(getKey(idtitle, j.getValue()), filepath.get(j.getValue()).substring(0, filepath.get(j.getValue()).length() - idsize.get(getKey(idtitle, j.getValue()))));
                    }
                }
            } else {
                if (filepath.containsKey(title)) {
                    finalpath.put(getKey(idtitle, title), filepath.get(title).substring(0, filepath.get(title).length() - idsize.get(getKey(idtitle, title))));
                } else {
                    finalpath.put(getKey(idtitle, title), downdir.substring(0, downdir.length()));
                }
            }
        } else {
            if (filepath.containsKey(title)) {
                finalpath.put(getKey(idtitle, title), filepath.get(title).substring(0, filepath.get(title).length() - idsize.get(getKey(idtitle, title))));
            } else {
                finalpath.put(getKey(idtitle, title), downdir.substring(0, downdir.length()));
            }
        }
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    public void textenter() {
        Runnable task = new Runnable() {
            public void run() {
                runTask();
            }
        };
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    public void runTask() {
        try {
            File myob = new File(datapath);
            FileWriter writer = new FileWriter(datapath);
            for (String j : finalpath.keySet()) {
                writer.write(j + "->" + finalpath.get(j) + "->" + idtitle.get(j).substring(0, idtitle.get(j).length() - getKey(idtitle, idtitle.get(j)).length() - 3) + System.getProperty("line.separator"));
            }
            writer.close();
            list = new ArrayList<String>();
            try {
                z = 0;
                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", downpath);
                builder.redirectErrorStream(true);
                Process p = builder.start();
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while (true) {
                    final String line = r.readLine();
                    if (line == null) {
                        break;
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            textArea.appendText(line + "\n");
                            if (line.contains("Downloading...")) {
                                pb.setProgress((float) z / finalpath.size());
                                z = z + 1;
                            }
                        }
                    });

                }
                p.waitFor();
            } catch (Exception e) {
                System.out.println(e);
            }
            myob.delete();
            pb.setProgress(1.0);
            finalpath.clear();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getfolddata() {
        foldprocess();
    }

    public void foldprocess() {
        Runnable taskfold = new Runnable() {
            public void run() {
                runtaskfold();
            }
        };
        Thread backgroundThread = new Thread(taskfold);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    public void runtaskfold() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sc.setCursor(Cursor.WAIT);
            }
        });
        list = new ArrayList<>();
        temp = new ArrayList<>();
        root2 = new TreeItem<>("Shared Drive");
        try {
            
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", execfoldpy);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                list.add(line);
                if (line == null) {
                    break;
                }
            }
            p.waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }

        k = 0;
        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(i));
            if (((i + 1) % 5) == 0) {
                id = temp.get(k + 0);
                type = temp.get(k + 1);
                parent = temp.get(k + 2);
                title = temp.get(k + 3);
                size = temp.get(k + 4);
                idsize1.put(id, size);
                idtitle.put(id, title + "---" + id);
                parentid.put(id, parent);
                idtype.put(id, type);
                idsize.put(id, title.length());
                if (type.contains("folder")) {
                    folderid.put(idtitle.get(id), new TreeItem<>(idtitle.get(id)));
                } else {
                    fileid.put(id, idtitle.get(parent));
                }

                k = k + 5;
            }
        }

        //adding leaf nodes of files to tree
        for (String j : fileid.keySet()) {
            TreeItem<String> item = new TreeItem<>(idtitle.get(j));
            item.setExpanded(true);
            filetree.put(idtitle.get(j), item);
            folderid.get(fileid.get(j)).getChildren().add(item);
        }

        //contructing tree by subfolders
        for (String j : parentid.keySet()) {
            if (idtype.get(j).contains("folder")) {

                if (idtitle.get(parentid.get(j)) == null) {
                    ArrayList<String> b = new ArrayList<>();
                    b.add(idtitle.get(j));
                    folderparent.put("main", b);
                    b = null;
                    root.getChildren().add(folderid.get(idtitle.get(j)));
                } else {
                    if (folderparent.containsKey(idtitle.get(parentid.get(j)))) {
                        ArrayList<String> b;
                        b = folderparent.get(idtitle.get(parentid.get(j)));
                        b.add(idtitle.get(j));
                        folderparent.put(idtitle.get(parentid.get(j)), b);
                        folderid.get(idtitle.get(parentid.get(j))).getChildren().add(folderid.get(idtitle.get(j)));
                    } else {
                        ArrayList<String> b = new ArrayList<>();
                        b.add(idtitle.get(j));
                        folderparent.put(idtitle.get(parentid.get(j)), b);
                        folderid.get(idtitle.get(parentid.get(j))).getChildren().add(folderid.get(idtitle.get(j)));
                    }
                }
            }
        }
        ObservableList<TreeItem<String>> cl = root.getChildren();
        for (TreeItem<String> j : cl) {
            tempo.add(j);
        }
        for (TreeItem<String> j : tempo) {
            root2.getChildren().add(j);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drive.setRoot(root2);
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sc.setCursor(Cursor.DEFAULT);
            }
        });
    }

    public void getfiledata() {
        fileprocess();
    }

    public void fileprocess() {
        Runnable taskfile = new Runnable() {
            public void run() {
                runtaskfile();
            }
        };
        Thread backgroundThread = new Thread(taskfile);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    public void runtaskfile() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sc.setCursor(Cursor.WAIT);
            }
        });
        filetitleid = new HashMap<>();
        list = new ArrayList<>();
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", execfilepy);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                list.add(line);
                if (line == null) {
                    break;
                }
            }
            p.waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
        size = list.get(1);
        type = list.get(2);
        id = list.get(3);
        title = list.get(0) + "---" + id;
        idtitle.put(id, title);
        idsize.put(id, title.length());
        addsomething();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sc.setCursor(Cursor.DEFAULT);
            }
        });
    }
}
