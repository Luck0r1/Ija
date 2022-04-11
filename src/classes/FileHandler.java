
package classes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

public class FileHandler{
    
    public void Rename_F(String dir,String old,String newN){

        for (File phil : new File(dir).listFiles()){
            if(phil.getName().equals(old)){
                phil.renameTo(new File(dir+"/"+newN));
            }
        }
    }

    public void Delete_F(String dir,String name){
        for (File phil : new File(dir).listFiles()){
            
            if(phil.getName().equals(name)){
                phil.delete();
            }
        }
    }

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

                for (Class n_class : curClass.GetClasses()) {
                    Element add_class = doc.createElement(n_class.GetName());
                    add_class.setAttribute("x", Integer.toString( n_class.GetPosX()));
                    add_class.setAttribute("y", Integer.toString( n_class.GetPosY()));
                    Element add_args = doc.createElement("args");
                    for(CD_Element n_elem : n_class.GetElements()){
                        Element add_elem = doc.createElement(n_elem.GetName());
                        add_elem.setAttribute("type", n_elem.GetType());
                        add_elem.setAttribute("access", n_elem.GetAccess());
                        add_args.appendChild(add_elem);
                    }
                    Element add_funcs = doc.createElement("funcs");
                    for(CD_Element n_elem : n_class.GetFunca()){
                        Element add_elem = doc.createElement(n_elem.GetName());
                        add_elem.setAttribute("type", n_elem.GetType());
                        add_elem.setAttribute("access", n_elem.GetAccess());
                        add_funcs.appendChild(add_elem);
                    }
                    add_class.appendChild(add_args);
                    add_class.appendChild(add_funcs);
                    classes.appendChild(add_class);
                }

                for (Bind n_bind : curClass.GetBinds()) {
                    Element add_bind = doc.createElement(n_bind.GetName());
                    for(Class n_elem : n_bind.GetClasses()){
                        Element add_c = doc.createElement(n_elem.GetName());
                        add_bind.appendChild(add_c);
                    }
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                //transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                DOMSource domSource = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(new File(pathway+"/"+fileN));

                transformer.transform(domSource, streamResult);

            } catch (ParserConfigurationException pce) {
                //TODO: handle exception
            } catch (TransformerException tfe){
                //TODO
            }
        }
    }

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


            for(int i=0; i<clssList.getLength();i++ ){
                NodeList argList = clssList.item(i).getChildNodes().item(0).getChildNodes();
                NodeList funcList = clssList.item(i).getChildNodes().item(1).getChildNodes();

                toLoad.Class_Add( clssList.item(i).getNodeName());
                Class toAdd = toLoad.Class_Get(clssList.item(i).getNodeName());
                toAdd.SetPos(Integer.parseInt( clssList.item(i).getAttributes().item(0).getNodeValue()),Integer.parseInt( clssList.item(i).getAttributes().item(1).getNodeValue()));
                for(int j = 0 ; j<argList.getLength();j++){
                    toAdd.addElement(argList.item(j).getNodeName());
                    toAdd.Get_Element(argList.item(j).getNodeName()).ReType(argList.item(j).getAttributes().item(0).getNodeName());
                    if(argList.item(j).getAttributes().item(1).getNodeName().equals("0")){
                        toAdd.Get_Element(argList.item(j).getNodeName()).SetType(AccesT.PUBLIC);
                    }else
                    if(argList.item(j).getAttributes().item(1).getNodeName().equals("1")){
                        toAdd.Get_Element(argList.item(j).getNodeName()).SetType(AccesT.PROTECTED);
                    }else{
                        toAdd.Get_Element(argList.item(j).getNodeName()).SetType(AccesT.PRIVATE);
                    }
                }
                for(int j = 0 ; j<funcList.getLength();j++){
                    toAdd.addFunc(funcList.item(j).getNodeName());
                    toAdd.Get_Element(argList.item(j).getNodeName()).ReType(argList.item(j).getAttributes().item(0).getNodeName());
                    if(argList.item(j).getAttributes().item(1).getNodeName().equals("0")){
                        toAdd.Get_Element(argList.item(j).getNodeName()).SetType(AccesT.PUBLIC);
                    }else
                    if(argList.item(j).getAttributes().item(1).getNodeName().equals("1")){
                        toAdd.Get_Element(argList.item(j).getNodeName()).SetType(AccesT.PROTECTED);
                    }else{
                        toAdd.Get_Element(argList.item(j).getNodeName()).SetType(AccesT.PRIVATE);
                    }
                }
            }

            for(int i=0; i<bindList.getLength();i++ ){
                toLoad.Bind_Add( bindList.item(i).getLocalName() );
                Bind toAdd = toLoad.Bind_Get(clssList.item(i).getLocalName());
                NodeList elemsToAdd = clssList.item(i).getChildNodes();
                for(int j = 0 ; j<elemsToAdd.getLength();j++){
                    toAdd.Class_Add (toLoad.Class_Get(elemsToAdd.item(j).getLocalName()));
                }
            }
            
            return toLoad;
        } catch (Exception e) {
            //TODO: handle exception
            return null;
        }
    }

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

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                //transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                DOMSource domSource = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(new File(pathway+"/New_class.xml"));

                transformer.transform(domSource, streamResult);


            } catch (ParserConfigurationException pce) {
                //TODO: handle exception
            } catch (TransformerException tfe){
                //TODO
            }
        }
    }
    //Transformer.setOutputProperty(outputkeys.INDENT,"yes") 
}