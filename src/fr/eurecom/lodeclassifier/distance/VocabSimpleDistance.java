package fr.eurecom.lodeclassifier.distance;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.DistanceFunction;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.neighboursearch.PerformanceStats;
import weka.core.NormalizableDistance;


public class VocabSimpleDistance extends NormalizableDistance {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Enumeration<Option> listOptions() {
		// TODO Auto-generated method stub
		return super.listOptions();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
	}

	@Override
	public String[] getOptions() {
		return super.getOptions();
	}

	@Override
	public void setInstances(Instances insts) {
		super.setInstances(insts);
	}

	@Override
	public Instances getInstances() {
		return super.getInstances();
	}

	@Override
	public void setAttributeIndices(String value) {
		super.setAttributeIndices(value);		
	}

	@Override
	public String getAttributeIndices() {
		return super.getAttributeIndices();
	}

	@Override
	public void setInvertSelection(boolean value) {
		super.setInvertSelection(value);
		
	}

	@Override
	public boolean getInvertSelection() {
		// TODO Auto-generated method stub
		return super.getInvertSelection();
	}

	@Override
	public double distance(Instance first, Instance second) {
				
		String tax1 =  first.stringValue( first.dataset().attribute(0));
		String tax2 =  second.stringValue( second.dataset().attribute(0));



		double similarity = LetterPairSimilarity.compareStrings(tax1, tax2);
		
		return similarity;
		
	}

	@Override
	public double distance(Instance first, Instance second,
			PerformanceStats stats) {
		String tax1 =  first.stringValue( first.dataset().attribute(0));
		String tax2 =  second.stringValue( second.dataset().attribute(0));


		double similarity = LetterPairSimilarity.compareStrings(tax1, tax2);
	
		return similarity;
	}

	@Override
	public double distance(Instance first, Instance second, double cutOffValue) {
		String tax1 =  first.stringValue( first.dataset().attribute(0));
		String tax2 =  second.stringValue( second.dataset().attribute(0));

		

		
		double similarity = LetterPairSimilarity.compareStrings(tax1, tax2);
		
		return similarity;
	}

	@Override
	public double distance(Instance first, Instance second, double cutOffValue,
			PerformanceStats stats) {
		String tax1 =  first.stringValue( first.dataset().attribute(1));
		String tax2 =  second.stringValue( second.dataset().attribute(1));

		
		System.out.println("UNO:" + tax1);
		System.out.println("DOS:" + tax2);
		
		
		double similarity = LetterPairSimilarity.compareStrings(tax1, tax2);
		
		System.out.println("DISTANCE:" + similarity);

		return 1.0 - similarity;
	}

	@Override
	public void postProcessDistances(double[] distances) {
		super.postProcessDistances(distances);
	}

	@Override
	public void update(Instance ins) {
		// TODO Auto-generated method stub
		super.update(ins);
	}

	@Override
	public void clean() {
		super.clean();
		
	}

	@Override
	public String getRevision() {
		// TODO Auto-generated method stub
		return "0.1";
	}

	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return "Global INFO";
	}

	@Override
	protected double updateDistance(double currDist, double diff) {
		// TODO Auto-generated method stub
		return currDist + diff;
	}



}
