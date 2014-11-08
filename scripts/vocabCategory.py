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


def makeuri(concept): 
	var = concept
	if len(concept.split( )) > 1 :
		var = "_".join(concept.split( ))

	base = "http://dbpedia.org/resource/"
	return base + str(var)


def findConcept(text):
	# Create the AlchemyAPI Object
	alchemyapi = AlchemyAPI()
	#print('############################################')
	#print('#   Concept Tagging retrieval               #')
	#print('############################################')
	#print('')
	print('')

	print('Processing text: ', text)
	print('')

	response = alchemyapi.concepts('text', text)
	return response

def findTaxonomy(text):
	alchemyapi = AlchemyAPI()
	response = alchemyapi.taxonomy('text', text)
	return response

def findCategory(text):
	alchemyapi = AlchemyAPI()
	response = alchemyapi.category('text', text)
	return response


if __name__=='__main__':
	 # read graph in turtle.
	g = Graph()

    
	g.load("vocabsDomainLOV.ttl", format="turtle")
	#g.parse(data=n3, format='n3')
	tfile=g.serialize(format='turtle')

	VOAF = Namespace('http://purl.org/vocommons/voaf#')
	DCTERMS = Namespace('http://purl.org/dc/terms/')
	FRAPO = Namespace('http://purl.org/cerif/frapo/')

	data = {'results':[]}
	for vocab in g.subjects(RDF.type, VOAF["Vocabulary"]):
		for desc in g.objects(vocab, DCTERMS["description"]):
			# launch alchemy API with the term
			#print desc
			datum = {'alchemyconcepts': [],
					 'alchemytaxonomies': []}
			datum['uri'] = vocab
			datum['description'] = desc # desc is dcterms:title + dcterms:description in English
			for domain in g.objects(vocab, FRAPO["hasDomainName"]):  ## adding vocab domain
						datum['LOV_category'] = domain 

			outputConcept = findConcept(desc) # call concept tagging
			outputTaxonomy = findTaxonomy(desc) # call taxonomy classification

			#outputCategory = findCategory(desc) # call category classification 

			if outputConcept['status'] =='OK':
				print('## Object Concept Tagging ##')
				print(json.dumps(outputConcept, indent=4))
				#print('')
				#print('## Concepts')
				#item = {}
				#datum['alchemyconcepts'].append(outputConcept) 
				for concept in outputConcept['concepts']:
					#print('text: ', concept['text'])
					item = {}
					item['text'] = concept['text']
					item['relevance'] = concept['relevance']
					dbpediauri = makeuri(concept['text']) ## creating DBpedia-URI alike
					item['dbpedia'] = dbpediauri
					
					
					
					datum['alchemyconcepts'].append(item)
					#print('relevance: ', concept['relevance'])
					#print('')


			else:
				print('Error in concept tagging call: ', outputConcept['statusInfo'])

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




	f = open("outputAlchemydataset.json", "w")
	f.write(json.dumps(data, sort_keys=True, indent=4))
	f.close()





	