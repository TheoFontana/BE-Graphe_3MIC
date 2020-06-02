package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label{
	
	private double estimedCost;
	
	public LabelStar(Node s, boolean v, double c, Arc p, Node dest) {
		super(s, v, c, p);
		this.estimedCost =s.getPoint().distanceTo(dest.getPoint());
	}
	
	public double  getTotalCost(){
		return this.getCost() +this.estimedCost;
	}	
}