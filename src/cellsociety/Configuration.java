package cellsociety;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
  private String fpath = ".xml";
  private String celltype;
  private NodeList nList;
  private Element element;

  /**
   * Constructor for a configuration object, takes in the filename and decides which type of the simulation the contents of the file represent.
   * Based on what type of simulation it is, the appropriate method will be called for parsing.
   * @param filename
   */
  public Configuration(String filename){
    //type = filename;
    String xmlpath = frontpath+filename+fpath;
    docInit(xmlpath);
    genConfigFile(init_state, filename, 20, 10, 0.7);
    //errorCheck(element);
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
      String unverified_type = ((Element)nList.item(0)).getElementsByTagName("sim_type").item(0).getTextContent();
      boolean isError = errorCheck(unverified_type);
      if(isError){
        return;
      }
      type = unverified_type;

      for(int temp = 0; temp<nList.getLength(); temp++){
        Node nNode = nList.item(temp);

        if(nNode.getNodeType() == Node.ELEMENT_NODE){
          element = (Element) nNode;
          width = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
          height = Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());
          for(int i = 1; i<=height; i++){
            String num = "s"+i;
            String s = element.getElementsByTagName(num).item(0).getTextContent();
            int[] arr = Arrays.stream(s.substring(0, s.length()).split("")).map(String::trim).mapToInt(Integer::parseInt).toArray();
            for(int k = 0; k<arr.length; k++){
              init_state.add(arr[k]);
            }
          }
        }
      }
    } catch(Exception e){
      e.printStackTrace();
    }


  }

  /**
   * Method to set the initial states to various difference values, by random, by preference and other options.
   *
   * i=1 Allows the user to generate a random init state based
   * @param i
   */

  public void initStateOp(int i){

    int max_val = Collections.max(init_state);
    int size = init_state.size();

    if(i == 1){
      for(int j = 0; j<size; j++){
        Random rand = new Random();
        //double randomDouble = Math.random();
        //randomDouble = randomDouble * (max_val);
        init_state.set(j, rand.nextInt(max_val+1));
      }
    }
    else if(i == 2){

    }

    //init_state
  }

  public void genConfigFile(ArrayList<Integer> currentState, String sim_type, int width, int height, double prob){

    try{
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("type");
      doc.appendChild(rootElement);
      Element simtype = doc.createElement("sim_type");
      simtype.appendChild(doc.createTextNode(sim_type));
      rootElement.appendChild(simtype);
      Element wid = doc.createElement("width");
      wid.appendChild(doc.createTextNode(""+width+""));
      rootElement.appendChild(wid);
      Element hei = doc.createElement("height");
      hei.appendChild(doc.createTextNode(""+height+""));
      rootElement.appendChild(hei);
      int marker = 0;
      for(int i = 1; i<=height; i++){
        Element temp = doc.createElement("s"+i);
        List<Integer> list = init_state.subList(marker, marker+width);
        String stemp = Arrays.toString(list.toArray()).replaceAll(",","").replaceAll(" ", "");
        stemp = stemp.substring(1, stemp.length()-1);
        temp.appendChild(doc.createTextNode(""+stemp+""));
        rootElement.appendChild(temp);
        marker+=width;
      }
      Element probab = doc.createElement("prob");
      probab.appendChild(doc.createTextNode(""+prob+""));
      rootElement.appendChild(probab);
      Element author = doc.createElement("author");
      author.appendChild(doc.createTextNode("Amjad, Michael, Frank"));
      rootElement.appendChild(author);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("test.xml"));
      transformer.transform(source, result);
      System.out.println("File saved!");

    } catch(ParserConfigurationException pce){

    } catch(TransformerException tfe){

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
   * Error checking method that checks for:
   * 1. Invalid or no simulation type given.
   * 2. Default parameter for sim type is Fire.
   *
   * @param el
   */
  public void errorCheck(Element el){

  }

  public boolean errorCheck(String type){
    if(!type.equals("Fire") && !type.equals("GameOfLife") && !type.equals("Percolation") && !type.equals("PredatorPrey") && !type.equals("Segregation") || type==null){
      System.out.println("ERROR: Invalid Sim Type or No Sim Type Given");
      return true;
    }
    return false;
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
