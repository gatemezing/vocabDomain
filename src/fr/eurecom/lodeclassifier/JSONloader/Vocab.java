package fr.eurecom.lodeclassifier.JSONloader;

import java.util.List;

public class Vocab {
	String LOV_category; 
	List <alchemyconcept> alchemyconcepts;
	List <alchemytaxonomy> alchemytaxonomies;
	String description;
	String uri;
	public String getLOV_category() {
		return LOV_category;
	}
	public void setLOV_category(String lOV_category) {
		LOV_category = lOV_category;
	}
	public List<alchemyconcept> getAlchemyconcepts() {
		return alchemyconcepts;
	}
	public void setAlchemyconcepts(List<alchemyconcept> alchemyconcepts) {
		this.alchemyconcepts = alchemyconcepts;
	}
	public List<alchemytaxonomy> getAlchemytaxonomies() {
		return alchemytaxonomies;
	}
	public void setAlchemytaxonomies(List<alchemytaxonomy> alchemytaxonomies) {
		this.alchemytaxonomies = alchemytaxonomies;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	
	
	/*
		"LOV_category": "E-Business", 
		"alchemyconcepts": [
		    {
		        "dbpedia": "http://dbpedia.org/resource/Marketing", 
		        "relevance": "0.9477", 
		        "text": "Marketing"
		    }, 
		    {
		        "dbpedia": "http://dbpedia.org/resource/Electronic_commerce", 
		        "relevance": "0.920562", 
		        "text": "Electronic commerce"
		    }, 
		    {
		        "dbpedia": "http://dbpedia.org/resource/Business-to-business", 
		        "relevance": "0.6942", 
		        "text": "Business-to-business"
		    }
		], 
		"alchemytaxonomies": [
		    {
		        "label": "/business and industrial", 
		        "score": "0.671023"
		    }, 
		    {
		        "label": "/hobbies and interests/reading", 
		        "score": "0.310446"
		    }, 
		    {
		        "label": "/society", 
		        "score": "0.220584"
		    }
		], 
		"description": "Business to Business Ontology. A vocabulary for documents interchange in B2B processes.", 
		"uri": "http://purl.org/b2bo#"
	 */


}

class alchemytaxonomy{
	String label;
	String score;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
}

class alchemyconcept{
	String dbpedia;
	Double relevance;
	String text;
	public String getDbpedia() {
		return dbpedia;
	}
	public void setDbpedia(String dbpedia) {
		this.dbpedia = dbpedia;
	}
	public Double getRelevance() {
		return relevance;
	}
	public void setRelevance(Double relevance) {
		this.relevance = relevance;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}


}

