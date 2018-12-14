import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Controller
{
	//variable declaration
	Model model;

	public Controller(){
		model = null;
	}

	public Controller(Model m){
		model = m;
	}

	//notifies model of changed view
	public void viewSwitch(View v){
		model.viewSet(v);
		model.updateViews();
	}

	//adds book citation info from view to model
	public void addBook(String lName, String fName, String title, String publisher, String city, String state, String year,
	String dateAccessed, String vol, String edition, String series, String totalVol)
	{
		model.sources.add(new BookCitation(fName, lName, title, publisher, city, state, year, dateAccessed, vol, edition, series, totalVol));
		model.updateViews();
	}

	//adds website citation info from view to model
	public void addWebsite(String fName, String lName, String title, String publisher, String year, String dateAccessed, String url, String publishDay,
		String publishMonth, String websiteTitle)
	{
		model.sources.add(new WebsiteCitation(fName, lName, title, publisher, year, dateAccessed, url, publishDay, publishMonth, websiteTitle));
		model.updateViews();
	}

	//calls model to update the view and add the current source list
	public void getSources()
	{
		model.updateViews();
	}

	//Saves sources to project file
	public void saveProject(File fileName) throws IOException{
		FileWriter out = null;
		try{
			out = new FileWriter(fileName);
			for (int i=0; i<model.sources.size(); i++){
				model.sources.get(i).saveSource(out);
			}
		}
		finally{
			out.close();
		}
		model.updateViews();
	}

	//Loads sources from project file
	public void loadProject(File fileName) throws IOException
	{
			FileReader in = null;
			Scanner s = null;
			int sourceType = 0;
			try{
				in = new FileReader(fileName);
				s = new Scanner(in);
				s.useDelimiter("&&");

				model.sources = new ArrayList<Citation>();

				while(s.hasNext()){
					String stringSource = s.next();
					sourceType = Integer.parseInt(stringSource);
					if (sourceType == 1)
						model.sources.add(new BookCitation());
					else if (sourceType == 2)
						model.sources.add(new WebsiteCitation());
					model.sources.get(model.sources.size()-1).loadSource(s);
				}
			}
			finally{
				in.close();
				s.close();
			}
			model.updateViews();
	}
}