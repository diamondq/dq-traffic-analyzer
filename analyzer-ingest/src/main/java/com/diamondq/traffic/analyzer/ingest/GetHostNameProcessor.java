package com.diamondq.traffic.analyzer.ingest;

import com.diamondq.common.lambda.future.ExtendedCompletableFuture;
import com.diamondq.common.storage.kv.IKVStore;
import com.diamondq.common.storage.kv.TransactionUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.inject.Inject;

import org.javatuples.Pair;

public class GetHostNameProcessor {

	static final String		sCACHE_TABLE	= "ipaddress";

	private final IKVStore	mKVStore;

	@Inject
	public GetHostNameProcessor(IKVStore pKVStore) {
		mKVStore = pKVStore;
	}

	public <CONTEXT> ExtendedCompletableFuture<Pair<String, CONTEXT>> process(String pIPAddress, CONTEXT pContext) {

		return TransactionUtil.runInTransaction(mKVStore, (t) -> {

			/* First step is to check the database to see if we've already got information about this IP address */

			ExtendedCompletableFuture<Pair<String, CONTEXT>> future =
				t.getByKey(sCACHE_TABLE, pIPAddress, null, String.class, pContext);
			return future.splitCompose((p) -> p.getValue0() != null,
				(p) -> ExtendedCompletableFuture.completedFuture(p), (p) -> {

					/* If there was no host name, then issue a domain name lookup */

					try {
						InetAddress result = InetAddress.getByName(pIPAddress);
						String hostName = result.getCanonicalHostName();

						return t.putByKey(sCACHE_TABLE, pIPAddress, null, hostName, Pair.with(hostName, pContext));
					}
					catch (UnknownHostException ex) {
						throw new RuntimeException(ex);
					}
				});
		});
	}

}
