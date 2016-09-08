package com.diamondq.traffic.analyzer.ingest;

import com.diamondq.common.storage.kv.inmemory.InMemoryStore;

import org.junit.Test;

public class ProcessTest {

	@Test
	public void test() {

		GetHostNameProcessor process = new GetHostNameProcessor(new InMemoryStore());
		process.process("9.189.109.108");

	}

}
