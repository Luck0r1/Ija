package support;

import support.*;

import java.util.*;

import javax.print.attribute.standard.Sides;

import java.awt.Dimension;
import javafx.scene.layout.Pane;
import classes.*;
import classes.Class;


/**
 * Class responsible for editing the layer under the visible parts.
 * This map is form of two dimensional array, and helps with layout of elements 
 * @author xlukac16
 */
public class Mapper{

    //Class offset
    private int offset = 5;
    //map itself
    private ArrayList<ArrayList<Integer>> mapper;

    /**
     * This returns side thats on s of side x
     * @param x side in
     * @param s is left,right,oppose
     * @return side in relation
     */
    private Side Oppose(Side x,String s){
        if(s=="oppose"){
            if(x == Side.LEFT)
                return Side.RIGHT;
            else if(x==Side.RIGHT)
                return Side.LEFT;
            else if(x==Side.BOTTOM)
                return Side.TOP;
            else
                return Side.BOTTOM;
        }else
        if(s=="left"){
            if(x == Side.LEFT)
                return Side.BOTTOM;
            else if(x==Side.RIGHT)
                return Side.TOP;
            else if(x==Side.BOTTOM)
                return Side.RIGHT;
            else
                return Side.LEFT;
        }else{
            if(x == Side.LEFT)
                return Side.TOP;
            else if(x==Side.RIGHT)
                return Side.BOTTOM;
            else if(x==Side.BOTTOM)
                return Side.LEFT;
            else
                return Side.TOP;
        }
    }
    /**
     * Loads map with size
     * @param x
     * @param y
     */
    private void LoadMap(int x,int y){
        this.mapper = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<x/5;i++)
            this.mapper.add(new ArrayList<Integer>(Collections.nCopies(y/5, 0)));
    }

    /**
     * Fills space for single class in map
     * @param c class
     */
    public void FillSpaceForClass(classes.Class c){

        int strtX = c.GetPosX();
        int strtY = c.GetPosY();
        int endX = strtX + c.GetGraphicWidth()/5;
        int endY = strtY + c.GetGraphicHeigth()/5;

        for (int i = strtX-this.offset;i<endX+this.offset;i++){
            for(int j=strtY-this.offset;j<endY +this.offset;j++){
                this.mapper.get(i).set(j, -1);
            }
        }

        for(int i = strtX; i<endX;i++){
            for (int j = strtY; j<endY;j++){
                this.mapper.get(i).set(j, c.GetId()+1);
            }
        }
    }

    /**
     * Unfills map for single class
     * @param c class
     */
    public void UnfilSpaceForClass(classes.Class c){

        int strtX = c.GetPosX();
        int strtY = c.GetPosY();
        int endX = strtX + c.GetGraphicWidth()/5;
        int endY = strtY + c.GetGraphicHeigth()/5;

        for (int i = strtX-this.offset;i<endX +this.offset;i++){
            for(int j=strtY-this.offset;j<endY +this.offset;j++){
                this.mapper.get(i).set(j, 0);
            }
        }
    }

    /**
     * Returns values of one field in map
     * @return int
     */
    public int GetVal(int x,int y){
        return mapper.get(x).get(y);
    }
    /**
     * Returns value of one field in map in world coordinates
     * @param x
     * @param y
     * @return int
     */
    public int GetMap(double x,double y){
        return this.mapper.get(Converts.ToMapCoordinatesX(x)).get(Converts.ToMapCoordinatesY(y));
    }
    /**
     * Checks if there is enough space to place a class here
     * @param startX
     * @param startY
     * @param width
     * @param height
     * @return boolean
     */
    private boolean IsEmpty(int startX,int startY,int width,int height){
        int bgnX = startX-this.offset;
        int endX = startX + width + this.offset;
        int bgnY = startY - this.offset;
        int endY = startY + height + this.offset;
        if(bgnX<=0 || bgnY<=0)return false;
        for(int i=startX; i< endX;i++){
            for(int j=startY;j<endY;j++){
                int x=1;
                try {
                    x=this.GetVal(i, j);
                } catch (Exception e) {
                    return false;
                }
                if(x!=0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds closest place to place a class*
     * @param c
     * @param newX
     * @param newY
     * @param dimX
     * @return Dimension of closest place in world coordinates
     */
    public Dimension FindClosestPlace(classes.Class c,double newX, double newY,int dimX){
        Dimension h = new Dimension(-1,-1);
        int tarPosX = Converts.ToMapCoordinatesX(newX);
        int tarPosY = Converts.ToMapCoordinatesY(newY);

        if(this.IsEmpty(tarPosX, tarPosY,  c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
            h.setSize(tarPosX, tarPosY);
            return h;
        }

        for (int d = 1; d<mapper.size()*2; d++)
        {
            for (int i = 0; i < d + 1; i++)
            {
                int x1 = tarPosX - d + i;
                int y1 = tarPosY - i;

                if(this.IsEmpty(x1, y1, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(x1, y1);
                    return h;
                }

                int x2 = tarPosX + d - i;
                int y2 = tarPosY + i;

                if(this.IsEmpty(x2, y2, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(x2, y2);
                    return h;
                }
            }


            for (int i = 1; i < d; i++)
            {
                int x3 = tarPosX - i;
                int y3 = tarPosY + d - i;

                if(this.IsEmpty(x3, y3, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(x3, y3);
                    return h;
                }

                int x4 = tarPosX + i;
                int y4 = tarPosY - d + i;

                if(this.IsEmpty(x4, y4, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(x4, y4);
                    return h;
                }
            }
        }
        return h;
    }

    /**
     * Fills place for all classes
     * @param cd
     */
    private void FillAll(ClassDiagram cd){
        for (classes.Class c : cd.GetClasses()){
            this.FillSpaceForClass(c);
        }
    }

    /**
     * Finds connecting point for class
     * @param b
     * @param c
     * @param s
     * @return Side of allocated point
     */
    private Side FillConPoint(Bind b,Class c,Side s){
        List<Dimension> listDim;
        List<Dimension> retDim = new ArrayList<Dimension>();
        if(s==Side.TOP)
            listDim = c.GetConTop();
        else
        if(s==Side.BOTTOM)
            listDim=c.GetConBot();
        else
        if(s==Side.LEFT)
            listDim=c.GetConLef();
        else
            listDim=c.GetConRig();
        if(listDim.size()==0){
            for(int i = 0; i<4;i++){
                if(i==0){listDim = c.GetConTop();s=Side.TOP;}
                if(i==1){listDim=c.GetConBot();s=Side.BOTTOM;}
                if(i==2){listDim=c.GetConLef();s=Side.LEFT;}
                if(i==3){listDim=c.GetConRig();s=Side.RIGHT;}
                if(listDim.size()!=0){
                    break;
                }
            }
        }
        if(listDim.size()==0){
            System.exit(1);
        }

        Dimension d = listDim.get(0);
        listDim.remove(0);
        List<Dimension> bindPartsList = b.GetDims();
        bindPartsList.add(d);
        return s;
    }

    /**
     * Draws into map to fill bind space
     * @param d
     * @param from
     * @param to
     * @param rew
     */
    private void GenerateMapPlace(Dimension d,Side from,Side to,boolean rew){
        if(rew){this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-8);return;}
        else if((from == Side.LEFT && to == Side.RIGHT)||(from == Side.RIGHT && to == Side.LEFT)){this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-2);return;}
        else if((from == Side.TOP && to == Side.BOTTOM)||(from == Side.BOTTOM && to == Side.TOP)){this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-3);return;}
        else if((from == Side.TOP && to == Side.RIGHT)||(from == Side.RIGHT && to == Side.TOP)){this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-4);return;}
        else if((from == Side.RIGHT && to == Side.BOTTOM)||(from == Side.BOTTOM && to == Side.RIGHT)){this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-5);return;}
        else if((from == Side.LEFT && to == Side.BOTTOM)||(from == Side.BOTTOM && to == Side.LEFT)){this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-6);return;}
        else if((from == Side.TOP && to == Side.LEFT)||(from == Side.LEFT && to == Side.TOP)){this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-7);return;}
    }

    /**
     * Draws into map correct kind of connecting point
     * @param d
     * @param from
     * @param type
     */
    private void GenerateMapCon(Dimension d,Side from,int type){
        Side sides[] = {Side.LEFT,Side.TOP,Side.RIGHT,Side.BOTTOM};
        if(type == 0){
            if(from == Side.LEFT || from == Side.RIGHT)
                this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-9);
            else
                this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-10);  
        }else
        {
            for(int i=0;i<4;i++)
                if(sides[i]==from){
                    this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-11-(type-1)*4-i);
                    return;
                }
        }
    }
    /**
     * Gets remaining side from three given
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    private Side GetRemainingSide(Side s1,Side s2,Side s3){
        List<Side> s = new ArrayList<Side>();
        s.add(Side.RIGHT);
        s.add(Side.LEFT);
        s.add(Side.TOP);
        s.add(Side.BOTTOM);
        s.remove(s1);
        s.remove(s2);
        s.remove(s3);
        Side f = s.get(0);
        return f;
    }

    /**
     * Get priority of direction to go
     * @param mainDir
     * @param sideDir
     * @param currentPlace
     * @param target
     * @param from
     * @return List<Side>
     */
    private List<Side> GetPrior(Side mainDir,Side sideDir, Dimension currentPlace,Dimension target, Side from){
        List<Side> returner = new ArrayList<>();
        List<Side> sider = new ArrayList<>();
        sider.add(Side.TOP);
        sider.add(Side.BOTTOM);
        sider.add(Side.LEFT);
        sider.add(Side.RIGHT);
        sider.remove(from);

        if(mainDir == Side.TOP){
            if(currentPlace.getHeight()>target.getHeight() && sider.contains(mainDir))
                returner.add(mainDir);
        }else
        if(mainDir==Side.LEFT){
            if(currentPlace.getWidth()>target.getWidth() && sider.contains(mainDir))
                returner.add(mainDir);
        }else
        if(mainDir==Side.BOTTOM){
            if(currentPlace.getHeight()<target.getHeight() && sider.contains(mainDir))
                returner.add(mainDir);
        }else{
            if(currentPlace.getWidth()<target.getWidth() && sider.contains(mainDir))
                returner.add(mainDir);
        }

        if(sideDir == Side.TOP){
            if(currentPlace.getHeight()>target.getHeight() && sider.contains(sideDir))
                returner.add(sideDir);
        }else
        if(sideDir==Side.LEFT){
            if(currentPlace.getWidth()>target.getWidth() && sider.contains(sideDir))
                returner.add(sideDir);
        }else
        if(sideDir==Side.BOTTOM){
            if(currentPlace.getHeight()<target.getHeight() && sider.contains(sideDir))
                returner.add(sideDir);
        }else{
            if(currentPlace.getWidth()<target.getWidth() && sider.contains(sideDir))
                returner.add(sideDir);
        }

        for(Side s : returner)
            sider.remove(s);

        for(Side s: sider){
            if(s == Side.TOP){
                if(currentPlace.getHeight()>=target.getHeight())
                    returner.add(s);
            }else
            if(s==Side.LEFT){
                if(currentPlace.getWidth()>=target.getWidth())
                    returner.add(s);
            }else
            if(s==Side.BOTTOM){
                if(currentPlace.getHeight()<=target.getHeight())
                    returner.add(s);
            }else{
                if(currentPlace.getWidth()<=target.getWidth())
                    returner.add(s);
            }
        }

        for(Side s : returner)
            sider.remove(s);

        for(Side s : sider){
            returner.add(s);
        }

        return returner;
    }

    /**
     * This function is called recursivly to draw the whole bind to map
     * @param mainDir
     * @param sideDir
     * @param currentPlace
     * @param target
     * @param from
     * @param till
     * @param rew
     * @param b
     * @return boolean
     */
    private boolean FillPlace(Side mainDir,Side sideDir, Dimension currentPlace,Dimension target, Side from, Side till,boolean rew,Bind b){
        if(currentPlace.getWidth()==target.getWidth() && currentPlace.getHeight()==target.getHeight()){
            this.GenerateMapPlace(currentPlace, from, till, rew);
            b.GetDims().add(currentPlace);
            return true;
        }
        List<Side> priorityQueue = this.GetPrior(mainDir, sideDir, currentPlace, target, from);
        int i = 0;
        while(i<3){
            if(priorityQueue.get(i)==Side.TOP){
                Dimension to = new Dimension((int)currentPlace.getWidth(),(int)currentPlace.getHeight()-1);

                if(this.GetVal((int)to.getWidth(), (int)to.getHeight())==0 || this.GetVal((int)to.getWidth(), (int)to.getHeight())==-2){
                    boolean rewrite=(this.GetVal((int)to.getWidth(), (int)to.getHeight())==-2);
                    int old = this.GetVal((int)to.getWidth(), (int)to.getHeight());
                    this.GenerateMapPlace(currentPlace, from, Side.TOP, rew);
                    if(this.FillPlace(mainDir, sideDir, to, target, Side.BOTTOM, till, rewrite,b)){
                        b.GetDims().add(currentPlace);
                        return true;
                    }else
                        this.mapper.get((int)to.getWidth()).set((int)to.getHeight(), old);
                }
            }else
            if(priorityQueue.get(i)==Side.BOTTOM){
                Dimension to = new Dimension((int)currentPlace.getWidth(),(int)currentPlace.getHeight()+1);

                if(this.GetVal((int)to.getWidth(), (int)to.getHeight())==0 || this.GetVal((int)to.getWidth(), (int)to.getHeight())==-2){
                    boolean rewrite=(this.GetVal((int)to.getWidth(), (int)to.getHeight())==-2);
                    int old = this.GetVal((int)to.getWidth(), (int)to.getHeight());
                    this.GenerateMapPlace(currentPlace, from, Side.BOTTOM, rew);
                    if(this.FillPlace(mainDir, sideDir, to, target, Side.TOP, till, rewrite,b)){
                        b.GetDims().add(currentPlace);
                        return true;
                    }else
                        this.mapper.get((int)to.getWidth()).set((int)to.getHeight(), old);
                }
            }else
            if(priorityQueue.get(i)==Side.LEFT){

                Dimension to = new Dimension((int)currentPlace.getWidth()-1,(int)currentPlace.getHeight());

                if(this.GetVal((int)to.getWidth(), (int)to.getHeight())==0 || this.GetVal((int)to.getWidth(), (int)to.getHeight())==-3){
                    boolean rewrite=(this.GetVal((int)to.getWidth(), (int)to.getHeight())==-3);
                    int old = this.GetVal((int)to.getWidth(), (int)to.getHeight());
                    this.GenerateMapPlace(currentPlace, from, Side.LEFT, rew);
                    if(this.FillPlace(mainDir, sideDir, to, target, Side.RIGHT, till, rewrite,b)){
                        b.GetDims().add(currentPlace);
                        return true;
                    }else
                        this.mapper.get((int)to.getWidth()).set((int)to.getHeight(), old);
                }
            }else{

                Dimension to = new Dimension((int)currentPlace.getWidth()+1,(int)currentPlace.getHeight());

                if(this.GetVal((int)to.getWidth(), (int)to.getHeight())==0 || this.GetVal((int)to.getWidth(), (int)to.getHeight())==-3){
                    boolean rewrite=(this.GetVal((int)to.getWidth(), (int)to.getHeight())==-3);
                    int old = this.GetVal((int)to.getWidth(), (int)to.getHeight());
                    this.GenerateMapPlace(currentPlace, from, Side.RIGHT, rew);
                    if(this.FillPlace(mainDir, sideDir, to, target, Side.LEFT, till, rewrite,b)){
                        b.GetDims().add(currentPlace);
                        return true;
                    }else
                        this.mapper.get((int)to.getWidth()).set((int)to.getHeight(), old);
                }
            }
            i++;
        }


        return false;
    }

    /**
     * Gets the beggining and ending point for bind
     * @param d1
     * @param s1
     * @return
     */
    private Dimension GetStartAndTarget(Dimension d1,Side s1){
        Dimension add1=new Dimension();
        
        if(s1 == Side.TOP)
            add1.setSize(d1.width, d1.height-3);
        else if(s1==Side.LEFT)
            add1.setSize(d1.width-3, d1.height);
        else if(s1 == Side.RIGHT)
            add1.setSize(d1.width+3, d1.height);
        else
            add1.setSize(d1.width, d1.height+3);
        return add1;
    }
    /**
     * Fils the bind
     * @param b
     */
    public void FillBind(Bind b){
        Class c1 = b.GetClass1();
        Class c2 = b.GetClass2();

        int c1_x = c1.GetPosX();
        int c1_y = c1.GetPosY();
        int c2_x = c2.GetPosX();
        int c2_y = c2.GetPosY();

        int len_x;
        int len_y;
        Side c1_p1;
        Side c1_p2;
        Side mainDir;
        Side sideDir;

        Side startDir;
        Side finishDir;
        Dimension startPoint;
        Dimension endPoint;
        if(c1!=c2){
            if(c1_x < c2_x){
                len_x = c2_x - c1_x;
                c1_p1 = Side.RIGHT;
            }else{
                len_x = c1_x - c2_x;
                c1_p1 = Side.LEFT;
            }
            if(c1_y < c2_y){
                len_y = c2_y - c1_y;
                c1_p2 = Side.BOTTOM;
            }else{
                len_y = c1_y - c2_y;
                c1_p2=Side.TOP;
            }

            if(len_x > len_y){
                mainDir = c1_p1;
                sideDir = c1_p2;
            }
            else {
                mainDir=c1_p2;
                sideDir = c1_p1;    
            }
            startDir = this.FillConPoint(b,c1 ,mainDir);
            finishDir = this.FillConPoint(b,c2 ,this.Oppose(mainDir, "oppose"));
            startPoint = this.GetStartAndTarget(b.GetDims().get(0), startDir);
            endPoint = this.GetStartAndTarget(b.GetDims().get(1), finishDir);
            GenerateMapCon(b.GetDims().get(0), startDir,b.Type_Get_L());
            GenerateMapCon(b.GetDims().get(1), finishDir,b.Type_Get_R());
            boolean rewrite = (this.GetVal((int)startPoint.getWidth(), (int)startPoint.getHeight())==-3);
            if(FillPlace(mainDir, sideDir, startPoint, endPoint, this.Oppose(startDir, "oppose"), finishDir, rewrite,b)==false){
                System.out.println("lineF");
                System.exit(1);
            }
        }else{
            mainDir=Side.BOTTOM;
            sideDir=Side.LEFT;
            startDir = this.FillConPoint(b,c1 ,Side.TOP);
            finishDir = this.FillConPoint(b,c1 ,Side.LEFT);
            startPoint = this.GetStartAndTarget(b.GetDims().get(0), Side.TOP);
            endPoint = this.GetStartAndTarget(b.GetDims().get(1), Side.LEFT);
            GenerateMapCon(b.GetDims().get(0), startDir,b.Type_Get_L());
            GenerateMapCon(b.GetDims().get(1), finishDir,b.Type_Get_R());
            boolean rewrite = (this.GetVal((int)startPoint.getWidth(), (int)startPoint.getHeight())==-3);
            if(FillPlace(mainDir, sideDir, startPoint, endPoint, this.Oppose(startDir, "oppose"), this.Oppose(finishDir, "oppose"), rewrite,b)==false){
                System.out.println("lineF");
                System.exit(1);
            }
        }
    }
    /**
     * Fills all binds to map
     * @param cd
     */
    public void FillBinds(ClassDiagram cd){
        for(Bind b : cd.GetBinds()){
            this.FillBind(b);    
        }
    }

    /**
     * Removes content of bind from map
     * @param b
     */
    public void UnfillBind(Bind b){
        List<Dimension> dmList = b.GetDims();
        if(dmList.size()==0)return;
        Dimension dm = dmList.get(0);
        dmList.remove(dm);
        dm = dmList.get(0);
        dmList.remove(dm);
        for(Dimension d : dmList){
            mapper.get((int)d.getWidth()).set((int)dm.getHeight(),0);
        }
        b.ResetDims();
    }

    /**
     * Removes all binds from map
     * @param cd
     */
    public void UnfillBinds(ClassDiagram cd){
        for(Bind b : cd.GetBinds()){
            this.UnfillBind(b);
        }
    }

    /**
     * Debug function
     * @param cd
     */
    public void DebugFillCons(ClassDiagram cd){
        for(classes.Class c : cd.GetClasses()){
            c.RefillConnectors();
            for(Dimension d : c.GetConTop()){
                this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-2);
            }
            for(Dimension d : c.GetConBot()){
                this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-2);
            }
            for(Dimension d : c.GetConLef()){
                this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-2);
            }
            for(Dimension d : c.GetConRig()){
                this.mapper.get((int)d.getWidth()).set((int)d.getHeight(),-2);
            }
        }
    }

    /**
     * Debug function
     * @return
     */
    public Pane DebugMap(){
        Pane rPane = new Pane();
        for(int i=0;i<this.mapper.size();i++){
            for(int j=0;j<this.mapper.get(i).size();j++){
                int x = this.mapper.get(i).get(j);
                    Pane newPane = new Pane();
                    newPane.setPrefHeight(5);
                    newPane.setPrefWidth(5);
                    newPane.setLayoutX(Converts.ToWorldCoordinatesX(i));
                    newPane.setLayoutY(Converts.ToWorldCoordinatesY(j));
                    if(x==0)
                        newPane.setStyle("-fx-background-color: #34eb43");
                    else if(x==-1){
                        newPane.setStyle("-fx-background-color: #ebae34");
                    }
                    else if(x>0){
                        newPane.setStyle("-fx-background-color: #eb3434");
                    }else if(x==-5){
                        newPane.setStyle("-fx-background-color: #ffffff");
                    }
                    else{
                        newPane.setStyle("-fx-background-color: #000000");
                    }
                    rPane.getChildren().add(newPane);
            }
        }
    
        return rPane;
    }
    
    public Mapper(int dimX,int dimY,ClassDiagram cd){
        this.LoadMap(dimX-300, dimY);
        this.FillAll(cd);
    }
    
}