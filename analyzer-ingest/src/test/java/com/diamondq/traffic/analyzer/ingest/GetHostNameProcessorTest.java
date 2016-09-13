package com.diamondq.traffic.analyzer.ingest;

import com.diamondq.common.storage.kv.IKVTransaction;
import com.diamondq.common.storage.kv.inmemory.InMemoryStore;

import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

public class GetHostNameProcessorTest {

	@Test
	public void simpleHostTest() throws Throwable {

		GetHostNameProcessor process = new GetHostNameProcessor(new InMemoryStore());
		Pair<String, Object> pair = process.process("23.209.11.195", null).get();
		String host = pair.getValue0();
		System.out.println(host);

	}

	@Test
	public void testCache() throws Throwable {

		InMemoryStore store = new InMemoryStore();

		/* Store a IPAddress / Host combination in the store */

		IKVTransaction transaction = store.startTransaction();
		transaction.putByKey(GetHostNameProcessor.sCACHE_TABLE, "1.1.1.1", null, "testhost");
		transaction.commit();

		/* Now attempt to retrieve the host, and expect it will get it from the store */

		GetHostNameProcessor process = new GetHostNameProcessor(store);
		String result = process.process("1.1.1.1", null).get().getValue0();

		/* Make sure we got the match */

		Assert.assertEquals("Host names don't match", "testhost", result);

	}

}
