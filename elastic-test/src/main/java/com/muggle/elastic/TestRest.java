package com.muggle.elastic;

import org.elasticsearch.action.IndicesRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.ToXContentObject;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.AbstractBulkIndexByScrollRequest;
import org.elasticsearch.tasks.TaskId;

import java.io.IOException;

/**
 * Description
 * Date 2021/8/31
 * Created by muggle
 */
public class TestRest extends AbstractBulkIndexByScrollRequest<TestRest> implements IndicesRequest.Replaceable, ToXContentObject {

    private String pipeline;

    public TestRest() {
        this(new SearchRequest());
    }

    public TestRest(String... indices) {
        this(new SearchRequest(indices));
    }

    TestRest(SearchRequest search) {
        this(search, true);
    }

    public TestRest(StreamInput in) throws IOException {
        super(in);
        this.pipeline = in.readOptionalString();
    }

    private TestRest(SearchRequest search, boolean setDefaults) {
        super(search, setDefaults);
    }

    public TestRest setPipeline(String pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public TestRest setQuery(QueryBuilder query) {
        if (query != null) {
            this.getSearchRequest().source().query(query);
        }

        return this;
    }

    /** @deprecated */
    @Deprecated
    public TestRest setDocTypes(String... types) {
        if (types != null) {
            this.getSearchRequest().types(types);
        }

        return this;
    }

    public TestRest setRouting(String routing) {
        if (routing != null) {
            this.getSearchRequest().routing(routing);
        }

        return this;
    }

    public TestRest setBatchSize(int size) {
        this.getSearchRequest().source().size(size);
        return this;
    }

    public TestRest setIndicesOptions(IndicesOptions indicesOptions) {
        this.getSearchRequest().indicesOptions(indicesOptions);
        return this;
    }

    public int getBatchSize() {
        return this.getSearchRequest().source().size();
    }

    public String getRouting() {
        return this.getSearchRequest().routing();
    }

    /** @deprecated */
    @Deprecated
    public String[] getDocTypes() {
        return this.getSearchRequest().types() != null ? this.getSearchRequest().types() : new String[0];
    }

    public String getPipeline() {
        return this.pipeline;
    }

    protected TestRest self() {
        return this;
    }

    public TestRest forSlice(TaskId slicingTask, SearchRequest slice, int totalSlices) {
        TestRest request = (TestRest)this.doForSlice(new TestRest(slice, false), slicingTask, totalSlices);
        request.setPipeline(this.pipeline);
        return request;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("update-by-query ");
        this.searchToString(b);
        return b.toString();
    }

    public IndicesRequest indices(String... indices) {
        assert this.getSearchRequest() != null;

        this.getSearchRequest().indices(indices);
        return this;
    }

    public String[] indices() {
        assert this.getSearchRequest() != null;

        return this.getSearchRequest().indices();
    }

    public IndicesOptions indicesOptions() {
        assert this.getSearchRequest() != null;

        return this.getSearchRequest().indicesOptions();
    }

    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeOptionalString(this.pipeline);
    }

    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        if (this.getScript() != null) {
            builder.field("script");
            this.getScript().toXContent(builder, params);
        }

        this.getSearchRequest().source().innerToXContent(builder, params);
        builder.endObject();
        return builder;
    }
}
