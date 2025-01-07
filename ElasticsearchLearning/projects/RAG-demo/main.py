from elasticsearch import Elasticsearch
from openai import OpenAI

es = Elasticsearch("http://113.45.129.200:9200", request_timeout=30)
print(es.info())

llm_client = OpenAI(api_key='sk-ad43441046cb4343a80972b9dd0668cd', base_url='https://dashscope.aliyuncs.com/compatible-mode/v1')

text = "Hello, ElasticSearch"
tmp = llm_client.embeddings.create(input=text,  model='text-embedding-v3')
print(tmp.data[0].embedding, len(tmp.data[0].embedding))

# 向量数据存入ElasticSearch
## 构建索引
mappings = {
    "mappings": {
        "properties": {
            "semantic": {
                "type": "dense_vector",
                "dims": 1024
            },
            "content": {
                "type": "text"
            }
        }
    }
}
es.indices.create(index='rag_vector_index_zt', body=mappings)

## 向量入库
def generate_docs(documents):
    index_name = 'rag_vector_index_zt'
    result = []
    for row in documents:
        text = row['text']
        text_vector = row['text_vector']
        doc = {'content': text, 'semantic': text_vector}
        result.append({'index': {'_index': index_name}})
        result.append(doc)
    return result
documents = [{'text': 'Hello, ElasticSearch', 'text_vector': llm_client.embeddings.create(input='Hello, ElasticSearch', model='text-embedding-v3').data[0].embedding},
             {'text': 'Hello, Kafka', 'text_vector': llm_client.embeddings.create(input='Hello, Kafka', model='text-embedding-v3').data[0].embedding}]
es.bulk(body=generate_docs(documents))

# 检索
user_text = 'ElasticSearch'
user_text_vector = llm_client.embeddings.create(input=text, model='text-embedding-v3').data[0].embedding
query = {
    'knn': {
        'field': 'semantic',
        'query_vector': user_text_vector,
        'num_candidates': 1
    }
}
result = es.search(index='rag_vector_index_zt', body={'query': query})
print(result)


