package com.sd.kafka.streams.ml;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.AbstractPrediction;
import hex.genmodel.easy.prediction.BinomialModelPrediction;
import hex.genmodel.easy.prediction.RegressionModelPrediction;

public class TextSentimentTest {

	// Name of the generated H2O model
	private static String modelClassName = "com.sd.kafka.streams.ml.models.gbm_text_sentiment_pojo";

	// Prediction Value
	private static String airlineDelayPreduction = "unknown";
	
	public static void main(final String[] args) throws Exception {
		
		String value = "jquery,how do you do?";
		
		// Create H2O object (see gbm_pojo_test.java)
		hex.genmodel.GenModel rawModel;
		rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).newInstance();
		EasyPredictModelWrapper model = new EasyPredictModelWrapper(rawModel);

					
		System.out.println("#####################");
		System.out.println("Twitter Text Input:" + value);

		String[] valuesArray = value.split(",");

		RowData row = new RowData();
		row.put("Query", valuesArray[0]);
		row.put("Text", valuesArray[1]);
		//BinomialModelPrediction p = null;
		RegressionModelPrediction p = null;
		try {
			p = model.predictRegression(row);
		} catch (PredictException e) {
			e.printStackTrace();
		}

		//airlineDelayPreduction = p.label;
		System.out.println("Label (aka prediction) is : " + p.value);
//					System.out.print("Class probabilities: ");
//					for (int i = 0; i < p.classProbabilities.length; i++) {
//						if (i > 0) {
//							System.out.print(",");
//						}
//						System.out.print(p.classProbabilities[i]);
//					}
//					System.out.println("");
		System.out.println("#####################");
					
	}

}
