package cz.krejcar25.sudoku.neuralNetwork;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface NeuralNetworkLayerProperties {
	boolean needsProperties() default false;

	String label();
}
