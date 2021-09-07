package com.basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorage.class,
        ListStorageTest.class,
        MapStorage.class,
        MapResumeStorageTest.class
})
public class AllStorageTest {
}
