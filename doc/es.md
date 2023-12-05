## 语法示例
```shell
http.request.method: *

http.request.method: GET

http.request.body.content: "null pointer"

http.response.bytes > 10000 and http.response.bytes <= 20000

@timestamp < now-2w

http.response.status_code: 4*

http.request.method: GET OR http.response.status_code: 400

(http.request.method: GET AND http.response.status_code: 200) OR
(http.request.method: POST AND http.response.status_code: 400)

datastream.*: logs

user.names:{ first: "Alice" and last: "White" }
```

## es|ql
```
POST /_query?format=txt
{
 "query" : "FROM test | limit 1"
}

  FROM employees
| EVAL hired_year = TO_INTEGER(DATE_FORMAT(hire_date, "YYYY"))
| WHERE hired_year > 1984
| STATS avg_salary = AVG(salary) BY languages
| EVAL avg_salary = ROUND(avg_salary)
| EVAL lang_code = TO_STRING(languages)
| ENRICH languages_policy ON lang_code WITH lang = language_name
| WHERE NOT IS_NULL(lang)
| KEEP avg_salary, lang
| SORT avg_salary ASC
| LIMIT 3



FROM kibana_sample_data_ecommerce
| WHERE   products.product_name LIKE "Basic*" 
| LIMIT 30
| SORT products.created_on DESC

from test | where description like "香蕉*"


from test | where description like "香蕉*"|stats count_distinct(title)

# 支持以下聚合函数：
AVG COUNT COUNT_DISTINCT MAX MEDIAN MEDIAN_ABSOLUTE_DEVIATION MIN PERCENTILE
```


## es 查询语句

```shell
# 查询索引
GET /_cat/indices?v

# 新增索引
PUT /products

# 删除索引
DELETE /products
```



```shell
# 创建索引 & 映射

# 创建商品索引 products，指定 mapping {id, title, price, created_at, description}
PUT /products
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0
  },
  "mappings": {
    "properties": {
      "id": {
        "type": "integer"
      },
      "title": {
        "type": "keyword"
      },
      "price": {
        "type": "double"
      },
      "created_at": {
        "type": "date",
        "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
      },
      "description": {
        "type": "text"
      }
    }
  }
}

# 查看映射

GET /products/_mapping

# 添加文档并指定 id
POST /products/_doc/1
{
  "id": 1,
  "title": "苹果",
  "price": 4.8,
  "created_at": "2022-09-07",
  "description": "苹果真好吃，真好吃"
}
```

```shell
# 添加文档
POST /products/_doc
{
  "title": "香蕉",
  "price": 3.6,
  "created_at": "2022-09-07",
  "description": "香蕉真好吃，真好吃"
}

# 文档查询，基于 id
GET /products/_doc/1
# 删除文档，基于 id
DELETE /products/_doc/1
# 更新文档
POST /products/_update/1
{
  "doc": {
    "price": 4.5
  }
}

# 下面是一种 PUT 更新方式，会丢弃掉未传递的属性，慎用

PUT /products/_doc/1
{
  "price": 4.5
}
# 批量添加
POST /products/_bulk
{"index":{"_id":2}}
{"id":2,"title":"香蕉","price":3.6,"created_at":"2022-09-07","description":"香蕉真好吃，真好吃"}
# 批量添加，不指定 id
POST /products/_bulk
{"index":{}}
{"id":1024,"title":"猕猴桃","price":3.6,"created_at":"2022-09-07","description":"猕猴桃真好吃，真好吃"}
# 批量添加、更新、删除
POST /products/_bulk
{"index":{"_id":3}}
{"id":3,"title":"橘子","price":3.6,"created_at":"2022-09-07","description":"橘子真好吃，真好吃"}
{"update":{"_id":1}}
{"doc":{"title":"红富士苹果"}}
{"delete":{"_id":2}}
```


```shell
# 查询所有（match_all）
GET /products/_search
{
  "query": {
    "match_all": {}
  }
}
# 关键词查询（term）
# text 类型默认使用 es 标准分词，中文单字分词，英文单词分词，其他类型不分词

GET /products/_search
{
  "query": {
    "term": {
      "description": {
        "value": "吃"
      }
    }
  }
}

# 范围查询（range）
# 查询价格指定区间内的记录

GET /products/_search
{
  "query": {
    "range": {
      "price": {
        "gte": 3.6,
        "lte": 5
      }
    }
  }
}
# 查询创建时间在指定时间段内的记录 gte：大于等于 lte：小于等于 gt：大于 lt：小于

GET /products/_search
{
  "query": {
    "range": {
      "created_at": {
        "gte": "2022-09-07 16:30:57",
        "lte": "2022-09-07 16:31:08"
      }
    }
  }
}
# 前缀查询（prefix）
GET /products/_search
{
  "query": {
    "prefix": {
      "title": {
        "value": "火龙"
      }
    }
  }
}
# 通配符查询（wildcard）
GET /products/_search
{
  "query": {
    "wildcard": {
      "description": {
        "value": "goo?"
      }
    }
  }
}
# ids 查询（ids）
GET /products/_search
{
  "query": {
    "ids": {
      "values": ["1", "2", "4"]
    }
  }
}
# 模糊查询（fuzzy）

# 搜索关键词长度为 2，不允许存在模糊，无匹配查询结果
GET /products/_search
{
  "query": {
    "fuzzy": {
      "title": "红烧"
    }
  }
}

# 搜索关键词长度为 2，全字匹配，匹配到查询结果
GET /products/_search
{
  "query": {
    "fuzzy": {
      "title": "香蕉"
    }
  }
}

# 关键词长度为 4，允许 1 次模糊，匹配到查询结果
GET /products/_search
{
  "query": {
    "fuzzy": {
      "title": "小鱼豆豆"
    }
  }
}

# 出现两次模糊，无匹配查询结果
GET /products/_search
{
  "query": {
    "fuzzy": {
      "title": "小鱼小豆"
    }
  }
}

# 关键词长度为 6，出现两次模糊，匹配到查询结果
GET /products/_search
{
  "query": {
    "fuzzy": {
      "title": "红烧排骨小小"
    }
  }
}

# 超过两次模糊，无匹配查询结果
GET /products/_search
{
  "query": {
    "fuzzy": {
      "title": "红烧排小小小"
    }
  }
}

# 只能查出苹果
GET /products/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "ids": {
            "values": [1, 2]
          }
        },
        {
          "term": {
            "title": {
              "value": "苹果"
            }
          }
        }
      ]
    }
  }
}

# 不存在 id 为 1 或 2，同时标题为猕猴桃的商品，无匹配查询结果
GET /products/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "ids": {
            "values": [1, 2]
          }
        },
        {
          "term": {
            "title": {
              "value": "猕猴桃"
            }
          }
        }
      ]
    }
  }
}
# 查询出 id 为 1 或 2，以及标题为猕猴桃的所有记录，合计三条
GET /products/_search
{
  "query": {
    "bool": {
      "should": [
        {
          "ids": {
            "values": [1, 2]
          }
        },
        {
          "term": {
            "title": {
              "value": "猕猴桃"
            }
          }
        }
      ]
    }
  }
}

# 查询出 id 不为 1、2，同时标题不为猕猴桃的所有记录
GET /products/_search
{
  "query": {
    "bool": {
      "must_not": [
        {
          "ids": {
            "values": [1, 2]
          }
        },
        {
          "term": {
            "title": {
              "value": "猕猴桃"
            }
          }
        }
      ]
    }
  }
}

# title 字段是 keyword 类型，不分词，description 字段是 text，会做分词，“火果” 被分为 “火” 和 “果” 然后做分词查询，故可以返回苹果和火龙果两条记录
GET /products/_search
{
  "query": {
    "multi_match": {
      "query": "火果",
      "fields": ["title", "description"]
    }
  }
}
```

