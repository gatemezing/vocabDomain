#! /usr/bin/python
# -*- coding: utf-8 -*-

use_sparql = True

from SPARQLWrapper import SPARQLWrapper, JSON
import json
import sys
import argparse

# --- liste des classes et individus - requete 1 ---

def preprocess(concept):
	var = concept.split( )
	if len(var) > 1 :
		var = "_".join(var)

	return var 

def makeuri(concept): 
	var = concept
	if len(concept.split( )) > 1 :
		var = "_".join(concept.split( ))

	base = "http://dbpedia.org/resource/"
	return base + str(var)
	 


def findCategory(resource): 
	if use_sparql:
		sparql = SPARQLWrapper("http://dbpedia.org/sparql") #"http://dbpedia.org/sparql"
		sparql.setQuery("""	
	select ?category <LONG::IRI_RANK> (?category) AS ?rank {
<"""+resource+"""> dcterms:subject ?category } ORDER BY DESC ( <LONG::IRI_RANK> (?category) )
	 

		""")
		sparql.setReturnFormat(JSON)
		cats = sparql.query().convert()

	tcat = []
	for cat in cats["results"]["bindings"]:
		uri = cat['category']['value']
		tcat.append(uri)
		
	#print tcat
		
	return 	tcat[0]
		

		#new_classes['children'].append(item)

	#with open("classesd3.json", "w") as f:
		#f.write(json.dumps(new_classes, sort_keys=True, indent=4))


categories = []
#concepts = ["Semantic Web", "Web Ontology Language", "Time", "Website", "Being", "Vocabulary", "Metaphysics", "Semantics", "Resource Description Framework", "Concept"]
concepts = ["2000s music groups", "Breed Groups", "Group",
"Concept", "Concepts in metaphysics", "Mathematical terminology", "Measure word", "Transitive verb", "Description", "Trait", "The Core",
"Data",  "Data set", "Data management", "Asset",
"Metadata", "Geospatial metadata",
"Region", "United Kingdom", "The Domain Sydney", "British Empire",
"Configuration Management", "Content management system", "Crisis management", "Design management", "Project management", "Knowledge management", "Information technology management", "Project",
"Agent", "Bobby Hutcherson", "Legal person", "Lawrence Lessig", "Mary I of England",
"Website", "Web page", "Web service", "Web server", "Internet", "hyperlink", "Link", "World Wide Web", "2G", "Information architecture",
"Mathematics", "Geodesy", "Statistics", "Metaphysics", "Numerical analysis", "Science", "Sociology", "Qualitative research", "Official statistics", "Geometry",
"Personality psychology", "Rhetoric", "Substance theory", "Philosophy of life", "Semiotics", "Philosophy of science",
"Observation", "Transformation", "Distribution", "Review", "Means of Production", "Workflow",
"Java", "Oject-oriented programming", "Unified Modelling Language", "German language", "Computer program", "Wiki software", "Object", "Web Ontology Language", "Natural language processing", "SQL", "First-class function",
"Comma-separated values", "Syntax", "XML schema", "Abstract syntax tree",
"License", "Certificate", "Access control", "Cryptography", "GNU Lesser General Public License",
"Library", "Citation", "Biography", "Open content", "Open Archives Initiative", "Functional Requirements for Bibliographic Records", "Type system", "Index", "Review",
"Marketing", "Offers and acceptance", "Communication", "Technical Communication", "Consumer electronics", "Call for bids",
"Identifiers", "Code", "Uniform Resource Identifier",
"Geologic time scale", "Calendar", "Prehistory", "Coordinate Universal Time", "Time",
"Ordnance Survey", "Nature Publishing Group", "Yahoo!", "Dublin Core", "ISO 3166-1", "Volkswagen", "Civil service",
"Game", "Sport",
"Semantics", "Ontology", "Controlled vocabulary", "Best Practice", "Semantic Web", "Interoperability", "Design Pattern",
"Quality", "Units of measurement", "Measurement", "Surveying", 
"Everyday Life", "Being", "Mind", "Alcoholic beverage", "Preference", "Relationship",
"The Hydra", "Tonic", "Lens", "Renewable energy", "Species", "Pytheas"]
for c in concepts:
	
	item = {}
	item['concept'] = c
	try:
		curi = makeuri(c)
		item['dbpedia'] = curi
	except:
		pass
	
	try:
		catego = findCategory(curi)
		item['category'] = catego
	except:
		pass
	
	
	
	categories.append(item)


#print categories
with open("categories-dbpedia.json","w") as f:
	f.write(json.dumps(categories, sort_keys=True, indent=4))

		

			