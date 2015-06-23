package org.sparkredisexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import redis.clients.jedis.Jedis;
import scala.Tuple2;

public class Temperature {
	
	private static final FlatMapFunction<String, String> WORDS_EXTRACTOR = new FlatMapFunction<String, String>() {
		private static final long serialVersionUID = 1L;

		@Override
		public Iterable<String> call(String s) throws Exception {
			return Arrays.asList(s.split(" "));
		}
	};

	private static final PairFunction<String, String, Integer> WORDS_MAPPER = new PairFunction<String, String, Integer>() {
		private static final long serialVersionUID = 1L;

		@Override
		public Tuple2<String, Integer> call(String s) throws Exception {
			return new Tuple2<String, Integer>(s, 1);
		}
	};

	private static final Function2<Integer, Integer, Integer> WORDS_REDUCER = new Function2<Integer, Integer, Integer>() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Integer call(Integer a, Integer b) throws Exception {
			return a + b;
		}
	};

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		List<String> list = jedis.lrange("temperature-list", 0, 5);
		SparkConf conf = new SparkConf().setAppName("org.sparkexample.WordCount").setMaster("local");
		JavaSparkContext context = new JavaSparkContext(conf);
		JavaRDD<String> listRdd = context.parallelize(list);
		JavaPairRDD<String, Integer> pairs = listRdd.mapToPair(WORDS_MAPPER);
		JavaPairRDD<String, Integer> counter = pairs.reduceByKey(WORDS_REDUCER);
		counter.saveAsTextFile(args[1]);
		jedis.close();
	}
}