package fr.eurecom.lodeclassifier.JSONloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class JSON_Reader {
	
	File f = null;
	String pathout = "dataset/lov-dataset.arff"; 
	
	public JSON_Reader (String pathin, String pathout){
		f = new File (pathin);
		this.pathout = pathout;
	}
	
	public JSON_Reader (String pathin){
		f = new File (pathin);
	}
	
	public Instances createDatasetPack() throws FileNotFoundException{
		
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

		
		Gson gson = new Gson();
		Type listtype = new TypeToken<jsonFormat>() {}.getType();
		jsonFormat vocabs = gson.fromJson(bufferedReader,listtype);
		System.out.println("\t#Vocabs available:" + vocabs.getResults().size());
		
		List <String> categories = generateCategories (vocabs);
		

		 ArrayList <Attribute> atts = new ArrayList <Attribute>();

		 // Declare two string attributes
		 Attribute alchemy_taxonomies = new Attribute("alchemy_taxonomies",(ArrayList <String>) null);
		 atts.add(alchemy_taxonomies);
		 Attribute alchemy_concepts = new Attribute("alchemy_concepts", (ArrayList <String>) null);
		 atts.add(alchemy_concepts);
		 
		 
		 // Creating Class.
		 ArrayList <String> category_att_Val = new ArrayList <String>(categories.size());
		 for (String cat : categories){
			 category_att_Val.add(cat);
		 }
		 Attribute category_att = new Attribute("lov_category", category_att_Val);
		 atts.add(category_att);
		 
		Instances data = new Instances("Lov_dataset", atts, 0);           
		
		for (Vocab voc :vocabs.getResults()){
			String concepts_text = "";
			for (alchemyconcept concept : voc.getAlchemyconcepts()){
				concepts_text = concepts_text + concept.getText() + "#";
			}
			if (!concepts_text.equals(""))concepts_text = concepts_text.substring(0, concepts_text.length() -1);
			
			String taxonomies_text = "";
			for (alchemytaxonomy taxonomy : voc.getAlchemytaxonomies()){
				taxonomies_text = taxonomies_text + taxonomy.getLabel() + "#";
			}
			if (!taxonomies_text.equals(""))taxonomies_text = taxonomies_text.substring(0, taxonomies_text.length() -1);
			
			double [] values = new double[data.numAttributes()];
			
			values[0] = data.attribute(0).addStringValue(taxonomies_text);
			values[1] = data.attribute(1).addStringValue(concepts_text);

			values[2] = data.attribute(2).indexOfValue(voc.getLOV_category());

			Instance inst = new DenseInstance(1.0, values);
			data.add(inst);
		}

		data.setClassIndex(2);
		return data;
	}


	public Instances createDatasetUnfoldPlain() throws FileNotFoundException{
		
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

		
		Gson gson = new Gson();
		Type listtype = new TypeToken<jsonFormat>() {}.getType();
		jsonFormat vocabs = gson.fromJson(bufferedReader,listtype);
		System.out.println("\t#Vocabs available:" + vocabs.getResults().size());
		
		List <String> categories = generateCategories (vocabs);
		

		 ArrayList <Attribute> atts = new ArrayList <Attribute>();

		 // Declare  string attributes
		 Attribute alchemy_taxonomies = new Attribute("alchemy_taxonomies",(ArrayList <String>) null);
		 atts.add(alchemy_taxonomies);
		 Attribute alchemy_taxonomies2 = new Attribute("alchemy_taxonomies2",(ArrayList <String>) null);
		 atts.add(alchemy_taxonomies2);
		 Attribute alchemy_taxonomies3 = new Attribute("alchemy_taxonomies3",(ArrayList <String>) null);
		 atts.add(alchemy_taxonomies3);
		 
		 Attribute alchemy_concepts = new Attribute("alchemy_concepts", (ArrayList <String>) null);
		 atts.add(alchemy_concepts);
		 Attribute alchemy_concepts2 = new Attribute("alchemy_concepts2", (ArrayList <String>) null);
		 atts.add(alchemy_concepts2);
		 Attribute alchemy_concepts3 = new Attribute("alchemy_concepts3", (ArrayList <String>) null);
		 atts.add(alchemy_concepts3);
		 
		 
		 // Creating Class.
		 ArrayList <String> category_att_Val = new ArrayList <String>(categories.size());
		 for (String cat : categories){
			 category_att_Val.add(cat);
		 }
		 Attribute category_att = new Attribute("lov_category", category_att_Val);
		 atts.add(category_att);
		 
		Instances data = new Instances("Lov_dataset", atts, 0);           
		
		for (Vocab voc :vocabs.getResults()){
			
			String taxonomy1 = "";
			if (voc.getAlchemytaxonomies().size() > 0) taxonomy1 = voc.getAlchemytaxonomies().get(0).getLabel();
			String taxonomy2 = "";
			if (voc.getAlchemytaxonomies().size() > 1) taxonomy2 = voc.getAlchemytaxonomies().get(1).getLabel();
			String taxonomy3 = "";
			if (voc.getAlchemytaxonomies().size() > 2) taxonomy3 = voc.getAlchemytaxonomies().get(2).getLabel();
				
			String concept1 = "";
			if (voc.getAlchemyconcepts().size() > 0) concept1 = voc.getAlchemyconcepts().get(0).getText();
			String concept2 = "";
			if (voc.getAlchemyconcepts().size() > 1) concept2 = voc.getAlchemyconcepts().get(1).getText();
			String concept3 = "";
			if (voc.getAlchemyconcepts().size() > 2) concept3 = voc.getAlchemyconcepts().get(2).getText();
			
			
			double [] values = new double[data.numAttributes()];
			
			values[0] = data.attribute(0).addStringValue(taxonomy1);
			values[1] = data.attribute(1).addStringValue(taxonomy2);
			values[2] = data.attribute(2).addStringValue(taxonomy3);

			values[3] = data.attribute(3).addStringValue(concept1);
			values[4] = data.attribute(4).addStringValue(concept2);
			values[5] = data.attribute(5).addStringValue(concept3);
			
			values[6] = data.attribute(6).indexOfValue(voc.getLOV_category());
			
			
			Instance inst = new DenseInstance(1.0, values);
			data.add(inst);
		}

		data.setClassIndex(6);
		return data;
	}

	
	public Instances createDatasetUnfoldPlainSmall() throws FileNotFoundException{
	
	BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

	
	Gson gson = new Gson();
	Type listtype = new TypeToken<jsonFormat>() {}.getType();
	jsonFormat vocabs = gson.fromJson(bufferedReader,listtype);
	System.out.println("\t#Vocabs available:" + vocabs.getResults().size());
	
	List <String> categories = generateCategories (vocabs);
	

	 ArrayList <Attribute> atts = new ArrayList <Attribute>();

	 // Declare  string attributes
	 Attribute alchemy_taxonomies = new Attribute("alchemy_taxonomies",(ArrayList <String>) null);
	 atts.add(alchemy_taxonomies);

	 
	 
	 // Creating Class.
	 ArrayList <String> category_att_Val = new ArrayList <String>(categories.size());
	 for (String cat : categories){
		 category_att_Val.add(cat);
	 }
	 Attribute category_att = new Attribute("lov_category", category_att_Val);
	 atts.add(category_att);
	 
	Instances data = new Instances("Lov_dataset", atts, 0);           
	
	for (Vocab voc :vocabs.getResults()){
		
		String taxonomy1 = "";
		if (voc.getAlchemytaxonomies().size() > 0) taxonomy1 = voc.getAlchemytaxonomies().get(0).getLabel();
		
		String concept1 = "";
		if (voc.getAlchemyconcepts().size() > 0) concept1 = voc.getAlchemyconcepts().get(0).getText();

		
		
		double [] values = new double[data.numAttributes()];
		
		values[0] = data.attribute(0).addStringValue(taxonomy1);

		
		values[1] = data.attribute(1).indexOfValue(voc.getLOV_category());
		
		
		Instance inst = new DenseInstance(1.0, values);
		data.add(inst);
	}

	data.setClassIndex(1);
	return data;
	
	
}
public Instances createDatasetUnfold() throws FileNotFoundException{
		
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

		
		Gson gson = new Gson();
		Type listtype = new TypeToken<jsonFormat>() {}.getType();
		jsonFormat vocabs = gson.fromJson(bufferedReader,listtype);
		System.out.println("\t#Vocabs available:" + vocabs.getResults().size());
		
		List <String> categories = generateCategories (vocabs);
		

		 ArrayList <Attribute> atts = new ArrayList <Attribute>();

		 // Declare two string attributes
		 
		 ArrayList<Attribute> atts_rel_taxonomy = new ArrayList<Attribute>();
		 atts_rel_taxonomy.add(new Attribute("label",(ArrayList <String>) null));
	     Instances rel_struct_tax = new Instances("taxonomies", atts_rel_taxonomy, 0);
	     Attribute relational_taxonomies = new Attribute("alchemy_taxonomies", rel_struct_tax);
		 atts.add(relational_taxonomies);
		 
		 ArrayList<Attribute> atts_rel_concept = new ArrayList<Attribute>();
		 atts_rel_concept.add(new Attribute("text",(ArrayList <String>) null));
	     Instances rel_struct_con = new Instances("concepts", atts_rel_concept, 0);
	     Attribute relational_concepts = new Attribute("alchemy_concepts", rel_struct_con);
		 atts.add(relational_concepts);
		 
		 
		 // Creating Class.
		 ArrayList <String> category_att_Val = new ArrayList <String>(categories.size());
		 for (String cat : categories){
			 category_att_Val.add(cat);
		 }
		 Attribute category_att = new Attribute("lov_category", category_att_Val);
		 atts.add(category_att);
		 
		Instances data = new Instances("Lov_dataset", atts, 0);           
		
		for (Vocab voc :vocabs.getResults()){

			Instances data_taxonomies = new Instances(data.attribute(0).relation(),0);
			for (alchemytaxonomy taxonomy : voc.getAlchemytaxonomies()){
			     double [] values_rel_tax = new double[data_taxonomies.numAttributes()];
			     values_rel_tax[0] = data.attribute(0).relation().attribute(0).addStringValue(taxonomy.getLabel());;
			     data_taxonomies.add(new DenseInstance(1.0, values_rel_tax));
			}

			Instances data_concepts = new Instances(data.attribute(1).relation(),0);
			for (alchemyconcept concept : voc.getAlchemyconcepts()){
			     double [] values_rel_concept = new double[data_concepts.numAttributes()];
			     values_rel_concept[0] = data.attribute(1).relation().attribute(0).addStringValue(concept.getText());;
			     data_concepts.add(new DenseInstance(1.0, values_rel_concept));
			}



			
			double [] values = new double[data.numAttributes()];

			values[0] = data.attribute(0).addRelation(data_taxonomies);
			values[1] = data.attribute(1).addRelation(data_concepts);
			values[2] = data.attribute(2).indexOfValue(voc.getLOV_category());


			Instance inst = new DenseInstance(1.0, values);
			data.add(inst);
		}

		data.setClassIndex(2);
		return data;
	}
	private List<String> generateCategories(jsonFormat vocabs) {

		HashMap <String, Integer> lov_categories = new HashMap <String, Integer> ();

		for (Vocab v: vocabs.getResults()){
			String category = v.getLOV_category();
			if (lov_categories.containsKey(category)){
				lov_categories.put(category, lov_categories.get(category)+1);
			}
			else{
				lov_categories.put(category, 1);
			}
		}
		
		
		List<Entry<String, Integer>> ordered_categories = new ArrayList<Entry<String, Integer>> (lov_categories.entrySet());
		Collections.sort(ordered_categories, new CategoriesComparator());

		List <String> categories = new ArrayList <String> ();
		for (Entry<String, Integer> categoryEntry : ordered_categories){
			//System.out.println(categoryEntry.getKey() +",  "+ categoryEntry.getValue());
			categories.add(categoryEntry.getKey());
		}
		
		return categories;
	}

}


class jsonFormat{
	List<Vocab> results;

	public List<Vocab> getResults() {
		return results;
	}

	public void setResults(List<Vocab> results) {
		this.results = results;
	}
	
	
}

class CategoriesComparator implements Comparator<Entry<String,Integer>> {
	 

	public int compare(Entry<String,Integer> e1, Entry<String,Integer> e2) {
		return (int) (e2.getValue() - e1.getValue());

	}
}

