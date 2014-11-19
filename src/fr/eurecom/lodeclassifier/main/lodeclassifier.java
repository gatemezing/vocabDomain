package fr.eurecom.lodeclassifier.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Random;

import fr.eurecom.lodeclassifier.JSONloader.JSON_Reader;
import fr.eurecom.lodeclassifier.distance.VocabSimpleDistance;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.M5P;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.lazy.IBk;


import weka.core.ChebyshevDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.NormalizableDistance;
import weka.core.converters.ArffSaver;

public class lodeclassifier {

	/**
	 * @param args
	 */
	static String 	lov_dataset_json = 	"dataset/outputAlchemydataset.json";

	public static void main(String[] args) {
		System.out.println("Starting LODE Classifier...");
	
		try {
			
			System.out.println("Reading data...");
			
			//Instances data = loadExampleWeka();
			JSON_Reader converter = new JSON_Reader(lov_dataset_json);
			Instances data2 = converter.createDatasetUnfoldPlain();

		
			
			saveToArff(data2, "dataset/outputAlchemydataset.arff");
			
			System.out.println ("Filtering data");
			
			
			
			System.out.println("Training classifier...");

			IBk tree = new IBk();         // new instance of tree
			//tree.setOptions(options);     // set the options
			VocabSimpleDistance df = new VocabSimpleDistance();
			df.setInstances(data2);
			tree.getNearestNeighbourSearchAlgorithm().setDistanceFunction(df);

			
			System.out.println("Evaluation...");
			Evaluation eval = new Evaluation(data2);
			eval.crossValidateModel(tree, data2, 10, new Random(1));

			
			System.out.println("Printing results...");
			System.out.println(eval.toSummaryString());

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private static void saveToArff(Instances data2, String path) throws IOException {
		 ArffSaver saver = new ArffSaver();
		 saver.setInstances(data2);
		 saver.setFile(new File(path));
		 saver.setDestination(new File(path));   // **not** necessary in 3.5.4 and later
		 saver.writeBatch();
		
	}

	private static Instances loadExampleWeka() throws IOException {
		BufferedReader reader = new BufferedReader(
                new FileReader("dataset/diabetes.arff"));
		Instances data = new Instances(reader);

		int cIdx=data.numAttributes()-1;
		data.setClassIndex(cIdx);
		
		printDataset(data);
		reader.close();

		return data;
	}

	private static void printDataset(Instances data) {
		ListIterator<Instance> iterator = data.listIterator();
		while (iterator.hasNext()){
			String line = "";

			Instance instance = iterator.next();
			for (int i = 0; i < instance.numAttributes(); i++){
				line = line + instance.value(i) + ",";
			}
			System.out.println(line);
		}
	}

}
