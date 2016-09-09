package com.diamondq.traffic.analyzer.ingest;

import com.diamondq.common.storage.kv.IKVStore;
import com.diamondq.common.storage.kv.IKVTransaction;

import javax.inject.Inject;

public class GetHostNameProcessor {

	private final IKVStore mKVStore;

	@Inject
	public GetHostNameProcessor(IKVStore pKVStore) {
		mKVStore = pKVStore;
	}

	public void process(String pIPAddress) {

		IKVTransaction transaction = mKVStore.startTransaction();
		boolean complete = false;

		try {
			/* First step is to check the database to see if we've already got information about this IP address */

			String result = transaction.getByKey("ipaddress", pIPAddress, null, String.class);
			if (result == null) {

			}

			complete = true;
		}
		finally {
			if (complete == true)
				transaction.commit();
			else
				transaction.rollback();
		}
	}

}
