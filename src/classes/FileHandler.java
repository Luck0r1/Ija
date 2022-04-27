
package classes;

import support.AccesT;
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
                phil.renameTo(new File(newN));
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
                    add_class.setAttribute("id", Integer.toString(n_class.GetId()));
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
                        add_elem.setAttribute("rtype", n_elem.GetReturnT());
                        add_funcs.appendChild(add_elem);
                    }
                    add_class.appendChild(add_args);
                    add_class.appendChild(add_funcs);
                    classes.appendChild(add_class);
                }

                for (Bind n_bind : curClass.GetBinds()) {
                    Element add_bind = doc.createElement(n_bind.GetName());
                    add_bind.setAttribute("c1", n_bind.Get_C1());
                    add_bind.setAttribute("c2", n_bind.Get_C2());
                    for(Class n_elem : n_bind.GetClasses()){

                        Element add_c = doc.createElement(n_elem.GetName());
                        add_c.setAttribute("id", Integer.toString(n_elem.GetId()));
                        add_bind.appendChild(add_c);
                    }
                    binds.appendChild(add_bind);
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

                Class toAdd = toLoad.Class_Add( clssList.item(i).getNodeName());
                toAdd.SetPos(Integer.parseInt( clssList.item(i).getAttributes().item(1).getNodeValue()),Integer.parseInt( clssList.item(i).getAttributes().item(2).getNodeValue()));
                toAdd.SetId(Integer.parseInt(clssList.item(i).getAttributes().item(0).getNodeValue()));

                for(int j = 0 ; j<argList.getLength();j++){
                    CD_Element newElement = toAdd.addElement(argList.item(j).getNodeName());
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
                    CD_Element newFunction = toAdd.addFunc(funcList.item(j).getNodeName());
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
                NodeList elemsToAdd = bindList.item(i).getChildNodes();
                Bind adder = new Bind(bindList.item(i).getNodeName(),
                                        toLoad.Class_Get_By_Id(Integer.parseInt(elemsToAdd.item(0).getAttributes().item(0).getNodeValue())),
                                        toLoad.Class_Get_By_Id(Integer.parseInt(elemsToAdd.item(1).getAttributes().item(0).getNodeValue())));
                adder.Set_C1(bindList.item(i).getAttributes().item(0).getNodeName());
                adder.Set_C2(bindList.item(i).getAttributes().item(1).getNodeName());
                toLoad.Bind_FAdd(adder);
                
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