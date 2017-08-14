/*
 * RoutingSolrClientFactory.java
 * Copyright 2016 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package org.springframework.data.solr.server.support;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.data.solr.server.SolrClientFactory;
import org.springframework.util.Assert;

import java.util.List;

import static org.springframework.data.solr.server.support.SolrClientUtils.isCloudSolrClient;
import static org.springframework.data.solr.server.support.SolrClientUtils.isHttpSolrClient;

/**
 * @author alei
 */
public class RoutingSolrClientFactory implements SolrClientFactory {

    private final SolrClientFactory delegate;

    public RoutingSolrClientFactory(final SolrClient solrClient) {
        this(solrClient, null, false);
    }

    public RoutingSolrClientFactory(final SolrClient solrClient,
                                    final String core) {
        this(solrClient, core, false);
    }

    public RoutingSolrClientFactory(final SolrClient solrClient,
                                    final boolean multicoreSupport) {
        this(solrClient, null, multicoreSupport);
    }

    public RoutingSolrClientFactory(final SolrClient solrClient,
                                    final String core,
                                    final boolean multicoreSupport) {
        Assert.notNull(solrClient, "SolrClient must not be null");

        if (isCloudSolrClient(solrClient)) {
            delegate = new CloudSolrClientFactory(solrClient, core);
        } else if (multicoreSupport && isHttpSolrClient(solrClient)) {
            delegate = new MultiHttpSolrClientFactory(solrClient, core);
        } else {
            delegate = new HttpSolrClientFactory(solrClient, core);
        }
    }

    @Override
    public SolrClient getSolrClient() {
        return delegate.getSolrClient();
    }

    @Override
    public SolrClient getSolrClient(final String core) {
        return delegate.getSolrClient(core);
    }

    @Override
    public List<String> getCores() {
        return delegate.getCores();
    }
}
