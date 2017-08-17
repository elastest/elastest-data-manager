# make sure ES is up and running
import requests
res = requests.get('http://localhost:9201')
print(res.content)

#connect to our cluster
from elasticsearch import Elasticsearch
es = Elasticsearch([{'host': 'localhost', 'port': 9201}])

#index some test data
es.index(index='python-test-index', doc_type='test', id=1, body={'test': 'test'})



# from datetime import datetime
# from elasticsearch import Elasticsearch
# es = Elasticsearch()

# doc = {
#     'author': 'kimchy',
#     'text': 'Elasticsearch: cool. bonsai cool.',
#     'timestamp': datetime.now(),
# }
# res = es.index(index="test-index", doc_type='tweet', id=1, body=doc)
# print(res['created'])

# res = es.get(index="test-index", doc_type='tweet', id=1)
# print(res['_source'])

# es.indices.refresh(index="test-index")

# res = es.search(index="test-index", body={"query": {"match_all": {}}})
# print("Got %d Hits:" % res['hits']['total'])
# for hit in res['hits']['hits']:
#     print("%(timestamp)s %(author)s: %(text)s" % hit["_source"])