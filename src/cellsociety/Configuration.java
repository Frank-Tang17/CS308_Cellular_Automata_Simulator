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
    public static int width;
    public static int height;
    public static String type;
    public static String author;
    public static ArrayList<Integer> init_state = new ArrayList<Integer>();
    public static double prob;
    public int shark;
    public int seg;
    public int fish;
    String celltype;

    public Configuration(String filename){
        celltype = filename;
    }

    public void parseOther(String filename){
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
                    shark = Integer.parseInt(eElement.getElementsByTagName("shark").item(0).getTextContent());
                    fish = Integer.parseInt(eElement.getElementsByTagName("fish").item(0).getTextContent());
                    type = nNode.getNodeName();
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
                    shark = Integer.parseInt(eElement.getElementsByTagName("shark").item(0).getTextContent());
                    fish = Integer.parseInt(eElement.getElementsByTagName("fish").item(0).getTextContent());
                    type = nNode.getNodeName();
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
                    seg = Integer.parseInt(eElement.getElementsByTagName("seg").item(0).getTextContent());
                    type = nNode.getNodeName();
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
                    seg = Integer.parseInt(eElement.getElementsByTagName("seg").item(0).getTextContent());
                    type = nNode.getNodeName();
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void parseFire(String filename){
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
                        int test = Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent());
                        init_state.add(test);
                    }
                    prob = Double.parseDouble(eElement.getElementsByTagName("prob").item(0).getTextContent());
                    author = eElement.getElementsByTagName("author").item(0).getTextContent();
                    type = eElement.getElementsByTagName("sim_type").item(0).getTextContent();
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
}
