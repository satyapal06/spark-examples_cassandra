# spark-examples_cassandra
git clone https://github.com/satyapal06/spark-examples_cassandra.git
cd spark-examples_cassandra
mvn clean install
cd ..

#Run the following command :-
./bin/spark-submit --class org.sparkexample.WordCount --master local[2] /<path to maven project>/target/spark-examples-1.0-SNAPSHOT.jar /<path to a demo test file> /<path to output directory>
