/*
 * MultiHttpSolrClientFactory.java
 * Copyright 2016 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package org.springframework.data.solr.server.support;

import org.apache.http.auth.Credentials;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author alei
 */
public class MultiHttpSolrClientFactory extends SolrClientFactoryBase {

    private Credentials credentials;
    private String authPolicy;

    private Map<String, SolrClient> clientMap = new ConcurrentHashMap<String, SolrClient>();

    protected MultiHttpSolrClientFactory() {

    }

    public MultiHttpSolrClientFactory(SolrClient solrClient) {
        this(solrClient, null, null, null);
    }


    public MultiHttpSolrClientFactory(SolrClient solrClient, String core) {
        this(solrClient, core, null, null);
    }

    public MultiHttpSolrClientFactory(SolrClient solrClient, String core, Credentials credentials, String authPolicy) {
        super(solrClient);
        if (authPolicy != null) {
            Assert.hasText(authPolicy);
        }

        this.credentials = credentials;
        this.authPolicy = authPolicy;

        if (StringUtils.hasText(core)) {
            ((CloudSolrClient) solrClient).setDefaultCollection(core);
        }
        appendAuthentication(this.credentials, this.authPolicy, this.getSolrClient());
    }

    @Override
    public SolrClient getSolrClient(final String core) {
        if (!StringUtils.hasText(core)) {
            return this.getSolrClient();
        }
        if (!clientMap.containsKey(core)) {
            clientMap.put(core, new SolrClientWrapper(core, this.getSolrClient()));
        }
        return clientMap.get(core);
    }

    @Override
    public List<String> getCores() {
        return new ArrayList<String>(clientMap.keySet());
    }

    private void appendAuthentication(Credentials credentials, String authPolicy, SolrClient solrClient) {
        // no-op
    }

}
