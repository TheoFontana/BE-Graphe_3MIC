package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
protected ShortestPathSolution doRun() {
    	
        final ShortestPathData data = getInputData(); //Récupération des données
        ShortestPathSolution solution = null; //Chemin final
        Graph graph = data.getGraph(); // Récupération du Graphe
        List<Node> nodes = graph.getNodes(); // Récupération des Noeuds
        BinaryHeap<Label> heap = new BinaryHeap<Label>(); // Création du tas de labels
        LabelStar[] labels = new LabelStar[graph.size()]; // Création des labels  
        Node début = data.getOrigin(); // Origine du chemin
        
        //Initialisation 
        for (Node node : nodes) 
        {
        	labels[node.getId()] = new LabelStar(node,false,Float.POSITIVE_INFINITY,null,data.getDestination());
        }
        labels[début.getId()].setCost(0);
        heap.insert(labels[début.getId()]);
        
        notifyOriginProcessed(début);

        //Boucle de traitement 
        while(!heap.isEmpty()&& labels[data.getDestination().getId()].isCheck() == false) 
        { 
        	Label label = heap.deleteMin(); // label en cours de traitement
        	Node noeud = label.getNode(); // noeud en cours de traitement 
        	label.check(); // on marque le label
        	
			for (Arc arc : noeud.getSuccessors()) 
			{		
		        
		        Node noeud_dest = arc.getDestination(); 
		        Label label_dest = labels[noeud_dest.getId()]; 
		        double cost = data.getCost(arc) + label.getCost();
		        
		        if (!label_dest.isCheck() && label_dest.getCost()>cost) // Le noeud n'a pas été visité
		        {
		        	if (label_dest.getPere()==null)
		        	{ // Noeud jamais rencontré
			        	label_dest.setCost(cost);
			            label_dest.setPere(arc);
			            heap.insert(label_dest);
			            notifyNodeReached(noeud_dest);
			        }	
		        	else 
		        	{
		        		heap.remove(label_dest);
			        	label_dest.setCost(cost);
			            label_dest.setPere(arc);
			            heap.insert(label_dest);
			            notifyNodeReached(noeud_dest);
		        	}
		        }    
	        }	
        }
        
        Node noeud_fin = data.getDestination();
        LabelStar label_fin = labels[noeud_fin.getId()];
        
        
        if (label_fin.getPere()== null) 
        { 
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        else 
        {
            notifyDestinationReached(noeud_fin);
            
            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = label_fin.getPere();
            
            while (arc != null) 
            {
                arcs.add(arc);
                label_fin = labels[arc.getOrigin().getId()];
                arc = label_fin.getPere();
            }
            
            // On retourne le chemin 
            Collections.reverse(arcs);
           
            Path path= new Path(graph, arcs);
            
            // On vérifie que ces valeurs soient bien égales
            System.out.println("Longueur obtenue avec la méthode de la classe path : "+ path.getLength());
            System.out.println("Longueur obtenur par le cout de la destination : " +labels[data.getDestination().getId()].getCost());
            
            // On vérifie que le chemin trouvé est bien valide
            if(path.isValid()) 
            {
            	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
            	System.out.println("Chemin valide");
            }
            else 
            {
            	solution = null;
            	System.out.println("Chemin non valide");
            }
            
            
        }
            
        return solution;
        
        
        
    }

}
