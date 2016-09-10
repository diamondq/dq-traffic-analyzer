package com.diamondq.traffic.analyzer.ingest;

import com.diamondq.common.storage.kv.inmemory.InMemoryStore;

import org.javatuples.Pair;
import org.junit.Test;

public class ProcessTest {

	@Test
	public void test() throws Throwable {

		GetHostNameProcessor process = new GetHostNameProcessor(new InMemoryStore());
		Pair<String, Object> pair = process.process("23.209.11.195", null).get();
		String host = pair.getValue0();
		System.out.println(host);

	}

}
