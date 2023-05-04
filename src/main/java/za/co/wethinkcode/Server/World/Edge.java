package za.co.wethinkcode.Server.World;

public class Edge extends World{
int x;
int y;

public Edge(int x, int y){
    this.x = x;
    this.y = y;
}

//public static List<Edge> AddTolistOfEdges(){
//    listOfEdges.add( new Edge())
//    return null;
//}
public int getEdgeX(){
    return this.x;
}
public int getEdgeY(){
    return this.y;
}
}
