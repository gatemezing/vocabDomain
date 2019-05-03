vocabDomain
===========

A repository for experiments to detect automatically the domain of a vocabulary using NER tools and machine learning.

Updates of the evolution of the study can be found at https://docs.google.com/document/d/1EoBkyT5c1lZ6iie6TapXmFL-j-zyg3GNYOFgH_wMeyY/edit?usp=sharing.


Calling spotlight
=================

<pre>
curl http://spotlight.sztaki.hu:2222/rest/annotate \
  --data-urlencode "text=Linked Statistical Models Vocabulary. A Vocabulary for Incorporating Predictive Models into the Linked Data Web." \
  --data "confidence=0.2" \
  --data "support=20" -H "Accept:application/json"
</pre>

Related Work
============
  - Automatic Domain Identification for Linked Open Data (http://daselab.cs.wright.edu/pub/domainIdentLOD13.pdf) 
  - Meusel, R., Spahiu, B., Bizer, C. & Paulheim, H. (2015). Towards Automatic Topical Classification of LOD Datasets

TODO
====
  - [ ] Look at Cosine similarity of the descriptions 
  - [ ] Find how to compare the results of the classifier with the experts of the domain
  - [ ] Test using DBpedia spotlight in the descriptions
  - [ ] Write a paper for any venue when we have some good results whatever the technique
