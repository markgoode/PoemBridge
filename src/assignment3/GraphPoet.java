//Mark Goode EE422C TA:Ben 2:00

package assignment3;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;

public class GraphPoet {


    Map<String, Vertex> vertices = new HashMap<>();
    List<String> words = new ArrayList<String>();
    List<String> prepoem = new ArrayList<String>();


    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    public GraphPoet(File corpus) throws IOException {

        /* Read in the File and place into graph here */

        Scanner input = new Scanner(corpus);
       while (input.hasNext()) {
           String before = input.next();
           String after = before.toLowerCase();

           words.add(after);
       }

        String current;

        for(int i = 0; i<words.size(); i++)
        {
           current = words.get(i);
            if(!vertices.containsKey(current))                                  // add vertex if doesnt match any. if does, skip
            {
                vertices.put(current, new Vertex(current));
            }

            if(i+1<words.size()) {                                              // check if another word after before adds edge

                if (vertices.get(current).checkWeightExists(words.get(i + 1)))                     // check if edge already exists for specific vertex
                {
                    vertices.get(current).incrementWeight(words.get(i+1));
                }
                else{
                    vertices.get(current).addEdge(words.get(i+1), 1);         // add edge with next word if doesn't exist
                }
            }


        }


    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {

        /* Read in input and use graph to complete poem */
        Map<String, Integer> potentialBridge = new HashMap<>();
        Set <String> keys;
        Iterator<String> itr;
        Map<String, String> punctuation = new HashMap<>();

        String now;
        String following;
        int currentWeight;
        int nextWeight;
        int weight;

        Scanner scan = new Scanner(input);
        while (scan.hasNext()) {
            String before = scan.next();
            String after = before.toLowerCase();
            String afterp  = after.replaceAll("\\p{Punct}+$", "");
            if(!after.equals(afterp))
            {
                punctuation.put(afterp, after);
            }
            prepoem.add(afterp);
        }

        for(int i =0; i<prepoem.size()-1; i++)                                 // size-1 to know that there is always a word after to check for bridge
        {


            now = prepoem.get(i);                                              // current word in poem

            if(vertices.containsKey(now))
            {

                following = prepoem.get(i + 1);
                keys = vertices.get(now).getVertexKeys();                      // get all following words for that vertex
                itr = keys.iterator();
                while(itr.hasNext()) {                                         // checking all edge words of current vertex, gets the edge word's vertex
                    String key = itr.next();
                   if( vertices.get(key).checkWeightExists(following))               // checks the new vertex to see if it contains the next word in the poem as an edge. If so, it's a valid bridge
                   {
                       currentWeight = vertices.get(now).getWeight(key);
                       nextWeight = vertices.get(key).getWeight(following);
                       weight = currentWeight + nextWeight;
                       potentialBridge.put(key, weight);
                   }

                }
            }

            if(potentialBridge.size() != 0) {

                Map.Entry<String, Integer> maxEntry = null;                                             // get bridge word with max weight, add it to poem

                for (Map.Entry<String, Integer> entry : potentialBridge.entrySet()) {
                    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                        maxEntry = entry;
                    }
                }

                prepoem.add(i + 1, maxEntry.getKey());
                i = i+1;                                                                                // so doesn't run through loop next on bridged word
                potentialBridge.clear();
            }
        }

        for(int i =0; i<prepoem.size(); i++)
        {
            if(punctuation.containsKey(prepoem.get(i)))
            {
                prepoem.set(i, punctuation.get(prepoem.get(i)));
            }
        }
            String poem = String.join(" ", prepoem);

        return poem;
    }

}
