package edu.sdsc.mmtf.spark.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.rcsb.mmtf.api.StructureDataInterface;

import edu.sdsc.mmtf.spark.mappers.StructureToPolymerChains;

public class MmtfReaderTest {
	private JavaSparkContext sc;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Before
	public void setUp() throws Exception {
		SparkConf conf = new SparkConf().setMaster("local[*]").setAppName(MmtfReaderTest.class.getSimpleName());
	    sc = new JavaSparkContext(conf);
    }
    
    @After
	public void tearDown() throws Exception {
		sc.close();
	}

	@Test
	public void test1() throws IOException {
		Path p = Paths.get("./src/main/resources/files/");
	    JavaPairRDD<String, StructureDataInterface> pdb = MmtfReader.readMmtfFiles(p.toString(), sc);
	    
	    assertTrue(pdb.count() == 3);
	}
	@Test
	public void test2() throws IOException {
		Path p = Paths.get("./src/main/resources/files/");
	    JavaPairRDD<String, StructureDataInterface> pdb = MmtfReader.readPdbFiles(p.toString(), sc);
	    
	    assertTrue(pdb.count() == 3);
	}
	@Test
	public void test3() throws IOException {
		Path p = Paths.get("./src/main/resources/files/");
	    JavaPairRDD<String, StructureDataInterface> pdb = MmtfReader.readMmcifFiles(p.toString(), sc);
	    
	    assertTrue(pdb.count() == 2);
	}
	@Test
	public void test4() throws IOException {
		Path p = Paths.get("./src/main/resources/files/test");
	    JavaPairRDD<String, StructureDataInterface> pdb = MmtfReader.readPdbFiles(p.toString(), sc);
	    assertTrue(pdb.count() == 1);
	    pdb = pdb.flatMapToPair(new StructureToPolymerChains());
	    assertTrue(pdb.count() == 8);
	}
	@Test
	public void test5() throws IOException {
		Path p = Paths.get("./src/main/resources/files/test");
	    JavaPairRDD<String, StructureDataInterface> pdb = MmtfReader.readMmtfFiles(p.toString(), sc);
	    assertTrue(pdb.count() == 1);
	    pdb = pdb.flatMapToPair(new StructureToPolymerChains());
	    assertTrue(pdb.count() == 8);
	}
	@Test
	public void test6() throws IOException {
		Path p = Paths.get("./src/main/resources/files/test");
	    JavaPairRDD<String, StructureDataInterface> pdb = MmtfReader.readMmcifFiles(p.toString(), sc);
	    assertTrue(pdb.count() == 1);
	    pdb = pdb.flatMapToPair(new StructureToPolymerChains());
	    assertTrue(pdb.count() == 8);
	}
}
