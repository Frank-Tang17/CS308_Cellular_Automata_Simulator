package cellsociety;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to handle the configuration file parsing and feeding the data back to the main classes for each simulation to use.
 */
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
  private NodeList nList;
  private Element element;

  /**
   * Constructor for a configuration object, takes in the filename and decides which type of the simulation the contents of the file represent.
   * Based on what type of simulation it is, the appropriate method will be called for parsing.
   * @param filename
   */
  public Configuration(String filename){
    type = filename;
    String xmlpath = frontpath+type+fpath;
    docInit(xmlpath);
    if(type.equals("Fire")){
      parseFire(element);
    }
    else if(type.equals("GameOfLife")){
      //parseGameLife(xmlpath);
    }
    else if(type.equals("Percolation")){
      //parsePercolation(xmlpath);
    }
    else if(type.equals("PredatorPrey")){
      parsePredPray(element);
    }
    else if(type.equals("Segregation")){
      parseSeg(element);
    }

  }

  /**
   * Method to iniitialize the variables needed to extract information from the xml file that is read in. Reduces the amount of duplicate code
   * needed from each simulation's parser.
   * @param filename
   */

  public void docInit(String filename){
    try{
      File fxml = new File(filename);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fxml);

      doc.getDocumentElement().normalize();

      nList = doc.getElementsByTagName("type");

      for(int temp = 0; temp<nList.getLength(); temp++){
        Node nNode = nList.item(temp);

        if(nNode.getNodeType() == Node.ELEMENT_NODE){
          element = (Element) nNode;
          width = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
          height = Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());
          for(int i = 0; i<width*height; i++){
            init_state.add(Integer.parseInt(element.getElementsByTagName("s1").item(i).getTextContent()));
          }
        }
      }
    } catch(Exception e){
      e.printStackTrace();
    }


  }

  /**
   * Method to parse the variables and information from an xml file containing information on how to run a Predator Prey simulation.
   * @param
   */

  public void parsePredPray(Element el){
    this.num_frames_for_fish = Integer.parseInt(el.getElementsByTagName("num_frames_for_fish").item(0).getTextContent());
    this.num_frames_for_shark = Integer.parseInt(el.getElementsByTagName("num_frames_for_shark").item(0).getTextContent());
    this.starting_energy_shark = Integer.parseInt(el.getElementsByTagName("starting_energy_shark").item(0).getTextContent());
    this.energy_in_fish = Integer.parseInt(el.getElementsByTagName("energy_in_fish").item(0).getTextContent());
  }


  /**
   * Method to parse the variables and information from an xml file containing information on how to run a segmentation simulation.
   * @param
   */
  public void parseSeg(Element el){
    seg_thresh = Double.parseDouble(el.getElementsByTagName("threshold").item(0).getTextContent());
  }

  /**
   * Method to parse the variables and information from an xml file containing information on how to run a Fire simulation.
   * @param
   */
  public void parseFire(Element el){
    this.prob = Double.parseDouble(el.getElementsByTagName("prob").item(0).getTextContent());
  }

  /**
   * Getter method to retrieve the width of the simulation grid
   * @return
   */
  public int getWidth(){
    return this.width;
  }

  /**
   * Getter method to retrieve the height of the simulation grid
   * @return
   */
  public int getHeight(){
    return this.height;
  }

  /**
   * Getter method to retrieve the type of the simulation grid
   * @return
   */
  public String getType(){
    return this.type;
  }

  /**
   * Getter method to retrieve the initial state of the simulation grid
   * @return
   */
  public ArrayList<Integer> getInitState(){
    return this.init_state;
  }
}
