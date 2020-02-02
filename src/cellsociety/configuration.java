package cellsociety;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class configuration {

    public int width;
    public int height;
    public String type;
    public int[] init_state;
    public int prob;
    public int shark;
    public int seg;
    public int fish;
    String celltype;

    public configuration(String filename){
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
                        init_state[i] = Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent());
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
                        init_state[i] = Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent());
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

            for(int temp = 0; temp<nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    width = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
                    height = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
                    for(int i = 0; i<width*height; i++){
                        init_state[i] = Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent());
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
                        init_state[i] = Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent());
                    }
                    seg = Integer.parseInt(eElement.getElementsByTagName("seg").item(0).getTextContent());
                    type = nNode.getNodeName();
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
                    width = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
                    height = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
                    for(int i = 0; i<width*height; i++){
                        init_state[i] = Integer.parseInt(eElement.getElementsByTagName("s1").item(i).getTextContent());
                    }
                    prob = Integer.parseInt(eElement.getElementsByTagName("prob").item(0).getTextContent());
                    type = nNode.getNodeName();
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
