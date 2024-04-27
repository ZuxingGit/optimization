package com.group1.task4.part2.ModfiedMOEAClass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.moeaframework.core.PRNG;
import org.moeaframework.core.Variable;

/**
 * Decision variable for real RGB values.
 */
public class RGBVariable implements Variable{
    private static final long serialVersionUID = 3141851312155686224L;
	
	/**
	 * The current value of this decision variable.
	 */
	private int value;

	/**
	 * The lower bound of this decision variable.
	 */
	private final int lowerBound;

	/**
	 * The upper bound of this decision variable.
	 */
	private final int upperBound;

	/**
	 * Constructs a real variable in the range {@code lowerBound <= x <= upperBound} with an uninitialized value.
	 * 
	 * @param lowerBound the lower bound of this decision variable, inclusive
	 * @param upperBound the upper bound of this decision variable, inclusive
	 */
	public RGBVariable(int lowerBound, int upperBound) {
		this(0, lowerBound, upperBound);
	}

	/**
	 * Constructs a real variable in the range {@code lowerBound <= x <= upperBound} with the specified initial value.
	 * 
	 * @param value the initial value of this decision variable
	 * @param lowerBound the lower bound of this decision variable, inclusive
	 * @param upperBound the upper bound of this decision variable, inclusive
	 * @throws IllegalArgumentException if the value is out of bounds
	 *         {@code (value < lowerBound) || (value > upperBound)}
	 */
	public RGBVariable(int value, int lowerBound, int upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		
		setValue(value);
	}

	/**
	 * Returns the current value of this decision variable.
	 * 
	 * @return the current value of this decision variable
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value of this decision variable.
	 * 
	 * @param value the new value for this decision variable
	 * @throws IllegalArgumentException if the value is out of bounds
	 *         {@code (value < getLowerBound()) || (value > getUpperBound())}
	 */
	public void setValue(int value) {
		if ((value < lowerBound) || (value > upperBound)) {
			throw new IllegalArgumentException("value out of bounds (value: " + value + 
					", min: " + lowerBound + ", max: " + upperBound + ")");
		}

		this.value = value;
	}

	/**
	 * Returns the lower bound of this decision variable.
	 * 
	 * @return the lower bound of this decision variable, inclusive
	 */
	public int getLowerBound() {
		return lowerBound;
	}

	/**
	 * Returns the upper bound of this decision variable.
	 * 
	 * @return the upper bound of this decision variable, inclusive
	 */
	public int getUpperBound() {
		return upperBound;
	}

	@Override
	public RGBVariable copy() {
		return new RGBVariable(value, lowerBound, upperBound);
	}

	@Override
	public String toString() {
		return encode();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(lowerBound)
				.append(upperBound)
				.append(value)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if ((obj == null) || (obj.getClass() != getClass())) {
			return false;
		} else {
			RGBVariable rhs = (RGBVariable)obj;
			
			return new EqualsBuilder()
					.append(lowerBound, rhs.lowerBound)
					.append(upperBound, rhs.upperBound)
					.append(value, rhs.value)
					.isEquals();
		}
	}

	@Override
	public void randomize() {
		setValue(PRNG.nextInt(lowerBound, upperBound));
	}
	
	@Override
	public String encode() {
		return Integer.toString(value);
	}
	
	@Override
	public void decode(String value) {
		this.value = Integer.parseInt(value);
	}
}
