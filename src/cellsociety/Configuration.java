package cellsociety;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Configuration {
    private int width;
    private int height;
    private String type;
    private String author;
    private ArrayList<Integer> init_state = new ArrayList<>();
    private double prob;
    private int starting_energy_shark;
    private int energy_in_fish;
    private int num_frames_for_shark;
    private int num_frames_for_fish;
    private double seg_thresh;
    private String frontpath = "src/cellsociety/";
    private String fpath = ".txt";
    private String celltype;

    public Configuration(String filename){
        System.out.println("Type is: " + filename);
        type = filename;
        String xmlpath = frontpath+type+fpath;

        if(type.equals("Fire")){
            parseFire(xmlpath);
        }
        else if(type.equals("GameOfLife")){
            parseGameLife(xmlpath);
        }
        else if(type.equals("Percolation")){
            parsePercolation(xmlpath);
        }
        else if(type.equals("PredatorPrey")){
            parsePredPray(xmlpath);
        }
        else if(type.equals("Segregation")){
            parseSeg(xmlpath);
        }

    }

    public void parsePercolation(String filename){
        try{
            File fxml = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fxml);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("type");

            for(int temp = 0; temp<nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    width = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
                    height = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
                    for(int i = 0; i<width*height; i++){
                        init_state.add(Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent()));
                    }
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void parsePredPray(String filename){
        try{
            File fxml = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fxml);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("type");

            for(int temp = 0; temp<nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    width = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
                    height = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
                    for(int i = 0; i<width*height; i++){
                        System.out.println(i);
                        init_state.add(Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent()));
                    }
                    num_frames_for_fish = Integer.parseInt(eElement.getElementsByTagName("num_frames_for_fish").item(0).getTextContent());
                    num_frames_for_shark = Integer.parseInt(eElement.getElementsByTagName("num_frames_for_shark").item(0).getTextContent());
                    starting_energy_shark = Integer.parseInt(eElement.getElementsByTagName("starting_energy_shark").item(0).getTextContent());
                    energy_in_fish = Integer.parseInt(eElement.getElementsByTagName("energy_in_fish").item(0).getTextContent());
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void parseSeg(String filename){
        try{
            File fxml = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fxml);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("type");

            init_state = new ArrayList<Integer>();

            for(int temp = 0; temp<nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    width = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
                    height = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
                    for(int i = 0; i<width*height; i++){
                        init_state.add(Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent()));
                    }
                    seg_thresh = Double.parseDouble(eElement.getElementsByTagName("threshold").item(0).getTextContent());
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void parseGameLife(String filename){
        try{
            File fxml = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fxml);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("type");

            for(int temp = 0; temp<nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    width = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
                    height = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
                    for(int i = 0; i<width*height; i++){
                        init_state.add(Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent()));
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void parseFire(String filename){
        try{
            File fxml = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fxml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("type");

            for(int temp = 0; temp<nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    this.width = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
                    this.height = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());

                    for(int i = 0; i<this.width*this.height; i++){
                        int test = Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent());
                        init_state.add(test);
                    }
                    this.prob = Double.parseDouble(eElement.getElementsByTagName("prob").item(0).getTextContent());
                    this.author = eElement.getElementsByTagName("author").item(0).getTextContent();
                }
            }
            System.out.println(width);
            System.out.println(height);
            System.out.println(type);
            System.out.println(Arrays.toString(init_state.toArray()));
            System.out.println(prob);
            System.out.println(author);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public String getType(){
        return this.type;
    }

    public ArrayList<Integer> getInitState(){
        return this.init_state;
    }
}
