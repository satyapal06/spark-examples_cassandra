# spark-examples_cassandra
    git clone https://github.com/satyapal06/spark-examples_cassandra.git
    cd spark-examples_cassandra
    mvn clean install

Once we have build the Java application spark-examples_cassandra-0.0.1-SNAPSHOT.jar we can execute it locally on Apache Spark, this makes the entire testing process very easy.

On a command shell move to the spark installation directory and use the following command:

    ./bin/spark-submit --class org.sparkexample.WordCount --master local[2] 
    <path to maven project>/target/spark-examples_cassandra-0.0.1-SNAPSHOT.jar
    <path to a demo test file> /<path to output directory>"

where 

    "--class org.sparkexample.WordCount" is the main Java class with the public static void main method
    "--master local[2]" starts the cluster locally using 2 CPU cores
    <path to maven project> is the path to our maven project
    <path to a demo test file> is a demo local file which contains some words, an example file can be downloaded at
    https://github.com/satyapal06/spark-examples_cassandra/tree/master/src/main/resources/resources/loremipsum.txt
    <path to output directory> is the directory where the resuls should be saved

If everything is fine the output should be similar to the following image and we should find the file part-00000 in the output directory.
