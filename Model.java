import java.util.ArrayList;
import java.awt.*;

public class Model
{
	Controller control;
	View view;
	ArrayList<Citation> sources;
	//ArrayList<views> views;

	public Model()
	{
		sources = new ArrayList<Citation>();
		control = new Controller(this);
		view = new MainMenuView(control);
		view.setVisible(true);
	}

	public static void main(String[] args)
	{
		Model model = new Model();
	}

	public void viewSet(View v){
		view = v;
	}

	public void updateViews(){
		view.update(sources);
	}
}