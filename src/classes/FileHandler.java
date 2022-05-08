
package classes;

import support.AccesT;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.Sequence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



import classes.ClassDiagram;

/**
 * Class used for handling files. Has functions that load, save and create Class diagrams.
 * These diagrams are saved in .xml format and are readable only by this application.
 * Dom parser is used for parsing and creating xml files.
 * @author xlukac16
 */
public class FileHandler{
    
    /**
     * Renames file 
     * @param dir path to directory
     * @param old old name
     * @param newN new Name
     */
    public void Rename_F(String dir,String old,String newN){

        for (File phil : new File(dir).listFiles()){
            if(phil.getName().equals(old)){
                phil.renameTo(new File(newN));
            }
        }
    }

    /**
     * Deletes file
     * @param dir path to directory
     * @param name name of file for deletion
     */
    public void Delete_F(String dir,String name){
        for (File phil : new File(dir).listFiles()){
            
            if(phil.getName().equals(name)){
                phil.delete();
            }
        }
    }

    /**
     * Saves class diagram in .xml format
     * @param pathway path to directory
     * @param curClass classdiagram being saved
     */
    public void Save(String pathway,ClassDiagram curClass){
        
        String fileN = curClass.GetName();

        //Check if file exists ask for overwrite
        if(true){
            try {
                
                DocumentBuilderFactory documentFac = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = documentFac.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                
                Element root = doc.createElement("head");
                doc.appendChild(root);

                Element classes = doc.createElement("Classes");
                root.appendChild(classes);
                Element binds = doc.createElement("Binds");
                root.appendChild(binds);
                Element sequenceDias = doc.createElement("seqDia");
                root.appendChild(sequenceDias);

                for (Class n_class : curClass.GetClasses()) {
                    Element add_class = doc.createElement("newClass");
                    add_class.setAttribute("x", Integer.toString( n_class.GetPosX()));
                    add_class.setAttribute("y", Integer.toString( n_class.GetPosY()));
                    add_class.setAttribute("id", Integer.toString(n_class.GetId()));
                    add_class.setAttribute("zname", n_class.GetName());
                    Element add_args = doc.createElement("args");
                    for(CD_Element n_elem : n_class.GetElements()){
                        Element add_elem = doc.createElement("newAttrib");
                        add_elem.setAttribute("type", n_elem.GetType());
                        add_elem.setAttribute("access", n_elem.GetAccess());
                        add_elem.setAttribute("zname", n_elem.GetName());
                        add_args.appendChild(add_elem);
                    }
                    Element add_funcs = doc.createElement("funcs");
                    for(CD_Element n_elem : n_class.GetFunca()){
                        Element add_elem = doc.createElement("newFunc");
                        add_elem.setAttribute("type", n_elem.GetType());
                        add_elem.setAttribute("access", n_elem.GetAccess());
                        add_elem.setAttribute("rtype", n_elem.GetReturnT());
                        add_elem.setAttribute("zname", n_elem.GetName());
                        add_funcs.appendChild(add_elem);
                    }
                    add_class.appendChild(add_args);
                    add_class.appendChild(add_funcs);
                    classes.appendChild(add_class);
                }

                for (Bind n_bind : curClass.GetBinds()) {
                    Element add_bind = doc.createElement("newBind");
                    add_bind.setAttribute("c1", n_bind.Get_C1());
                    add_bind.setAttribute("c2", n_bind.Get_C2());
                    add_bind.setAttribute("lType",Integer.toString(n_bind.Type_Get_L()));
                    add_bind.setAttribute("rType",Integer.toString(n_bind.Type_Get_R()));
                    add_bind.setAttribute("zname", n_bind.GetName());

                        Class n_elem = n_bind.GetClass1();
                        Element add_c = doc.createElement("cOne");
                        add_c.setAttribute("id", Integer.toString(n_elem.GetId()));
                        add_bind.appendChild(add_c);

                        Class n_elem2 = n_bind.GetClass2();
                        Element add_c2 = doc.createElement("cTwo");
                        add_c2.setAttribute("id", Integer.toString(n_elem2.GetId()));
                        add_bind.appendChild(add_c2);
                    binds.appendChild(add_bind);
                }

                for (SequenceDia sq : curClass.GetSeqD()){
                    sq.ReId();
                    Element add_sequence_dia = doc.createElement("Sequ");
                    add_sequence_dia.setAttribute("zname", sq.GetName());

                    Element cast = doc.createElement("cast");
                    for (Actor a : sq.GetCast()){

                        Element actor = doc.createElement("Actor");
                        String s;
                        if(a.IsEternal())s="1";
                        else s="0";
                        actor.setAttribute("eternal",s);
                        actor.setAttribute("id", Integer.toString(a.GetClass().GetId()));
                        cast.appendChild(actor);

                    }
                    add_sequence_dia.appendChild(cast);

                    Element loveLetters = doc.createElement("messages");
                    for(Message m : sq.GetMessages()){
                        Element message = doc.createElement("NewMessage");
                        String s;
                        String c;
                        if(m.GetResponse())s="1";
                        else s="0";
                        if(m.IsConstructor())c="1";
                        else c="0";
                        message.setAttribute("response", s);
                        message.setAttribute("func", Integer.toString(m.GetFuncId()));
                        message.setAttribute("a1", Integer.toString(sq.GetCast().indexOf(m.GetA1())));
                        message.setAttribute("a2", Integer.toString(sq.GetCast().indexOf(m.GetA2())));
                        message.setAttribute("constructor", c);
                        message.setAttribute("text", m.GetText());
                        message.setAttribute("zname", m.GetName());
                        
                        loveLetters.appendChild(message);
                    }
                    add_sequence_dia.appendChild(loveLetters);

                    sequenceDias.appendChild(add_sequence_dia);
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(new File(pathway+"/"+fileN));

                transformer.transform(domSource, streamResult);

            } catch (ParserConfigurationException pce) {
                System.exit(1);
            } catch (TransformerException tfe){
                System.exit(1);
            }
        }
    }

    /**
     * Loads diagram from .xml file
     * @param pathway path to directory
     * @param fileL file name
     * @return new classdiagram //P.S my class diagram also includes it's sequence diagrams
     */
    public ClassDiagram Load(String pathway, String fileL){
        try{
            File inputFile = new File(pathway+"/"+fileL);
            DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbFac.newDocumentBuilder();
            Document doc = db.parse(inputFile);


            doc.getDocumentElement().normalize();

            ClassDiagram toLoad = new ClassDiagram( fileL);



            NodeList clssList = doc.getDocumentElement().getChildNodes().item(0).getChildNodes();
            NodeList bindList = doc.getDocumentElement().getChildNodes().item(1).getChildNodes();
            NodeList seqDList = doc.getDocumentElement().getChildNodes().item(2).getChildNodes();


            for(int i=0; i<clssList.getLength();i++ ){
                NodeList argList = clssList.item(i).getChildNodes().item(0).getChildNodes();
                NodeList funcList = clssList.item(i).getChildNodes().item(1).getChildNodes();

                Class toAdd = toLoad.Class_Add( clssList.item(i).getAttributes().item(3).getNodeValue());
                toAdd.SetPos(Integer.parseInt( clssList.item(i).getAttributes().item(1).getNodeValue()),Integer.parseInt( clssList.item(i).getAttributes().item(2).getNodeValue()));
                toAdd.SetId(Integer.parseInt(clssList.item(i).getAttributes().item(0).getNodeValue()));

                for(int j = 0 ; j<argList.getLength();j++){
                    CD_Element newElement = toAdd.addElement(argList.item(j).getAttributes().item(2).getNodeValue());
                    newElement.ReType(argList.item(j).getAttributes().getNamedItem("type").getNodeValue());
                    if(argList.item(j).getAttributes().getNamedItem("access").getNodeValue().equals("public")){
                        newElement.SetType(AccesT.PUBLIC);
                    }else
                    if(argList.item(j).getAttributes().getNamedItem("access").getNodeValue().equals("protected")){
                        newElement.SetType(AccesT.PROTECTED);
                    }else{
                        newElement.SetType(AccesT.PRIVATE);
                    }
                }

                for(int j = 0 ; j<funcList.getLength();j++){
                    CD_Element newFunction = toAdd.addFunc(funcList.item(j).getAttributes().item(2).getNodeValue());
                    newFunction.ReType(funcList.item(j).getAttributes().getNamedItem("type").getNodeValue());
                    newFunction.SetReturnT(funcList.item(j).getAttributes().getNamedItem("rtype").getNodeValue());
                    if(funcList.item(j).getAttributes().getNamedItem("access").getNodeValue().equals("public")){                    
                        newFunction.SetType(AccesT.PUBLIC);
                    }else
                    if(funcList.item(j).getAttributes().getNamedItem("access").getNodeValue().equals("protected")){
                        newFunction.SetType(AccesT.PROTECTED);
                    }else{
                        newFunction.SetType(AccesT.PRIVATE);
                    }
                }
            }
            

            for(int i=0; i<bindList.getLength();i++ ){
                //System.err.println("here");
                NodeList elemsToAdd = bindList.item(i).getChildNodes();
                //System.err.println(toLoad.GetClasses());
                //System.err.println(Integer.parseInt(elemsToAdd.item(1).getAttributes().item(0).getNodeValue()));
                Bind adder = new Bind(bindList.item(i).getAttributes().item(4).getNodeValue(),
                                        toLoad.GetClasses().get(Integer.parseInt(elemsToAdd.item(0).getAttributes().item(0).getNodeValue())),
                                        toLoad.GetClasses().get(Integer.parseInt(elemsToAdd.item(1).getAttributes().item(0).getNodeValue())));
                //System.err.println("here");
                adder.Set_C1(bindList.item(i).getAttributes().item(0).getNodeName());
                adder.Set_C2(bindList.item(i).getAttributes().item(1).getNodeName());
                adder.Type_Set_L(Integer.parseInt(bindList.item(i).getAttributes().item(2).getNodeValue()));  
                adder.Type_Set_R(Integer.parseInt(bindList.item(i).getAttributes().item(3).getNodeValue()));
                toLoad.Bind_FAdd(adder);
                
            }
            

            for(int i=0;i<seqDList.getLength();i++){
                SequenceDia toAdd = new SequenceDia(seqDList.item(i).getAttributes().item(0).getNodeValue(),toLoad);
                NodeList acList = seqDList.item(i).getChildNodes().item(0).getChildNodes();
                NodeList msList = seqDList.item(i).getChildNodes().item(1).getChildNodes();
                for(int j = 0;j<acList.getLength();j++){
                    Boolean eternal = (Integer.parseInt(acList.item(j).getAttributes().item(0).getNodeValue())==1);
                    toAdd.AddActor(toLoad.Class_Get_By_Id(Integer.parseInt(acList.item(j).getAttributes().item(1).getNodeValue())), eternal);
                    toAdd.ReId();
                }
                for(int j =0;j<msList.getLength();j++){
                    Boolean response = (Integer.parseInt(msList.item(j).getAttributes().item(4).getNodeValue())==1);
                    int a1n = Integer.parseInt(msList.item(j).getAttributes().item(0).getNodeValue());
                    int a2n = Integer.parseInt(msList.item(j).getAttributes().item(1).getNodeValue());
                    int fn = Integer.parseInt(msList.item(j).getAttributes().item(3).getNodeValue());
                    Boolean mc = (Integer.parseInt(msList.item(j).getAttributes().item(2).getNodeValue())==1);
                    String text = msList.item(j).getAttributes().item(5).getNodeValue();
                    CD_Element func;
                    //System.out.print(mc);
                    Actor a1;
                    Actor a2;
                    if(a1n==-1)a1=null;
                    else a1=toAdd.GetCast().get(a1n);
                    if(a2n==-1)a2=null;
                    else a2=toAdd.GetCast().get(a2n);
                    if(fn==-1)func=null;
                    else func = toAdd.GetPossibleFunctions(a2.GetClass()).get(fn);
                    //toLoad.Element_Get_By_Id(fn);
                    toAdd.AddMessage(response, func, a1, a2,mc,text);
                }
                toLoad.SequenceDia_FAdd(toAdd);
            }
            
            return toLoad;
        } catch (Exception e) {
            //TODO: handle exception
            System.err.println(e);
            System.exit(1);
            return null;
        }
    }

    /**
     * Creates new empty file with new class diagram
     * @param pathway path to directory
     */
    public void New(String pathway){
        if(true){
            try {
                DocumentBuilderFactory documentFac = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = documentFac.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                
                Element root = doc.createElement("New_class");
                doc.appendChild(root);

                Element classes = doc.createElement("Classes");
                root.appendChild(classes);
                Element binds = doc.createElement("Binds");
                root.appendChild(binds);
                Element seqDias = doc.createElement("seqDia");
                root.appendChild(seqDias);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                //transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                DOMSource domSource = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(new File(pathway+"/New_class.xml"));

                transformer.transform(domSource, streamResult);


            } catch (ParserConfigurationException pce) {
                System.exit(1);
            } catch (TransformerException tfe){
                System.exit(1);
            }
        }
    }
}