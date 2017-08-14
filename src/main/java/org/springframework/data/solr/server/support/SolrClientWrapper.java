/*
 * SolrClientWrapper.java
 * Copyright 2017 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package org.springframework.data.solr.server.support;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author alei
 */
public class SolrClientWrapper extends SolrClient {

    private final String collection;
    private final SolrClient delegate;

    public SolrClientWrapper(String collection,
                             SolrClient delegate) {
        this.collection = collection;
        this.delegate = delegate;
    }

    @Override
    public NamedList<Object> request(final SolrRequest request, final String collection) throws SolrServerException, IOException {
        return delegate.request(request, collection);
    }

    @Override
    public QueryResponse query(SolrParams params) throws SolrServerException, IOException {
        return delegate.query(collection, params);
    }

    @Override
    public QueryResponse query(SolrParams params, SolrRequest.METHOD method) throws SolrServerException, IOException {
        return delegate.query(collection, params);
    }

    @Override
    public UpdateResponse add(SolrInputDocument doc, int commitWithinMs) throws SolrServerException, IOException {
        return delegate.add(collection, doc, commitWithinMs);
    }

    @Override
    public UpdateResponse commit() throws SolrServerException, IOException {
        return delegate.commit(collection, true, true);
    }

    @Override
    public UpdateResponse deleteByQuery(String query) throws SolrServerException, IOException {
        return delegate.deleteByQuery(collection, query);
    }

    public UpdateResponse deleteById(String id) throws SolrServerException, IOException {
        return deleteById(collection, id);
    }

    @Override
    public UpdateResponse add(Iterator<SolrInputDocument> docIterator) throws SolrServerException, IOException {
        return delegate.add(collection, docIterator);
    }

    @Override
    public UpdateResponse add(Collection<SolrInputDocument> docs, int commitWithinMs) throws SolrServerException, IOException {
        return delegate.add(collection, docs, commitWithinMs);
    }

    @Override
    public UpdateResponse add(Collection<SolrInputDocument> docs) throws SolrServerException, IOException {
        return delegate.add(collection, docs);
    }

    @Override
    public UpdateResponse deleteById(List<String> ids) throws SolrServerException, IOException {
        return delegate.deleteById(collection, ids);
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }
}
