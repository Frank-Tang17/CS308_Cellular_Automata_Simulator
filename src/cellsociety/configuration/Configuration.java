package cellsociety.configuration;

import cellsociety.view.DisplayError;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

/**
 * Class to handle the configuration file parsing and feeding the data back to the main classes for
 * each simulation to use. Code that will be used for masterpiece.
 *
 * The getter and setter methods have been removed for the purpose of meeting the line requirement and focus on the big elements.
 *
 * I think this code is well designed because it separates the different features of the config parser and makes the method lean
 * and functional. The data is also all private and dealt with only when needed, with getter and setter methods provided correctly.
 * Additionally, the code was significantly refactored to remove all of the redundancies that the parsing methods had that were specific
 * to each simulation. To resolve this, the core functional parsing operations were put into docInit, while lighter methods to deal with the specific
 * simulation variables in each type were created. This greatly improved the code's design and flexibility to accommodate future simulations as well.
 * The gen config file method also had many redundancies when creating the tags for the xml file, but these were resolved by putting in for loops.
 *
 *
 */
public class Configuration {

  private int width;
  private int height;
  private String type;
  private ArrayList<Integer> init_state = new ArrayList<>();
  private double prob;
  private int starting_energy_shark;
  private int energy_in_fish;
  private int num_frames_for_shark;
  private int num_frames_for_fish;
  private double seg_thresh;
  private NodeList nList;
  private Element element;
  private int[] nColIndex;
  private int[] nRowIndex;
  private String[] colors;
  private String[] scolors;
  private boolean toroidal;
  private boolean hexagonal;
  private static boolean generateRandomSimulation = false;

  /**
   * Constructor for a configuration object, takes in the filename and decides which type of the
   * simulation the contents of the file represent. Based on what type of simulation it is, the
   * appropriate method will be called for parsing.
   *
   * @param filename
   */
  public Configuration(File filename) {
    docInit(filename);
    //randomize();
    //if (generateRandomSimulation) {
    //randomize();
    //}
    if (type.equals("Fire")) {
      parseFire(element);
    } else if (type.equals("PredatorPrey")) {
      parsePredPray(element);
    } else if (type.equals("Segregation") || type.equals("Rps")) {
      parseSegRps(element);
    }
  }

  /**
   * Method to iniitialize the variables needed to extract information from the xml file that is
   * read in. Reduces the amount of duplicate code needed from each simulation's parser. Populates
   * the required variables based on what type of simulation it is.
   *
   * @param filename
   */

  public void docInit(File filename) {
    try {
      File fxml = filename;
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fxml);
      doc.getDocumentElement().normalize();

      nList = doc.getElementsByTagName("type");
      String unverified_type = ((Element) nList.item(0)).getElementsByTagName("sim_type").item(0)
              .getTextContent();
      boolean isError = errorCheck(unverified_type);
      if (isError) {
        return;
      }
      type = unverified_type;

      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          element = (Element) nNode;
          width = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
          height = Integer
                  .parseInt(element.getElementsByTagName("height").item(0).getTextContent());

          String tempp = element.getElementsByTagName("color").item(0).getTextContent();
          colors = tempp.trim().split(" ");
          String stempp = element.getElementsByTagName("scolor").item(0).getTextContent();
          scolors = stempp.trim().split(" ");
          String[] temp11 = (element.getElementsByTagName("neighborColIndex").item(0)
                  .getTextContent()).trim().split(" ");
          String[] temp22 = (element.getElementsByTagName("neighborRowIndex").item(0)
                  .getTextContent()).trim().split(" ");
          toroidal =
                  (Integer.parseInt(element.getElementsByTagName("toroidal").item(0).getTextContent()))
                          == 1;
          hexagonal =
                  (Integer.parseInt(element.getElementsByTagName("hexagonal").item(0).getTextContent()))
                          == 1;
          nColIndex = new int[temp11.length];
          nRowIndex = new int[temp22.length];
          for (int i = 0; i < temp11.length; i++) {
            nColIndex[i] = Integer.parseInt(temp11[i]);
          }
          for (int j = 0; j < temp22.length; j++) {
            nRowIndex[j] = Integer.parseInt(temp22[j]);
          }

          for (int i = 1; i <= height; i++) {
            String num = "s" + i;
            String s = element.getElementsByTagName(num).item(0).getTextContent();
            int[] arr = Arrays.stream(s.substring(0, s.length()).split("")).map(String::trim)
                    .mapToInt(Integer::parseInt).toArray();
            for (int k = 0; k < arr.length; k++) {
              init_state.add(arr[k]);
            }
          }
        }
      }
    } catch (Exception e) {
      new DisplayError("English", "Error initializing doc");
    }

  }

  /**
   * Method to set the initial states to various difference values, by random, by preference and
   * other options.
   */

  public void randomize() {

    int max_val = Collections.max(init_state);
    int size = init_state.size();
    for (int j = 0; j < size; j++) {
      Random rand = new Random();
      //double randomDouble = Math.random();
      //randomDouble = randomDouble * (max_val);
      init_state.set(j, rand.nextInt(max_val + 1));
    }
  }

  /**
   * Method that allows you to pause the simulation at any point and generate a configuration file
   * of the current state. Saves all the variables and information of the simulation to be able to
   * run it again starting from the same point.
   *
   * @param currentState
   * @param sim_type
   * @param width
   * @param height
   * @param prob
   */
  private void genConfigFile(ArrayList<Integer> currentState, String sim_type, int width, int height,
                             double prob) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("type");
      doc.appendChild(rootElement);
      Element simtype = doc.createElement("sim_type");
      simtype.appendChild(doc.createTextNode(sim_type));
      rootElement.appendChild(simtype);
      Element wid = doc.createElement("width");
      wid.appendChild(doc.createTextNode("" + width + ""));
      rootElement.appendChild(wid);
      Element hei = doc.createElement("height");
      hei.appendChild(doc.createTextNode("" + height + ""));
      rootElement.appendChild(hei);
      int marker = 0;
      for (int i = 1; i <= height; i++) {
        Element temp = doc.createElement("s" + i);
        List<Integer> list = currentState.subList(marker, marker + width);
        String stemp = Arrays.toString(list.toArray()).replaceAll(",", "").replaceAll(" ", "");
        stemp = stemp.substring(1, stemp.length() - 1);
        temp.appendChild(doc.createTextNode("" + stemp + ""));
        rootElement.appendChild(temp);
        marker += width;
      }
      Element probab = doc.createElement("prob");
      probab.appendChild(doc.createTextNode("" + prob + ""));
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

    } catch (ParserConfigurationException pce) {
      new DisplayError("English", "ParserConfigurationException");
    } catch (TransformerException tfe) {
      new DisplayError("English", "TransformerException");
    }
  }

  /**
   * Method to parse the variables and information from an xml file containing information on how to
   * run a Predator Prey simulation.
   *
   * @param
   */

  private void parsePredPray(Element el) {
    this.num_frames_for_fish = Integer
            .parseInt(el.getElementsByTagName("num_frames_for_fish").item(0).getTextContent());
    this.num_frames_for_shark = Integer
            .parseInt(el.getElementsByTagName("num_frames_for_shark").item(0).getTextContent());
    this.starting_energy_shark = Integer
            .parseInt(el.getElementsByTagName("starting_energy_shark").item(0).getTextContent());
    this.energy_in_fish = Integer
            .parseInt(el.getElementsByTagName("energy_in_fish").item(0).getTextContent());
  }

  /**
   * Error checking method that checks for invalid or no simulation type given.
   *
   * @param type is the type of simulation being inputted
   */

  private boolean errorCheck(String type) {
    if (!type.equals("Fire") && !type.equals("GameOfLife") && !type.equals("Percolation") && !type
            .equals("PredatorPrey") && !type.equals("Segregation") && !type.equals("Rps")
            || type == null) {
      System.out.println("ERROR: Invalid Sim Type or No Sim Type Given");
      return true;
    }
    return false;
  }

  /**
   * Method to parse the variables and information from an xml file containing information on how to
   * run a segmentation simulation.
   *
   * @param
   */
  private void parseSegRps(Element el) {
    seg_thresh = Double.parseDouble(el.getElementsByTagName("threshold").item(0).getTextContent());
  }

  /**
   * Method to parse the variables and information from an xml file containing information on how to
   * run a Fire simulation.
   *
   * @param
   */
  private void parseFire(Element el) {
    this.prob = Double.parseDouble(el.getElementsByTagName("prob").item(0).getTextContent());
  }
}