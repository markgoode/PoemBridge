//Mark Goode EE422C TA:Ben 2:00

package assignment3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Vertex {

    private String name;
    private Map<String, Integer> edges;

    public Vertex(String namePassed){
        name = namePassed;
        edges = new HashMap<>();
    }

    public void addEdge(String next, int weight)
    {
        edges.put(next, weight);
    }

    public String getName(){
        return name;
    }

    public boolean checkWeightExists(String next)
    {
        if(edges.size() == 0)
            return false;
        else
            return edges.containsKey(next);
    }

    public void incrementWeight(String key)
    {
        edges.put(key, edges.get(key) + 1);
    }

    public Set<String> getVertexKeys()
    {
       Set<String> keys =  edges.keySet();
       return keys;
    }

    public int getWeight(String key)
    {
        int weight = 0;
        weight = edges.get(key);
        return weight;
    }




}
