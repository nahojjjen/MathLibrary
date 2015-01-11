

public final class ImaginaryNumber  implements Comparable<ImaginaryNumber>, Cloneable{


	private double imaginary;
	private double real;

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////// Methods
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * {@inheritDoc} Variables that are hashed are the imaginary and real part
	 * of the number.
	 */
	@Override
	public int hashCode() {
		return ((int) (this.getImaginary() * 53) * 100000)
				+ (int) ((this.getReal() * 97) * 10000);
	}

	@Override
	/**
	 * {@inheritDoc}
	 * compare two imaginary numbers, returns true if the real and imaginary parts of the 
	 * number has the same value. Example, 2+3i and 2+3i returns true, 5+2i and 6+2i returns false.
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (this.getClass() != other.getClass()) {
			return false;
		}
		ImaginaryNumber otherIm = (ImaginaryNumber) other;
		if (this.getImaginary()== otherIm.getImaginary() && this.getReal() == otherIm.getReal()) {
			return true;
		}
		return false;

	}

	/**
	 * calculates the absolute value of an imaginary number
	 * 
	 * @param n
	 *            what imaginary number to test
	 * @return the absolute value of n
	 */
	public double calcAbsoluteVal() {
		// absolutvärde på komplext tal = sqrt(a^2 + b^2) : a är rationell del
		// och b är imaginär del
		return Math.sqrt((this.getReal() * this.getReal())
				+ (this.getImaginary() * this.getImaginary()));
	}

	/**
	 * slightly faster way of getting absolute val, make sure to check
	 * escapetime^2
	 * 
	 * @return absolute val of imaginary number^2
	 */
	public double calcAbsoluteValNoSqrt() {
		return (this.getReal() * this.getReal())
				+ (this.getImaginary() * this.getImaginary());
	}

	/**
	 * raise the imaginary number to the power of 2
	 */
	public ImaginaryNumber pow2() {
		ImaginaryNumber returner = new ImaginaryNumber((this.getReal() * this.getReal() - this.getImaginary()
				* this.getImaginary()) ,(this.getReal() * this.getImaginary() + this
						.getImaginary() * this.getReal()));
		return returner;

	}

	/**
	 * example: i.pow(3) returns i*i*i, i.pow(8) returns i^2^2^2 (efficient! :D)
	 * @pre cannot pow negative numbers
	 * @param powerOf what to raise to
	 * @return a new imaginary number with the result as value.
	 */
	public ImaginaryNumber pow(int powerOf) {
		if (powerOf < 0){
			throw new IllegalArgumentException("This api cant raise to the power of something negative, pls read @pre first");
		}   
		if (powerOf == 0){
			return new ImaginaryNumber(1,0);
		}
		if (powerOf == 1) {
			// c^1 = c
			return this;
		}
		if (powerOf % 2 == 0) {
			// c^8 = c^2^4
			return ((this.pow2()).pow(powerOf / 2));
		}
		if (powerOf % 2 == 1) {
			// c^7 = c^6 * c
			return ((this.multipyWith(this.pow(powerOf - 1))));
		}
		return this;
	}

	// (x+yi)(u+vi) = (xu - yv) + (xv + yu)i
	/**
	 * Multiply this imnum with another, overrides the value of this imnum with
	 * the results.
	 * 
	 * @param n
	 *            example: x.multiplyWith(n) does x*n and stores the result in
	 *            x.
	 */
	public ImaginaryNumber multipyWith(ImaginaryNumber n) {
		ImaginaryNumber returner = new ImaginaryNumber((this.getReal() * n.getReal() - this.getImaginary()
				* n.getImaginary()),(this.getReal() * n.getImaginary() + this.getImaginary() * n.getReal()));
		return returner;
	}

	/**
	 * divide an imaginary number with another
	 * @param n what imaginary number to divide this with
	 * @return a new imaginary number with the results.
	 */
	public ImaginaryNumber divideBy(ImaginaryNumber n){

		//C /c2 = C*
		if (n.getReal() ==0 && n.getImaginary() == 0){
			throw new DivideByZeroException("Even if it's imaginary, you cant divide by 0.");
		}
		ImaginaryNumber conjugate = new ImaginaryNumber(n.getReal(),-1*n.getImaginary());
		double divideNumber = n.getReal()*n.getReal() + n.getImaginary()*n.getImaginary();
		ImaginaryNumber returner = this.multipyWith(new ImaginaryNumber(conjugate.getReal(), conjugate.getImaginary()));
		return new ImaginaryNumber(returner.getReal()/divideNumber, returner.getImaginary()/divideNumber);
	}

	/**
	 * add an imaginary number to another
	 * 
	 * @param oth
	 *            what other imaginary number to add
	 * @return the new imaginary number
	 */
	public ImaginaryNumber add(ImaginaryNumber oth) {
		ImaginaryNumber returner = new ImaginaryNumber(this.getReal() + oth.getReal(),this.getImaginary() + oth.getImaginary());
		return returner;
	}

	/**
	 * subtract an imaginary number with another
	 * 
	 * @param oth
	 *            x - oth = new x. where x was the old imaginary number
	 */
	public ImaginaryNumber sub(ImaginaryNumber oth) {
		ImaginaryNumber returner = new ImaginaryNumber(this.getReal() - oth.getReal(),(this.getImaginary() - oth.getImaginary()));
		return returner;
	}

	/**
	 * {@inheritDoc} returns ImaginaryNumber to string in the format "of a + bi"
	 */
	@Override
	public String toString() {
		return String.valueOf(this.getReal()) + " + "
				+ String.valueOf(this.getImaginary() + "i");
	}

	/**
	 * Returns a string with the amount of decimals in args
	 * @param decimals	how many decimals toprint out, example toShortString(4) returns 0.1234
	 * @pre Becomes unprecise if you want more than a couple of decimals because of Double rounding.
	 * @return A string representation of the double rounded to decimals
	 * @throws	IllegalArgumentException if decimals is negative
	 */
	public String toShortString(int decimals) {

		if (decimals < 0){
			throw new IllegalArgumentException("Cannot have negative decimals");
		}

		double roundedReal = roundDouble(real,decimals);
		double roundedIm = roundDouble(imaginary,decimals);


		return String.valueOf(roundedReal) + " + " + String.valueOf(roundedIm) + "i";
	}

	/**
	 * Round a double to a shorter number
	 * @param input What double to round
	 * @param decimals How many decimals to show
	 * @return A new double with less decimals.
	 * @throws	IllegalArgumentException if decimals is negative
	 */

	
	private double roundDouble(double input,int decimals) {
		if (decimals < 0){
		throw new IllegalArgumentException("Cannot have negative decimals");
	}
		
		int decimalMultiplier = (int) Math.pow(10, decimals);

		double rounded =  (Math.round(input*decimalMultiplier));
		rounded = rounded /decimalMultiplier;
		return rounded;
	}

	/**
	 * default constructor , creates a new imnum = 0 + 0i
	 */
	public ImaginaryNumber() {
		this.real = 0;
		this.imaginary = 0;
	}

	/**
	 * constructor
	 * 
	 * @param a
	 *            real part of imaginary number
	 * @param i
	 *            imaginary part of imaginary number
	 */
	public ImaginaryNumber(double a, double i) {
		this.real = a;
		this.imaginary = i;
	}

	/**
	 * Clone constructor, creates a new imnum with the same values as params
	 * 
	 * @param otherIm
	 *            what imaginary number to clone.
	 */
	public ImaginaryNumber(ImaginaryNumber otherIm) {
		this.real = otherIm.getReal();
		this.imaginary = otherIm.getImaginary();
	}

	public double getReal() {
		return this.real;
	}

	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * {@inheritDoc}
	 * Compare imaginaryNumbers using their abolute value in the imaginary number-plane
	 */
	@Override
	public int compareTo(ImaginaryNumber other) {
		if (this.calcAbsoluteValNoSqrt() > other.calcAbsoluteValNoSqrt()){
			return 1;
		}
		else if (this.calcAbsoluteValNoSqrt() < other.calcAbsoluteValNoSqrt()){
			return -1;
		}
		return 0;
	}

	/**
	 * Returns a copy of this immutable number. I dont know why you need this.
	 * @return A copy of the object
	 */
	public Object Clone(){
		try{
			return super.clone();
		}
		catch (CloneNotSupportedException e){
			e.printStackTrace();
			return null;
		}
	}

}

