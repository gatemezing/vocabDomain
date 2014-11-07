from __future__ import print_function
from alchemyapi import AlchemyAPI
from rdflib import Graph, plugin, Namespace
from rdflib.parser import Parser
from rdflib.serializer import Serializer
from rdflib import Graph, RDF
#from rdflib.namespace import VOAF
import json

from gensim import corpora, models, similarities
from gensim.models import hdpmodel, ldamodel
from itertools import izip



def findTaxonomy(text):
	alchemyapi = AlchemyAPI()
	response = alchemyapi.taxonomy('text', text)
	return response

#def findCategory(text):
#	alchemyapi = AlchemyAPI()
#	response = alchemyapi.category('text', text)
#	return response


if __name__=='__main__':
	 # read graph in turtle.
	g = Graph()

    
	g.load("vocabsLOV-titles-20141106.ttl", format="turtle")
	#g.parse(data=n3, format='n3')
	tfile=g.serialize(format='turtle')

	VOAF = Namespace('http://purl.org/vocommons/voaf#')
	DCTERMS = Namespace('http://purl.org/dc/terms/')

	data = {'results':[]}
	for vocab in g.subjects(RDF.type, VOAF["Vocabulary"]):
		for desc in g.objects(vocab, DCTERMS["description"]):
			# launch alchemy API with the term
			#print desc
			datum = {'alchemytaxonomies': []
					 }
			datum['uri'] = vocab
			datum['description'] = desc # desc is dcterms:title + dcterms:description in English
			outputConcept = findConcept(desc) # call concept tagging
			#outputTaxonomy = findTaxonomy(desc) # call taxonomy classification

			#outputCategory = findCategory(desc) # call category classification 

			if outputTaxonomy['status'] =='OK':
				print('## Object Taxonomy API ##')
				print('')
				print(json.dumps(outputTaxonomy, indent=4))
				#datum['alchemytaxonomies'].append(outputTaxonomy)
				for category in outputTaxonomy['taxonomy']:
					taxo = {}
					taxo['label'] = category['label']
					taxo['score'] = category['score']
					datum['alchemytaxonomies'].append(taxo)

			else:
				print('Error in taxonomy call: ', outputTaxonomy['statusInfo'])


		data['results'].append(datum)

	#print tfile # find a way to insert the result into the Database of a json file
	# or json file to easily query it { "uri": "lien", "text": "letexte", tagging":[{}, {}]}




	f = open("outputAlchemyTaxonomies.json", "w")
	f.write(json.dumps(data, sort_keys=True, indent=4))
	f.close()





	