public class Polynomial
{
	private double[] coefficients;

	public Polynomial()
	{
		coefficients = new double[]{0};
	}

	public Polynomial(double[] num_coefficients)
	{
		coefficients = new double[num_coefficients.length];
		for(int i=0; i<num_coefficients.length; i++)
		{
			coefficients[i] = num_coefficients[i];
		}
	}

	public Polynomial add(Polynomial other) 
	{
		int maxLen = Math.max(this.coefficients.length, other.coefficients.length);

		double[] result = new double[maxLen];

		for (int i = 0; i < maxLen; i++) 
		{
        		double a = 0;
       			double b = 0;

        		if (i < this.coefficients.length) 
			{
            			a = this.coefficients[i];
        		}

     			if (i < other.coefficients.length) 
			{
            			b = other.coefficients[i];
      			}
       			result[i] = a + b;
    		}
    		return new Polynomial(result);
	}

	public double evaluate(double x)
	{
		double value = 0.0;
		for (int i = coefficients.length - 1; i >= 0; i--)
		{
			value = value * x + coefficients[i];
		}
		return value;
	}
	

	public boolean hasRoot(double num)
	{
		return evaluate(num) == 0;
	}
	
}













