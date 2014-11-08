import json
from pprint import pprint
use_sparql = True

from SPARQLWrapper import SPARQLWrapper, JSON
import sys
import argparse

## read the json file

results = {"categories": []}

with open('outputAlchemydataset.json') as json_data:
    d = json.load(json_data)
    json_data.close()
    #pprint(d["results"][0])
   
    for index in range(len(d["results"])):
    	uri = d["results"][index]["uri"]
    	lov = d["results"][index]["LOV_category"]
    	datum = {"vocab": uri, "LOVcategory": lov ,
    			 "skoscategory": []}

    	for uridbpedia in d["results"][index]["alchemyconcepts"]:
    		resource = uridbpedia["dbpedia"]

    		if use_sparql:
    			sparql = SPARQLWrapper("http://dbpedia.org/sparql")
    			sparql.setQuery("""
	select ?category <LONG::IRI_RANK> (?category) AS ?rank {
<"""+resource+"""> dcterms:subject ?category } ORDER BY DESC ( <LONG::IRI_RANK> (?category) )
	 

		""")

    			sparql.setReturnFormat(JSON)
    			categories = sparql.query().convert()
    			tcat = []
    			for cat in categories["results"]["bindings"]:
    				t = cat['category']['value']
    				tcat.append(t)
    				#for i in range(len(tcat)) print tcat[index]	
    				datum["skoscategory"]= tcat[0]   # only taken into account the maximum

    	results["categories"].append(datum)

with open("skosCategories.json", "w") as f:
		f.write(json.dumps(results, sort_keys=True, indent=4))
			
 