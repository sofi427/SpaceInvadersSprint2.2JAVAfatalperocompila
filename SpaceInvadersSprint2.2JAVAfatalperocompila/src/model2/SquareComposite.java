package model2;

import java.util.ArrayList;

public class SquareComposite implements Component {
	private ArrayList<Component> component = new ArrayList<Component>();
	
	public SquareComposite() {}
	
	public void addComponent(Component pComp) {
		component.add(pComp);
	}
	
	@Override
	public void move() {
		for (int i=0; i<component.size(); i++) {
			component.get(i).move();
		}
	}
	
}
