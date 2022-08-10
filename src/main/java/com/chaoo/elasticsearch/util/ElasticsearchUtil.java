package com.chaoo.elasticsearch.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.indices.*;
import com.chaoo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author chaoo
 * @Date: 2022/07/30/ 20:51
 */
@Component
public class ElasticsearchUtil {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /**
     * 创建索引
     *
     * @param indexName
     *         索引名称
     * @return 如果返回 True 表示创建成功
     */
    public Boolean createIndex(String indexName) {
        try {
            ElasticsearchIndicesClient indices = elasticsearchClient.indices();

            ExistsRequest existsRequest = new ExistsRequest.Builder()
                    .index(indexName)
                    .build();
            Boolean flag = indices.exists(existsRequest).value();
            if (flag) {
                throw new RuntimeException("你要创建的索引 " + indexName + " 已经存在,不能重复创建");
            }

            CreateIndexRequest request = new CreateIndexRequest.Builder()
                    .index(indexName)
                    .build();
            CreateIndexResponse response = indices.create(request);
            return response.acknowledged();
        } catch (IOException e) {
            throw new RuntimeException("创建索引出错,错误信息为:" + e.getMessage());
        }
    }

    /**
     * 查询索引
     *
     * @param indexName
     *         索引名称
     * @return 返回索引信息
     */
    public IndexState getIndex(String indexName) {
        try {
            ElasticsearchIndicesClient indices = elasticsearchClient.indices();
            GetIndexRequest request = new GetIndexRequest.Builder()
                    .index(indexName)
                    .build();
            GetIndexResponse response = indices.get(request);
            return response.get(indexName);
        } catch (IOException e) {
            throw new RuntimeException("查询索引 " + indexName + " 出错,出错信息为 " + e.getMessage());
        }
    }

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     */
    public boolean deleteIndex(String indexName) {
        try {
            ElasticsearchIndicesClient indices = elasticsearchClient.indices();
            DeleteIndexRequest request = new DeleteIndexRequest.Builder()
                    .index(indexName)
                    .build();
            DeleteIndexResponse response = indices.delete(request);
            return response.acknowledged();
        } catch (IOException e) {
            throw new RuntimeException("删除索引 " + indexName + " 出错,出错信息为 " + e.getMessage());
        }
    }

    /**
     * 添加文档
     *
     * @param indexName
     *         索引名称
     * @param user
     *         添加文档数据
     * @return
     */
    public String createDocument(String indexName, User user) {
        try {
            // 创建 CreateRequest 对象 通过这个方法创建对象
            CreateRequest<User> request = new CreateRequest.Builder<User>()
                    .id(user.getId().toString()) // 文档标识 需要文艺
                    .index(indexName)
                    .document(user)
                    .build();
            CreateResponse respose = elasticsearchClient.create(request);
            return respose.result().jsonValue();
        } catch (IOException e) {
            throw new RuntimeException("添加文档,索引 " + indexName + " 出错,出错信息为 " + e.getMessage());
        }
    }

    /**
     * 批量添加文档
     *
     * @param indexName
     * @param users
     * @return
     */
    public boolean bulkCreatDocument(String indexName, List<User> users) {
        try {
            ArrayList<BulkOperation> list = new ArrayList<>();
            for (User user : users) {
                CreateOperation<User> opt = new CreateOperation.Builder<User>()
                        .id(user.getId().toString())
                        .index(indexName)
                        .document(user)
                        .build();

                BulkOperation operation = new BulkOperation.Builder()
                        .create(opt)
                        .build();
                list.add(operation);
            }

            BulkRequest request = new BulkRequest.Builder()
                    .operations(list)
                    .build();
            BulkResponse bulk = elasticsearchClient.bulk(request);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("批量添加文档 ,索引" + indexName + " 出错,出错信息为 " + e.getMessage());
        }
    }

    /**
     * 查询全部文档
     *
     * @param indexName
     * @param clazz
     */
    public <T> List<Hit<T>> searchDocument(String indexName, Class<T> clazz) {
        /**
         * 这个地方我进行 matchAll 查询 默认 10 条 所以要进行 _search { "size" : 20 }查询
         */
        try {
            MatchAllQuery matchAllQuery = new MatchAllQuery.Builder().build();
            Query query = new Query.Builder()
                    .matchAll(matchAllQuery)
                    .build();

            SearchRequest request = new SearchRequest.Builder()
                    .index(indexName)
                    .query(query)
                    .build();
            SearchResponse<T> response = elasticsearchClient.search(request, clazz);
            // 真正的数据数量
            long size = response.hits().total().value();
            // 再次进行查询 通过数据大小
            request = new SearchRequest.Builder()
                    .index(indexName)
                    .size((int) size)
                    .build();
            response = elasticsearchClient.search(request, clazz);
            return response.hits().hits();
        } catch (IOException e) {
            throw new RuntimeException("查询全部文档,索引 " + indexName + " 出错,出错信息为 " + e.getMessage());
        }
    }


    /**
     * 查询指定文档
     *
     * @param indexName
     * @param clazz
     * @param fieldName
     * @param fieldValue
     * @param <T>
     * @return
     */
    public <T> List<Hit<T>> queryByIdDocument(String indexName, Class<T> clazz, String fieldName, Object fieldValue) {
        try {

            MatchQuery matchQuery = new MatchQuery.Builder()
                    .field(fieldName)
                    .query(fieldValue.toString())
                    .build();
            Query query = new Query.Builder().match(matchQuery).build();

            SearchRequest request = new SearchRequest.Builder()
                    .index(indexName)
                    .query(query)
                    .build();
            SearchResponse<T> response = elasticsearchClient.search(request, clazz);
            return response.hits().hits();

        } catch (IOException e) {
            throw new RuntimeException("查询单个文档,索引 " + indexName + " 出错,出错信息为 " + e.getMessage());

        }
    }


    /**
     * 删除指定文档
     *
     * @param indexName
     * @param documentID
     * @return
     */
    public String deleteDocument(String indexName, String documentID) {
        try {
            DeleteRequest request = new DeleteRequest.Builder()
                    .index(indexName)
                    .id(documentID)
                    .build();
            DeleteResponse response = elasticsearchClient.delete(request);
            return response.result().jsonValue();
        } catch (IOException e) {
            throw new RuntimeException("删除指定文档,索引 " + indexName + " 出错,出错信息为 " + e.getMessage());

        }
    }


}
