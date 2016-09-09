package com.diamondq.traffic.analyzer.ingest;

import com.diamondq.common.storage.kv.inmemory.InMemoryStore;

import org.junit.Test;

import rx.Observable;

public class ProcessTest {

	@Test
	public void test() {

		Observable<String> ipAddresses = Observable.from(new String[]{"9.189.109.108"});
		GetHostNameProcessor process = new GetHostNameProcessor(new InMemoryStore());
		process.process("9.189.109.108");

	}

}
