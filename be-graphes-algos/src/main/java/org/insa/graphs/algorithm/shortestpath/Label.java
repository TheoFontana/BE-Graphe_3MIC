package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	
	private Node sommet;
	private boolean vu; //vrai si le sommet a été vu
	private double cost;
	private Arc pere;
	
	//Constructeur
	public Label(Node s, boolean v, double c,  Arc p) {
		this.sommet = s;
		this.vu = v;
		this.cost = c;
		this.pere =p;
	}
	
	
	public void setPere(Arc p) {
		this.pere = p;
	}
	
	public Arc getPere() {
		return this.pere;
	}
	
	public void check() {
		this.vu = true;
	}
	
	public boolean isCheck() {
		return this.vu;
	}
	
	public void setCost(double c) {
		this.cost = c;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public double getTotalCost() {
		return this.cost;
	}
	
	public int compareTo(Label other) {
        return Double.compare( getTotalCost(),other.getTotalCost() );
    }
	
	public Node getNode() {
		return this.sommet;
	}
}
