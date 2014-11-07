from gensim import corpora, models, similarities
from gensim.models import hdpmodel, ldamodel
from itertools import izip

from alchemyapi import AlchemyAPI
from rdflib import Graph, plugin, Namespace
from rdflib.parser import Parser
from rdflib.serializer import Serializer
from rdflib import Graph, RDF
#from rdflib.namespace import VOAF
import json

#clustering the list of concepts
# output of Alchemy


## documents here are descriptions of ontologies
#######


### document here is the list of concepts to cluster ###
################

documents=["2000s music groups", "Breed Groups", "Group",
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

# remove common words and tokenize
stoplist = set('for a of the and to in or is are can by an as from that with used their it this on into describes provides be allows describing describe such defines integrated designed'.split())
texts = [[word for word in document.lower().split() if word not in stoplist]
         for document in documents]

# remove words that appear only once
all_tokens = sum(texts, [])
tokens_once = set(word for word in set(all_tokens) if all_tokens.count(word) == 1)
texts = [[word for word in text if word not in tokens_once]
         for text in texts]

dictionary = corpora.Dictionary(texts)
corpus = [dictionary.doc2bow(text) for text in texts]

# I can print out the topics for LSA
lsi = models.LsiModel(corpus, id2word=dictionary, num_topics=10)
corpus_lsi = lsi[corpus]

#for l,t in izip(corpus_lsi,corpus):
#  print l,"#",t
#print
#for top in lsi.print_topics(2):
#  print top

# I can print out the documents and which is the most probable topics for each doc.
lda = ldamodel.LdaModel(corpus, id2word=dictionary, num_topics=10)
corpus_lda = lda[corpus]

#for l,t in izip(corpus_lda,corpus):
#  print l,"#",t
#print

# But I am unable to print out the topics, how should i do it?
for top in lda.show_topics(topics=10, formatted=False, topn=5):
  print top

#i = 0
# We print the topics
#for topic in lda.show_topics(topics=10, formatted=False, topn=10):
# i = i + 1
# print "Topic #" + str(i) + ":",
# for p, id in topic:
#     print dictionary[int(id)],

# print ""