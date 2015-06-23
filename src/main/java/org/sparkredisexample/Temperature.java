package org.sparkredisexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import redis.clients.jedis.Jedis;

public class Temperature {
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		List<String> list = jedis.lrange("temperature-list", 0, 1000000);
		jedis.close();
		SparkConf conf = new SparkConf().setAppName("org.sparkredisexample.Temperature").setMaster("local");
		JavaSparkContext context = new JavaSparkContext(conf);
		JavaRDD<String> listRdd = context.parallelize(list);
		final List<Integer> maximumTemperatures = new ArrayList<Integer>();
		listRdd.foreach(new VoidFunction<String>() {
			private static final long serialVersionUID = 1L;
			public void call(String line) {
				List<String> list = Arrays.asList(line.split(","));
				String temperature = list.get(1);
				Integer temperatureValue = Integer.valueOf(temperature);
				if(temperatureValue > 40) {
					maximumTemperatures.add(temperatureValue);
				}
			}
		});
		
		JavaRDD<Integer> maximumTemperaturesRdd = context.parallelize(maximumTemperatures);
		maximumTemperaturesRdd.saveAsTextFile(args[1]);
	}
}